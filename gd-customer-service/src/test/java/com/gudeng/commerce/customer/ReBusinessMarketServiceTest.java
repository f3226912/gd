package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.ReBusinessMarketDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessMarketEntity;
import com.gudeng.commerce.gd.customer.service.ReBusinessMarketService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class ReBusinessMarketServiceTest extends TestCase{
	
	public ReBusinessMarketService service;
//	private static ReBusinessMarketService getService() throws MalformedURLException{
//		String url = "http://127.0.0.1:8080/gd-customer/service/reBusinessMarketService.hs";
//		HessianProxyFactory factory = new HessianProxyFactory();
//		return (ReBusinessMarketService) factory.create(ReBusinessMarketService.class, url);				
//	}
 
	
	@Override
	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-customer/service/reBusinessMarketService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		service=(ReBusinessMarketService)factory.create(ReBusinessMarketService.class, url);	
 	}


	public void testGetByBusinessId()throws Exception {
		ReBusinessMarketDTO i=service.getByBusinessId(5163L) ;
		if(i!=null ){
			System.out.println(i.getBusinessId());
			System.out.println(i.getMarketId());
			System.out.println(i.getMarketName());
		}
	}
	
//	public void testAddByDto()throws Exception {
//		ReBusinessMarketService service=this.getService();
//		ReBusinessMarketDTO mb = new ReBusinessMarketDTO();
//		mb.setBusinessId(4L);
//		mb.setMarketId(5L);
//		
//		int i=service.addReBusinessMarketDTO(mb) ;
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
	
	
//	public void testDeleteByDto()throws Exception {
//		ReBusinessMarketService service=this.getService();
//		ReBusinessMarketDTO mb = new ReBusinessMarketDTO();
//		mb.setBusinessId(3L);
//		mb.setMarketId(5L);
//		
//		int i=service.deleteReBusinessMarketDTO(mb) ;
//		if(i==1){
//			System.out.println("删除dto成功");
//		}
//	}
//	
	
//	
//	public void testGetByDto()throws Exception {
//		ReBusinessMarketService service=this.getService();
//		ReBusinessMarketDTO mb = new ReBusinessMarketDTO();
//		mb.setBusinessId(3L);
//		mb.setMarketId(5L);
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 	10);
//		map.put("bussinessId", 3);
//		map.put("marketId", 5);
//		List<ReBusinessMarketDTO> i=service.getBySearch(map) ;
//		for(ReBusinessMarketDTO rbm:i){
//			System.out.println(rbm.getBusinessId()+"...."+ rbm.getMarketId());
//		}
//	}
//	
	
	
//	public void testgetTotal()throws Exception {
//		ReBusinessMarketService service=this.getService();
//		ReBusinessMarketDTO mb = new ReBusinessMarketDTO();
//		mb.setBusinessId(3L);
//		mb.setMarketId(5L);
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 	10);
//		map.put("mainId", 1);
////		map.put("marketId", 5);
//		int i=service.getTotal(map);
//		System.out.println("个数"+i);
//	}
	
	
}
