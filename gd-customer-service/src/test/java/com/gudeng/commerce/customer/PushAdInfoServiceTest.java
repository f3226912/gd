package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;
import com.gudeng.commerce.gd.customer.service.PushAdInfoService;
import com.gudeng.commerce.gd.customer.util.DateUtil;

public class PushAdInfoServiceTest extends TestCase {
	private static PushAdInfoService getService() throws MalformedURLException {
		String url = "http://localhost:8080/gd-customer/service/pushAdInfoService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (PushAdInfoService) factory.create(PushAdInfoService.class, url);
	}
	
	/*@SuppressWarnings("static-access")
	public void testInsertEntity() throws Exception {
		PushAdInfoService service = this.getService();
		PushAdInfoEntity ent = new PushAdInfoEntity();
		Date nt = new Date();
		ent.setAdCanal("02");
		ent.setAdName("测试2");
		ent.setState("02");
		ent.setAdUrl("http://2");
		ent.setAdLinkUrl("http://2");
		ent.setEndTime(nt);
		ent.setCreateUserId("20");
		ent.setCreateUserName("20");
		ent.setCreateTime(nt);
		ent.setUpdateUserId("20");
		ent.setUpdateUserName("20");
		ent.setUpdateTime(nt);
		ent.setProductId(20l);
		ent.setSort(2);
		ent.setStartTime(nt);
		Long i = service.insertEntity(ent);
		System.out.println(i);
	}
	
	@SuppressWarnings("static-access")
	public void testDeleteById() throws Exception {
		PushAdInfoService service = this.getService();
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
		PushAdInfoService service = this.getService();
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
		PushAdInfoService service = this.getService();
		//PushAdInfoDTO dto = new PushAdInfoDTO();
		PushAdInfoDTO obj = service.getById(1l);
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
		PushAdInfoService service = this.getService();
		//PushAdInfoDTO dto = new PushAdInfoDTO();
		List<PushAdInfoDTO> objList = new ArrayList<PushAdInfoDTO>();
		PushAdInfoDTO obj = service.getById(1l);
		obj.setAdName("aaaaa");
		obj.setAdUrl("bbbb");
		obj.setUpdateTimeStr(DateUtil.getNow().toString());
		objList.add(obj);
		PushAdInfoDTO obj2 = service.getById(2l);
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
		PushAdInfoService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		System.out.println("total:" + service.getTotal(map));
	}*/
	
	@SuppressWarnings("static-access")
	public void testGetById() throws Exception {
		PushAdInfoService service = this.getService();
		PushAdInfoDTO dto = service.getById(2l);
		if (null != dto) {
			System.out.println(dto.getId() + ":" + dto.getAdName());
		}
	}
	 
	
	/*@SuppressWarnings("static-access")
	public void testGetListByConditionPage() throws Exception {
		PushAdInfoService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adName", "轮播");
		map.put("startRow", 0);
		map.put("endRow", 10);
		List<PushAdInfoDTO> pList = service.getListByConditionPage(map);
		System.out.println(pList);
		if (null != pList) {
			for (PushAdInfoDTO dto : pList) {
				System.out.println(dto.getId() + " : " + dto.getAdName()
						+ " : " + dto.getAdUrl());
			}
		}
	}

	@SuppressWarnings("static-access")
	public void testGetByCondition() throws Exception {
		PushAdInfoService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adName", "轮播");
		map.put("startRow", 0);
		map.put("endRow", 10);
		List<PushAdInfoDTO> pList = service.getListByCondition(map);
		System.out.println(pList);
		if (null != pList) {
			for (PushAdInfoDTO dto : pList) {
				System.out.println(dto.getId() + " : " + dto.getAdName()
						+ " : " + dto.getAdUrl());
			}
		}
	}*/

	

	
	
	/*@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void testAddByMap() throws Exception {
		PushAdInfoService service = this.getService();
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
		int i = service.addPushAdInfoDTO(map);
		if (i == 1) {
			System.out.println("增加map成功");
		}else{
			System.out.println("增加map失败");
		}
	}*/

	

}
