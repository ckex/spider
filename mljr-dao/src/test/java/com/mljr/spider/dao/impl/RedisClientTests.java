/**
 * 
 */
package com.mljr.spider.dao.impl;

import org.junit.Test;

import com.google.common.base.Function;
import com.mljr.spider.dao.AbstractIMljrDaoTest;

import redis.clients.jedis.Jedis;

/**
 * @author Ckex zha </br>
 *         2016年11月30日,上午11:01:58
 *
 */
public class RedisClientTests extends AbstractIMljrDaoTest {

  @Test
  public void testGet() {
    String result = redisClient.use(new Function<Jedis, String>() {

      @Override
      public String apply(Jedis jedis) {
        return jedis.get("test");
      }
    });
    logger.debug("result===> " + result);
  }

}
