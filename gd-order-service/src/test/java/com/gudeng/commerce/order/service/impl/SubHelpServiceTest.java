package com.gudeng.commerce.order.service.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.service.SubHelpService;
import com.gudeng.commerce.gd.order.service.impl.SubHelpServiceImpl;

/**
 * 对SubHelpServie的方法进行测试
 * @author lmzhang@gdeng.cn
 *
 */
public class SubHelpServiceTest {
	private SubHelpService service = new SubHelpServiceImpl();
	
	
	@Before
	public void initService(){
		ApplicationContext ac = new FileSystemXmlApplicationContext("classpath:conf/spring/spring-impl.xml");
		service = (SubHelpService)ac.getBean("subHelpService");
		
		assertNotNull("获取subHelpServiceImpl失败！", service);
	}
	
	@Test
	public void testGetSubOutMarket() {
		try {
			String dStr = "2015-12";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date date = sdf.parse(dStr);
			
//			Map<String, Integer> map = this.service.getSubOutMarket("粤B88888", (long)1, date);
//			assertNotNull("getSubOutMarket 方法异常！",map);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
