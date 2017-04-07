package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.ReMemberMarketDTO;
import com.gudeng.commerce.gd.customer.entity.ReMemberMarketEntity;
import com.gudeng.commerce.gd.customer.service.ReMemberMarketService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class ReMemberMarketServiceTest extends TestCase{
	private static ReMemberMarketService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/reMemberMarketService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ReMemberMarketService) factory.create(ReMemberMarketService.class, url);				
	}
 
	
//	public void testAddByDto()throws Exception {
//		ReMemberMarketService service=this.getService();
//		ReMemberMarketDTO mb = new ReMemberMarketDTO();
//		mb.setMemberId(4L);
//		mb.setMarketId(5L);
//		
//		int i=service.addReMemberMarketDTO(mb) ;
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
//	
	
	public void testDeleteByDto()throws Exception {
		ReMemberMarketService service=this.getService();
		ReMemberMarketDTO mb = new ReMemberMarketDTO();
		mb.setMemberId(4L);
		mb.setMarketId(5L);
		
		int i=service.deleteReMemberMarketDTO(mb) ;
		if(i==1){
			System.out.println("删除dto成功");
		}
	}
	
	
//	
//	public void testGetByDto()throws Exception {
//		ReMemberMarketService service=this.getService();
//		ReMemberMarketDTO mb = new ReMemberMarketDTO();
//		mb.setMemberId(3L);
//		mb.setMarketId(5L);
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 	10);
//		map.put("memberId", 4);
//		map.put("marketId", 5);
//		List<ReMemberMarketDTO> i=service.getBySearch(map) ;
//		for(ReMemberMarketDTO rbm:i){
//			System.out.println(rbm.getMemberId()+"...."+ rbm.getMarketId());
//		}
//	}
//	
	
	
	public void testgetTotal()throws Exception {
		ReMemberMarketService service=this.getService();
		ReMemberMarketDTO mb = new ReMemberMarketDTO();
		mb.setMemberId(3L);
		mb.setMarketId(5L);
		Map map=new HashMap();
		map.put("startRow", 0);
		map.put("endRow", 	10);
//		map.put("mainId", 1);
//		map.put("marketId", 5);
		int i=service.getTotal(map);
		System.out.println("个数"+i);
	}
	
	
}
