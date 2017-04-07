package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.SensitiveWordDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.entity.SensitiveWordEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.SensitiveWordService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class SensitiveWordServiceTest extends TestCase{
	private static SensitiveWordService getService() throws MalformedURLException{
		String url = "http://localhost:8080/gd-customer/service/sensitiveWordService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (SensitiveWordService) factory.create(SensitiveWordService.class, url);				
	}
	
	@SuppressWarnings("static-access")
	public void testAdd()throws Exception {
		SensitiveWordService service=this.getService();
		int count=0;
		
		for(int i=1;i<10000;i++){
			SensitiveWordEntity entity=new SensitiveWordEntity();
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			entity.setDescription("第"+i+"个");
			entity.setName("敏感词"+i);
			service.insert(entity);
			count++;
		}
		System.out.println("共插入"+count+"条记录");
		
	}
	
}
