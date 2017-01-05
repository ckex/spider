/**
 * 
 */
package com.mljr.spider.listener;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

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

	private static final RemovalListener<LocalCacheKey, MonitorData> LISTENER = new RemovalListener<LocalCacheKey, MonitorData>() {

		@Override
		public void onRemoval(RemovalNotification<LocalCacheKey, MonitorData> notification) {
			saveData(notification.getKey(), notification.getValue());
		}

	};

	// http://blog.csdn.net/abc86319253/article/details/53020432
	private static final LoadingCache<LocalCacheKey, MonitorData> LOCAL_CACHE = CacheBuilder.newBuilder().concurrencyLevel(5)
			.expireAfterWrite(1, TimeUnit.MINUTES).refreshAfterWrite(10, TimeUnit.SECONDS).initialCapacity(10).maximumSize(10000)
			.removalListener(RemovalListeners.asynchronous(LISTENER, Executors.newSingleThreadExecutor(new ThreadFactory() {
				
						@Override
						public Thread newThread(Runnable r) {
							final Thread thread = new Thread(r, "cache-remove-listener");
							thread.setDaemon(true);
							return thread;
						}
			}))).recordStats()
			.build(new CacheLoader<LocalCacheKey, MonitorData>() {

				@Override
				public MonitorData load(LocalCacheKey key) {
					return new MonitorData();
				}

			});

	protected void updateValue(LocalCacheKey key, Setter setter) {
		MonitorData value = LOCAL_CACHE.getUnchecked(key);
		synchronized (value) { // 同步 原子操作
			setter.setData(LOCAL_CACHE.getUnchecked(key));
		}
		
	}

	private static void saveData(final LocalCacheKey key, final MonitorData value) {
		redisClient.use(new Function<Jedis, Void>() {

			@Override
			public Void apply(Jedis jedis) {
				String keyStr = Joiner.on("-").join(KEY_PRE, key.hostname, key.domain);
				value.setTime(key.time);
				jedis.lpush(keyStr, JSON.toJSONString(value));
//				List<String> list = jedis.lrange(keyStr, 0, 0);
//				if (list.isEmpty()) {
//					jedis.lpush(keyStr, JSON.toJSONString(value));
//				} else {
//					merge(JSON.parseObject(list.get(0), MonitorData.class), value);
//					jedis.lset(keyStr, 0, JSON.toJSONString(value));
//				}
				return null;
			}

//			private void merge(MonitorData oldData, MonitorData value) {
//				// merge value
//				// value.setFreq200(value.getFreq200() + oldData.getFreq200());
//			}
		});
	}

	@FunctionalInterface
	protected interface Setter {
		void setData(MonitorData value);
	}

	public AbstractMonitorCache() {
		super();
	}

	public static final class LocalCacheKey {

		public final String time;
		public final String hostname;
		public final String domain;

		public LocalCacheKey(Date time, String hostname, String domain) {
			this(DateFormatUtils.format(time, PATTERN), hostname, domain);
		}

		public LocalCacheKey(String time, String hostname, String domain) {
			super();
			this.time = time;
			this.hostname = hostname;
			this.domain = domain;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((domain == null) ? 0 : domain.hashCode());
			result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
			result = prime * result + ((time == null) ? 0 : time.hashCode());
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
			if (time == null) {
				if (other.time != null)
					return false;
			} else if (!time.equals(other.time))
				return false;
			return true;
		}

	}
}
