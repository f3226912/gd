package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class BusinessBaseinfoServiceTest extends TestCase{
	private static BusinessBaseinfoService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/businessBaseinfoService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);				
	}
	
//	@SuppressWarnings("static-access")
//	public void testGetDic()throws Exception {
//		BusinessBaseinfoService service=this.getService();
//		BusinessBaseinfoDTO dto = service.getById("2");
//		if(null != dto){
//			System.out.println(dto.getBusinessId()+":"+dto.getName());
//		}
//	}
	 
//	public void testGetAll() throws Exception{
//		BusinessBaseinfoService service=this.getService();
//		Map map=new HashMap();
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<BusinessBaseinfoDTO> list=service.getBySearch(map);
//		for(BusinessBaseinfoDTO dto:list){
//			System.out.println(dto.getBusinessId()+":"+dto.getName());
//		}
//	}
	
//	
//	public void testGetTotal() throws Exception{
//		BusinessBaseinfoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("id", "1");
//		System.out.println("total:" + service.getTotal(map));
//		
//	}
//	
	
//	public void testDelete()throws Exception {
//	BusinessBaseinfoService service=this.getService();
//		 int i= service.deleteById("0");
//		if(i==1){
//			System.out.println("删除id为"+1+"的记录成功");
//		}
//	}
	
//	public void testAddByDto()throws Exception {
//		BusinessBaseinfoService service=this.getService();
//		BusinessBaseinfoDTO mb = new BusinessBaseinfoDTO();
//		mb.setName("点点电商公司");
//		mb.setShopsName("点点农产品小店");
//		mb.setUserId(24L);
//		mb.setCreateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
//		int i=service.addBusinessBaseinfoDTO(mb) ;
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
//	
//	public void testAddByEnt()throws Exception {
//		BusinessBaseinfoService service=this.getService();
//		BusinessBaseinfoEntity mb = new BusinessBaseinfoEntity();
//		mb.setAccount("testAccount1");
//		mb.setPassword("123456");
//		mb.setCreateUserId("123"); 
//		Long i=service.addBusinessBaseinfoEnt(mb);
//	 
//			System.out.println("。。。。。。。。。"+ i);
//	 
//	}
	
	public void testupdate()throws Exception {
		BusinessBaseinfoService service=this.getService();
		BusinessBaseinfoDTO mb = service.getById("2");
		mb.setName("点点电商公司修改");
		mb.setShopsName("点点农产品小店修改");
		mb.setCreateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		mb.setUpdateTime_string(DateUtil.toString(new Date(), DateUtil.DATE_FORMAT_DATETIME));
		int i=service.updateBusinessBaseinfoDTO(mb) ;
		if(i==1){
			System.out.println("修改dto成功");
		}
	}
	
}
