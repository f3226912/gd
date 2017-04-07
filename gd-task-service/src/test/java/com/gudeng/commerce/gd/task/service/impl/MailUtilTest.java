package com.gudeng.commerce.gd.task.service.impl;

import org.junit.Test;

import com.gudeng.commerce.gd.task.util.EmailUtil;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月23日 下午7:24:45
 */
public class MailUtilTest extends AbstractServiceTest {
	
	@Test
	public void testMail() {
		EmailUtil.doSendHtmlEmail("测试", "测试内容", "panmin231@163.com");
	}

}
