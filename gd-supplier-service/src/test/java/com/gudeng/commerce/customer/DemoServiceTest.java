package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.DictDTO;
import com.gudeng.commerce.gd.supplier.service.DemoService;

public class DemoServiceTest extends TestCase{
	private static DemoService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/demoService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (DemoService) factory.create(DemoService.class, url);				
	}
	
	@SuppressWarnings("static-access")
	public void testGetDic()throws Exception {
		DemoService service=this.getService();
		DictDTO dto = service.getById("1");
		if(null != dto){
			System.out.println(dto.getId()+":"+dto.getName());
		}
	}
	
	public void testGetByCondition() throws Exception{
		DemoService service=this.getService();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", "1");
		List<DictDTO> dicList = service.getByCondition(map);
		if(null != dicList){
			for(DictDTO dto : dicList){
				System.out.println(dto.getId() + ":" + dto.getName());
			}
		}
	}
	
	public void testGetTotal() throws Exception{
		DemoService service=this.getService();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", "1");
		System.out.println("total:" + service.getTotal(map));
		
	}
	
}
