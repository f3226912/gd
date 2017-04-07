package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.service.SubAuditService;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={/*"file:src/main/webapp/WEB-INF/wxiot-servlet.xml",*/ "classpath:applicationContext.xml","classpath:applicationContext-security.xml"})
@ContextConfiguration("locations={classpath:conf/spring/spring-impl.xml}")
public class TotalSubAmountTest {
	
	private SubAuditService service;
	private BaseDao baseDao;
	
	
	@Before
	public void initService(){
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:conf/spring/spring-impl.xml");
		service = (SubAuditService)ac.getBean("SubAuditService");
		baseDao = (BaseDao)ac.getBean("baseDao");
		assertNotNull("获取subHelpServiceImpl失败！", service);
	}
	
	@Test
	public void testGetTotalAmount(){
		//Map<Str>
		
		//baseDao.queryForList("getSubTotalAmount", paramMap)
	}
	
}
