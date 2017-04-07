package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AuditInfoDTO;
import com.gudeng.commerce.gd.customer.service.AuditInfoService;

public class AuditInfoServiceTest extends TestCase{
	private static AuditInfoService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/auditInfoService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (AuditInfoService) factory.create(AuditInfoService.class, url);				
	}
 
	
	public void testAddByDto()throws Exception {
		AuditInfoService service=this.getService();
		AuditInfoDTO mb = new AuditInfoDTO();
		mb.setMainId(2L);
		mb.setType("2");
		mb.setStatus("1");
		mb.setAuditTime_string("2015-03-02 00:00:00");
		mb.setMemberId("1");
		mb.setMemberName("姓名");
		mb.setReason("照片不对");
		mb.setCreateTime_string("2015-03-02 00:00:00");
		mb.setCreateUserId("2");
		mb.setUpdateUserId("2");
		mb.setUpdateTime_string("2015-03-02 00:00:00");
		int i=service.addAuditInfoDTO(mb) ;
		if(i==1){
			System.out.println("增加dto成功");
		}
	}

	
//	public void testDeleteByDto()throws Exception {
//		AuditInfoService service=this.getService();
//		AuditInfoDTO mb = new AuditInfoDTO();
//		
//		int i=service.deleteById("1") ;
//		if(i==1){
//			System.out.println("删除dto成功");
//		}
//	}
//	
	
	
	public void testGetByDto()throws Exception {
		AuditInfoService service=this.getService();
		AuditInfoDTO mb = new AuditInfoDTO();
		mb.setType("1");
		mb.setStatus("1");
		Map map=new HashMap();
		map.put("startRow", 0);
		map.put("endRow", 	10);
		map.put("bussinessId", 3);
		map.put("marketId", 5);
		List<AuditInfoDTO> i=service.getBySearch(map) ;
		for(AuditInfoDTO rbm:i){
			System.out.println(rbm.getType()+"...."+ rbm.getMemberName());
		}
	}
	
	
	
	public void testgetTotal()throws Exception {
		AuditInfoService service=this.getService();
		AuditInfoDTO mb = new AuditInfoDTO();
		mb.setType("1");
		mb.setStatus("1");
		Map map=new HashMap();
		map.put("startRow", 0);
		map.put("endRow", 	10);
		map.put("mainId", 2);
//		map.put("marketId", 5);
		int i=service.getTotal(map);
		System.out.println("个数"+i);
	}
	
	
}
