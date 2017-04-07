package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.SensitiveLogDTO;
import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.MemberBaseinfoService;
import com.gudeng.commerce.gd.customer.service.SensitiveLogService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class SensitiveLogServiceTest extends TestCase{
	private static SensitiveLogService getService() throws MalformedURLException{
		String url = "http://localhost:8080/gd-customer/service/sensitiveLogService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (SensitiveLogService) factory.create(SensitiveLogService.class, url);				
	}
	
	@SuppressWarnings("static-access")
	public void testGetDic()throws Exception {
		SensitiveLogService service=this.getService();
		SensitiveLogDTO dto = service.replaceAllSensitiveWords("在习近平领导的共产党政权下,共党组织得到很好发展");
		if(null != dto){
			System.out.println("dto.getCnt():"+dto.getCnt());
			System.out.println("dto.getStrs():"+dto.getStrs());
			System.out.println("dto.getWords():"+dto.getWords());
		}
	}
	
}
