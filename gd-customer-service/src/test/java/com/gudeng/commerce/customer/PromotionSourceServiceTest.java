package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PromotionSourceDTO;
import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;
import com.gudeng.commerce.gd.customer.service.PromotionSourceService;

public class PromotionSourceServiceTest extends TestCase{
	private static PromotionSourceService getService() throws MalformedURLException{
		String url = "http://localhost:8080/gd-customer/service/promotionSourceService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (PromotionSourceService) factory.create(PromotionSourceService.class, url);				
	}
	 
//	public void testAdd()throws Exception {
//		PromotionSourceService service = this.getService();
//		PromotionSourceEntity promotionSourceEntity=new PromotionSourceEntity();
//		promotionSourceEntity.setName("百度推广");
//		Long l=service.addPromotionSourceEnt(promotionSourceEntity);
//		System.out.println("新增的id 为"+l);
//	}
	
//	public void testUpdate()throws Exception {
//		PromotionSourceService service = this.getService();
//		PromotionSourceDTO promotionSourceDTO=new PromotionSourceDTO();
//		promotionSourceDTO.setId(1L);
//		promotionSourceDTO.setName("百度推广1");
//		int l=service.updatePromotionSourceDTO(promotionSourceDTO);
//		System.out.println("修改的id 为"+l);
//	}
//	public void testgetById()throws Exception {
//		PromotionSourceService service = this.getService();
//		PromotionSourceDTO promotionSourceDTO=new PromotionSourceDTO();
//	 
//		PromotionSourceDTO l=service.getById(1L) ;
//		System.out.println("修改的id 为"+l.getId()+l.getName());
//	}
//	public void testgetByName()throws Exception {
//		PromotionSourceService service = this.getService();
//		PromotionSourceDTO promotionSourceDTO=new PromotionSourceDTO();
//		
//		List<PromotionSourceDTO> l=service.getByName("百度") ;
//		System.out.println("修改的id 为"+l.get(0).getId()+l.get(0).getName());
//	}
	
}
