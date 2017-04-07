package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.MqAsyncErrorDTO;
import com.gudeng.commerce.gd.customer.entity.MqAsyncErrorEntity;
import com.gudeng.commerce.gd.customer.service.MqAsyncErrorService;

import junit.framework.TestCase;

public class MqAsyncErrorServiceTest extends TestCase {
	private static MqAsyncErrorService getService() throws MalformedURLException {
		String url = "http://127.0.0.1:8080/gd-customer/service/memberAsyncErrorService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (MqAsyncErrorService) factory.create(MqAsyncErrorService.class, url);
	}
	
	public void testadd() throws Exception {
		MqAsyncErrorService service = this.getService();
		MqAsyncErrorEntity entity = new MqAsyncErrorEntity();
		entity.setCrud(0);
		entity.setCreateTime(new Date());
		entity.setStatus(0);
		entity.setContent("{'id':123,'name':'name'}");
		Long i = service.insert(entity);
		if (i >0 ) {
			System.out.println("新增成功");
		}
	}
	public void testGetById() throws Exception {
		MqAsyncErrorService service = this.getService();
		MqAsyncErrorDTO dto = service.getById("1");
		System.out.println("成功"+dto.getContent()+dto.getStatus()+dto.getCrud());
	}
	
	public void testUpdate() throws Exception {
		MqAsyncErrorService service = this.getService();
		MqAsyncErrorDTO entity = new MqAsyncErrorDTO();
		entity.setId(1);
		entity.setCrud(0);
		entity.setCreateTime(new Date());
		entity.setStatus(0);
		entity.setContent("{'id':1234,'name':'updatename'}");
		int i = service.update(entity);
		if (i == 1) {
			System.out.println("修改成功");
		}
	}

}
