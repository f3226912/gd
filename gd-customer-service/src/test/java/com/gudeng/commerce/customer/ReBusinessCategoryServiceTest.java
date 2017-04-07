package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessCategoryService;

public class ReBusinessCategoryServiceTest extends TestCase{
	private static ReBusinessCategoryService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/reBusinessCategoryService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ReBusinessCategoryService) factory.create(ReBusinessCategoryService.class, url);				
	}
 
	
//	public void testAddByDto()throws Exception {
//		ReBusinessCategoryService service=this.getService();
//		ReBusinessCategoryDTO mb = new ReBusinessCategoryDTO();
//		mb.setBusinessId(4L);
//		mb.setCategoryId(6L);
//		
//		int i=service.addReBusinessCategoryDTO(mb) ;
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
	
	
	public void testDeleteByDto()throws Exception {
		ReBusinessCategoryService service=this.getService();
		ReBusinessCategoryDTO mb = new ReBusinessCategoryDTO();
		mb.setBusinessId(4L);
//		mb.setCategoryId(5L);
		
		int i=service.deleteByBusinessId(3L) ;
		
//		int i=service.deleteReBusinessCategoryDTO(mb) ;
		if(i==1){
			System.out.println("删除dto成功");
		}
	}
//	
	
//	
//	public void testGetByDto()throws Exception {
//		ReBusinessCategoryService service=this.getService();
//		ReBusinessCategoryDTO mb = new ReBusinessCategoryDTO();
//		mb.setBusinessId(3L);
//		mb.setCategoryId(5L);
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 	10);
//		map.put("businessId", 3);
//		map.put("categoryId", 5);
//		List<ReBusinessCategoryDTO> i=service.getBySearch(map) ;
//		for(ReBusinessCategoryDTO rbm:i){
//			System.out.println(rbm.getBusinessId()+"...."+ rbm.getCategoryId());
//		}
//	}
//	
	
//	public void testgetTotal()throws Exception {
//		ReBusinessCategoryService service=this.getService();
//		ReBusinessCategoryDTO mb = new ReBusinessCategoryDTO();
//		mb.setBusinessId(3L);
//		mb.setCategoryId(5L);
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 	10);
//		map.put("businessId", 4);
//		map.put("categoryId", 5);
//		int i=service.getTotal(map);
//		System.out.println("个数"+i);
//	}
//	
	
}
