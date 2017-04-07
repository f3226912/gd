package com.gudeng.commerce.customer;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.service.PushTaskService;
import com.gudeng.commerce.gd.task.service.AdTaskService;
import com.gudeng.commerce.gd.task.service.ExpireProductTask;
import com.gudeng.commerce.gd.task.service.PushMessageTaskService;
import com.gudeng.commerce.gd.task.service.StaticHtmlService;


/**
 * 
 * 
 * TestTask
 * semon
 * 2015年11月20日 下午4:12:18
 * 
 * @version 1.0.0
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({	
    "classpath:conf/spring/spring-cache.xml",
    "classpath:conf/spring/spring-da.xml",
    "classpath:conf/spring/spring-exception.xml",
    "classpath:conf/spring/spring-impl.xml",
    "classpath:conf/spring/spring-res.xml",
})
public class TestTask extends TestCase{
	
/*	private static  PushMessageTaskService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/pushtaskService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return ( PushMessageTaskService) factory.create(PushMessageTaskService.class, url);				
	}*/
	@Autowired
	private PushTaskService pushTaskService;
	
	@Autowired
	private StaticHtmlService staticHtmlService;
	
	@Autowired
	private PushMessageTaskService pushMessageTaskService;
	
	@Autowired 
	private ExpireProductTask expireProductTask;
	
	@Autowired
	private AdTaskService adTaskService;
	
	
	@Test
	public void testPushTask() throws Exception{
		//pushMessageTaskService.PushMessageTask();
		pushMessageTaskService.PushMessageTask();
		
	}
	
	@Test
	public void Html() throws Exception{
		//pushMessageTaskService.PushMessageTask();
		staticHtmlService.generatorHtml();
		
	}
	
	
	@Test
	public void ExpireProduct() throws Exception{
		//pushMessageTaskService.PushMessageTask();
		
		expireProductTask.HandleExpireProduct();
		
	}
	
	//过期广告处理
	@Test
	public void adProcess() throws Exception{
		//pushMessageTaskService.PushMessageTask();
		
		adTaskService.HandleExpireAd();
		
	}
	
	
 	
	
	
	public static void main(String[] args){
		
		String url = "http://192.168.20.164:8084/taskService/pushMessageTaskService.hs";
		
//		String url = "http://192.168.20.164:8084/gd-task/taskService/expireProductTask.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			PushMessageTaskService shs =  (PushMessageTaskService) factory.create(PushMessageTaskService.class, url);
//			shs.generatorHtml();
			shs.PushMessageTask();
//			ExpireProductTask shs =  (ExpireProductTask) factory.create(ExpireProductTask.class, url);
//			shs.HandleExpireProduct();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
