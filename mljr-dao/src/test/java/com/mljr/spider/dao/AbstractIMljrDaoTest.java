/**
 * 
 */
package com.mljr.spider.dao;

import com.mljr.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author Ckex zha </br>
 *         2016年11月7日,下午2:06:29
 *
 */
@ContextConfiguration(locations = { "classpath*:/spring/dao.xml", "classpath*:/spring/dao-datasource.xml" })
public class AbstractIMljrDaoTest extends AbstractJUnit4SpringContextTests {

	protected static transient Logger logger = LoggerFactory.getLogger(AbstractIMljrDaoTest.class);

	public AbstractIMljrDaoTest() {
		super();
	}

	@Autowired
	protected YyUserAddressBookDao yyUserAddressBookDao;

	@Autowired
	protected RedisClient redisClient;

	@Autowired
	protected SpiderBankCardLocationDao spiderBankCardLocationDao;

	@Autowired
	protected MerchantInfoDao merchantInfoDao;

	@Autowired
	protected YyUserCallRecordHistoryDao yyUserCallRecordHistoryDao;

	@Autowired
	protected YyUserAddressBookHistoryDao yyUserAddressBookHistoryDao;

	@Autowired
	protected YybgrkContactListDao yybgrkContactListDao;

}
