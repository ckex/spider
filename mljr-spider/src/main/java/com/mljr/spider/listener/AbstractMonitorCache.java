/**
 * 
 */
package com.mljr.spider.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalListeners;
import com.google.common.cache.RemovalNotification;
import com.mljr.common.ServiceConfig;
import com.mljr.entity.MonitorData;
import com.mljr.redis.RedisClient;
import com.mljr.utils.IpUtils;

import redis.clients.jedis.Jedis;

/**
 * @author Ckex zha </br>
 *         Jan 5, 2017,10:33:04 AM
 *
 */
public abstract class AbstractMonitorCache {

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String PATTERN = "yyyy-MM-dd-HH-mm";

	public static final String KEY_PRE = "status-code";

	private static RedisClient redisClient = ServiceConfig.getSpiderRedisClient();

	private static final RemovalListener<LocalCacheKey, Map<String, MonitorData>> LISTENER = new RemovalListener<LocalCacheKey, Map<String, MonitorData>>() {

		@Override
		public void onRemoval(RemovalNotification<LocalCacheKey, Map<String, MonitorData>> notification) {
			saveData(notification.getKey(), notification.getValue());
		}

	};

	// http://blog.csdn.net/abc86319253/article/details/53020432
	private static final LoadingCache<LocalCacheKey, Map<String, MonitorData>> LOCAL_CACHE = CacheBuilder.newBuilder()
			.concurrencyLevel(5).expireAfterWrite(80, TimeUnit.SECONDS).refreshAfterWrite(40, TimeUnit.SECONDS)
			.initialCapacity(10).maximumSize(10000).removalListener(
					RemovalListeners.asynchronous(LISTENER, Executors.newSingleThreadExecutor(new ThreadFactory() {

						@Override
						public Thread newThread(Runnable r) {
							final Thread thread = new Thread(r, "cache-remove-listener");
							thread.setDaemon(true);
							return thread;
						}
					})))
			.recordStats().build(new CacheLoader<LocalCacheKey, Map<String, MonitorData>>() {

				@Override
				public Map<String, MonitorData> load(LocalCacheKey key) {
					// return new MonitorData();
					return new HashMap<>();
				}

			});

	protected void updateValue(LocalCacheKey key, Setter setter) {
		updateValue(key, nowStr(), setter);
	}

	protected void updateValue(LocalCacheKey key, String time, Setter setter) {
		synchronized (LOCAL_CACHE) {
			Map<String, MonitorData> map = LOCAL_CACHE.getUnchecked(key);
			if (!map.containsKey(time)) {
				map.put(time, new MonitorData());
			}
			setter.setData(map.get(time));
		}
	}

	private static void saveData(final LocalCacheKey key, final Map<String, MonitorData> values) {
		synchronized (LOCAL_CACHE) {

			redisClient.use(new Function<Jedis, Void>() {

				@Override
				public Void apply(Jedis jedis) {
					String keyStr = Joiner.on("-").join(KEY_PRE, key.hostname, key.domain);
					values.forEach((k, val) -> {
						if (StringUtils.equalsIgnoreCase(k, nowStr())) {
							LOCAL_CACHE.getUnchecked(key).put(k, val);
						} else {
							val.setTime(k);
							val.setDomain(key.domain);
							val.setServerIp(key.hostname);
							jedis.lpush(keyStr, JSON.toJSONString(val));
						}
					});
					return null;
				}
			});
		}
	}

	@FunctionalInterface
	protected interface Setter {
		void setData(MonitorData value);
	}

	public AbstractMonitorCache() {
		super();
	}

	public static String nowStr() {
		return DateFormatUtils.format(new Date(), PATTERN);
	}

	public static final class LocalCacheKey {

		public final String hostname = IpUtils.getHostName();
		public final String domain;

		public LocalCacheKey(String domain) {
			super();
			this.domain = domain;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((domain == null) ? 0 : domain.hashCode());
			result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LocalCacheKey other = (LocalCacheKey) obj;
			if (domain == null) {
				if (other.domain != null)
					return false;
			} else if (!domain.equals(other.domain))
				return false;
			if (hostname == null) {
				if (other.hostname != null)
					return false;
			} else if (!hostname.equals(other.hostname))
				return false;
			return true;
		}

	}
}
