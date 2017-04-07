package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.service.DemoService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class DemoServiceTest extends TestCase{
	private static DemoService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/demoService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (DemoService) factory.create(DemoService.class, url);				
	}
	
//	@SuppressWarnings("static-access")
//	public void testGetDic()throws Exception {
//		DemoService service=this.getService();
//		DictDTO dto = service.getById("1");
//		if(null != dto){
//			System.out.println(dto.getId()+":"+dto.getName());
//		}
//	}
	
//	public void testGetByBirthday() throws Exception{
//		DemoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		String startdate="2000-03-01 00:00:00";
//		
//		Date st=DateUtil.formateDate(startdate,DateUtil.DATE_FORMAT_DATETIME);
//		
//		String enddate="2015-03-02 00:00:00";
//		
//		Date ed=DateUtil.formateDate(enddate,DateUtil.DATE_FORMAT_DATETIME);
//
//		
//		map.put("startdate",st);
//		map.put("enddate",ed);
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<DictDTO> dicList = service.getByBirthday(map);
//		
//		if(null != dicList){
//			for(DictDTO dto : dicList){
//				System.out.println(dto.getId() + " : " + dto.getName()+" : "+dto.getBirthday());
//			}
//		}
//	}
//	public void testGetByCondition() throws Exception{
//		DemoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("id", "1");
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<DictDTO> dicList = service.getByCondition(map);
//		if(null != dicList){
//			for(DictDTO dto : dicList){
//				System.out.println(dto.getId() + " : " + dto.getName()+" : "+dto.getBirthday());
//			}
//		}
//	}
//	public void testGetByName() throws Exception{
//		DemoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("name", "1");
//		map.put("startRow", 0);
//		map.put("endRow", 10);
//		List<DictDTO> dicList = service.getByName(map);
//		if(null != dicList){
//			for(DictDTO dto : dicList){
//				System.out.println(dto.getId() + ":" + dto.getName());
//			}
//		}
//	}
//	
//	public void testGetTotal() throws Exception{
//		DemoService service=this.getService();
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("id", "1");
//		System.out.println("total:" + service.getTotal(map));
//		
//	}
//	
	
//	public void testDelete()throws Exception {
//		DemoService service=this.getService();
//		 int i= service.deleteById("1");
//		if(i==1){
//			System.out.println("删除id为"+1+"的记录成功");
//		}
//	}
//	
//	
//	
//	public void testAddByDto()throws Exception {
//		DemoService service=this.getService();
//		DictDTO dto = new DictDTO();
//		dto.setId("1");
//		dto.setName("name2");
//		int i=service.addDictDTO(dto);
//
//		if(i==1){
//			System.out.println("增加dto成功");
//		}
//	}
//	public void testAddByMap()throws Exception {
//		DemoService service=this.getService();
//		Map	map=new HashMap();
//		map.put("id", "2");
//		map.put("name", "newName");
//		int i=service.addDictDTO(map);
//
//		if(i==1){
//			System.out.println("增加map成功");
//		}
//	}
//
//	public void testupdate()throws Exception {
//		DemoService service=this.getService();
//		
//		DictDTO dic=new DictDTO();
//		dic.setId("3");
//		dic.setName("update3");
//		int i=service.updateDictDTO(dic);
//		if(i==1){
//			System.out.println("修改成功");
//		}
//	}
	
}
