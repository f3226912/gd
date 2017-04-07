package com.gudeng.commerce.gd.api.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gudeng.commerce.gd.api.redis.RedisService;
import com.gudeng.commerce.gd.api.service.DemoService;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-cache.xml",
		"classpath:spring/spring-res.xml",
		"classpath:spring/applicationContext.xml" })
public class RedisTest {

	@Autowired
	public DemoService demoService;
	@Autowired
	public PaySerialnumberToolServiceImpl paySerialnumberToolServiceImpl;
	@Autowired
	public RedisService redisUtil;

	@Test
	public void test() throws Exception {

		PaySerialnumberDTO p = paySerialnumberToolServiceImpl.getById(11L);
//		System.out.println(redisUtil.setex("kk", "333333333333",100l));
//		System.out.println(redisUtil.exists(RedisConstant.kk));
//
//		System.out.println(redisUtil.get(RedisConstant.kk));
//
		System.out.println(redisUtil.ttl("kk"));
//		
//		System.out.println(redisUtil.ttl(RedisConstant.kk));

		// System.out.println(p);
	}

	@Test
	public void dele() throws Exception {

//		System.out.println(redisUtil.delete(RedisConstant.VALIDATE_CODE));

	}
}
