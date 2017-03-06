/**
 * 
 */
package com.mljr.redis;

import com.google.common.base.Function;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午5:35:02
 *
 */
public class RedisClient {

  private JedisPool pool = null;

  public RedisClient(String host, int port, int timeout, int maxTotal, int maxIdle, int maxWaitMillis, String pwd) {
    super();
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(maxTotal);
    config.setMaxIdle(maxIdle);
    config.setTestOnBorrow(true);
    config.setMaxWaitMillis(maxWaitMillis);
    this.pool = new JedisPool(config, host, port, timeout, pwd);
  }

  public RedisClient(String host, int port, int timeout, int maxTotal, int maxIdle, int maxWaitMillis, int dataabase, String pwd) {
    super();
    JedisPoolConfig config = new JedisPoolConfig();
    config.setMaxTotal(maxTotal);
    config.setMaxIdle(maxIdle);
    config.setTestOnBorrow(true);
    config.setMaxWaitMillis(maxWaitMillis);
    this.pool = new JedisPool(config, host, port, timeout, pwd, dataabase, null);
  }

  private Jedis getResource() {
    Jedis jedis = pool.getResource();
    if (jedis == null) {
      throw new RuntimeException("Get jedis can't be null.");
    }
    return jedis;
  }

  public <T> T use(Function<? super Jedis, T> function) {
    Jedis jedis = null;
    T result = null;
    try {
      jedis = getResource();
      result = function.apply(jedis);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (jedis != null) {
        jedis.close();
      }
    }
    return result;
  }

}
