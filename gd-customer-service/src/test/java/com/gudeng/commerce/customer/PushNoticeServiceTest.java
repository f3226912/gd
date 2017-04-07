package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Date;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushNoticeDTO;
import com.gudeng.commerce.gd.customer.entity.PushNoticeEntity;
import com.gudeng.commerce.gd.customer.service.PushNoticeService;

public class PushNoticeServiceTest extends TestCase {
	private static PushNoticeService getService() throws MalformedURLException {
		String url = "http://localhost:8080/gd-customer/service/pushNoticeService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (PushNoticeService) factory.create(PushNoticeService.class, url);
	}
	
	@SuppressWarnings("static-access")
	public void testInsertEntity() throws Exception {
		PushNoticeService service = this.getService();
		PushNoticeEntity ent = new PushNoticeEntity();
		Date nt = new Date();
//		ent.setAdCanal("02");
//		ent.setAdName("测试2");
//		ent.setState("02");
//		ent.setAdUrl("http://2");
//		ent.setAdLinkUrl("http://2");
//		ent.setEndTime(nt);
//		ent.setCreateUserId("20");
//		ent.setCreateUserName("20");
//		ent.setCreateTime(nt);
//		ent.setUpdateUserId("20");
//		ent.setUpdateUserName("20");
//		ent.setUpdateTime(nt);
//		ent.setProductId(20l);
//		ent.setSort(2);
//		ent.setStartTime(nt);
		Long i = service.insertEntity(ent);
		System.out.println(i);
	}
	/*
	@SuppressWarnings("static-access")
	public void testDeleteById() throws Exception {
		PushNoticeService service = this.getService();
		Long id = 1l;
		int i = service.deleteById(id);
		if (i == 1) {
			System.out.println("删除id为" + id + "的记录成功");
		}else{
			System.out.println("删除失败");
		}
	}
	
	@SuppressWarnings("static-access")
	public void testBatchDeleteById() throws Exception {
		PushNoticeService service = this.getService();
		List<Long> idList = new ArrayList<Long>();
		idList.add(1l);
		idList.add(2l);
		int i = service.batchDeleteById(idList);
		if (i == 1) {
			System.out.println("删除id为" + i + "的记录成功");
		}else{
			System.out.println("删除失败");
		}
	}
	
	@SuppressWarnings("static-access")
	public void testUpdateDTO() throws Exception {
		PushNoticeService service = this.getService();
		//PushNoticeDTO dto = new PushNoticeDTO();
		PushNoticeDTO obj = service.getById(1l);
		obj.setAdName("aaaaa");
		obj.setAdUrl("bbbb");
		obj.setUpdateTimeStr(DateUtil.getNow().toString());
		int i = service.updateDTO(obj);
		if (i == 1) {
			System.out.println("修改成功");
		}else{
			System.out.println("修改失败");
		}
	}
	
	@SuppressWarnings("static-access")
	public void testBatchUpdateDTO() throws Exception {
		PushNoticeService service = this.getService();
		//PushNoticeDTO dto = new PushNoticeDTO();
		List<PushNoticeDTO> objList = new ArrayList<PushNoticeDTO>();
		PushNoticeDTO obj = service.getById(1l);
		obj.setAdName("aaaaa");
		obj.setAdUrl("bbbb");
		obj.setUpdateTimeStr(DateUtil.getNow().toString());
		objList.add(obj);
		PushNoticeDTO obj2 = service.getById(2l);
		obj2.setAdName("aaaaa");
		obj2.setAdUrl("bbbb");
		obj2.setUpdateTimeStr(DateUtil.getNow().toString());
		objList.add(obj2);
		int i = service.batchUpdateDTO(objList);
		if (i == 1) {
			System.out.println("修改成功");
		}else{
			System.out.println("修改失败");
		}
	}
	
	@SuppressWarnings("static-access")
	public void testGetTotal() throws Exception {
		PushNoticeService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		System.out.println("total:" + service.getTotal(map));
	}*/
	
	@SuppressWarnings("static-access")
	public void testGetById() throws Exception {
		PushNoticeService service = this.getService();
		PushNoticeDTO dto = service.getById(2l);
		if (null != dto) {
			System.out.println(dto.getId() + ":" + dto.getTitle());
		}
	}
	 
	
	/*@SuppressWarnings("static-access")
	public void testGetListByConditionPage() throws Exception {
		PushNoticeService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adName", "轮播");
		map.put("startRow", 0);
		map.put("endRow", 10);
		List<PushNoticeDTO> pList = service.getListByConditionPage(map);
		System.out.println(pList);
		if (null != pList) {
			for (PushNoticeDTO dto : pList) {
				System.out.println(dto.getId() + " : " + dto.getAdName()
						+ " : " + dto.getAdUrl());
			}
		}
	}

	@SuppressWarnings("static-access")
	public void testGetByCondition() throws Exception {
		PushNoticeService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adName", "轮播");
		map.put("startRow", 0);
		map.put("endRow", 10);
		List<PushNoticeDTO> pList = service.getListByCondition(map);
		System.out.println(pList);
		if (null != pList) {
			for (PushNoticeDTO dto : pList) {
				System.out.println(dto.getId() + " : " + dto.getAdName()
						+ " : " + dto.getAdUrl());
			}
		}
	}*/

	

	
	
	/*@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void testAddByMap() throws Exception {
		PushNoticeService service = this.getService();
		Map map = new HashMap();
		map.put("adCanal", "01");
		map.put("adName", "轮播");
		map.put("state", "01");
		map.put("adUrl", "http://1");
		map.put("adLinkUrl", "http://1");
		map.put("endTimeStr", DateUtil.getNow().toString());
		map.put("createUserId", 10);
		map.put("createTimeStr", DateUtil.getNow().toString());
		map.put("updateUserId", 10);
		map.put("updateTimeStr", DateUtil.getNow().toString());
		map.put("productId", 10);
		map.put("sort", 10);
		map.put("startTimeStr", DateUtil.getNow().toString());
		int i = service.addPushNoticeDTO(map);
		if (i == 1) {
			System.out.println("增加map成功");
		}else{
			System.out.println("增加map失败");
		}
	}*/

	

}
