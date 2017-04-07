package com.gudeng.commerce.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.authority.sysmgr.entity.SysRegisterUser;
import com.gudeng.commerce.gd.authority.sysmgr.service.SysRegisterUserService;

public class SysRegisterUserServiceTest extends TestCase {

	private static SysRegisterUserService service;

	@Test
	public void test() {
		System.out.println(service);
		
	}

	@Test
	public void testGetTotal() throws Exception{
		System.out.println(service.getTotal(new HashMap<String ,Object>()));
	}
	
	@Test
	public void testGetList() throws Exception {
		System.out.println("starting..........");
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startRow", 5);
		map.put("endRow", 15);
		List<SysRegisterUser> list = service.getListSysRegisterUser(map);
//		List<DetectionDTO> detectionList = detectionService.getPricesList(map);
//
		for (int i = 0; i < list.size(); i++) {
			System.err.println(list.get(i).toString());
		}
		System.out.println("ending...........");
	}
	@Test
	public void testGetById() throws Exception {
		System.out.println("starting..........");
		String userId="11111";
		SysRegisterUser sysRegisterUser = service.get(userId);
		System.out.println(sysRegisterUser);
		System.out.println("ending...........");
	}
	
	@Test
	public void testAdd() throws Exception {
		System.out.println("starting..........");
//		DetectionDTO dto = new DetectionDTO();
//		dto.setProductName("");
//		dto.setMaketId(5L);
//		dto.setDetail("三翻四复斯蒂芬");
//		dto.setStatus("16");
//		dto.setDescription("阿双方发生的方式等多个地方");
//		System.out.println(dto);
//		detectionService.addDetectionDTO(dto);
		System.out.println("ending...........");
	}
	
	@Test
	public void testUpdate() throws Exception {
		System.out.println("starting..........");
//		DetectionDTO dto = new DetectionDTO();
//		dto.setProductName("");
//		dto.setMaketId(5L);
//		dto.setDetail("三翻四复斯蒂芬");
//		dto.setStatus("16");
//		dto.setId(6L);
//		dto.setDescription("阿双方发生的方式等多个地方");
//		System.out.println(dto);
//		detectionService.updateDetectionDTO(dto);
		System.out.println("ending...........");
	}

	@Test
	public void testCheckExsit() throws Exception {
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("id", 5);
//		System.out.println("starting..........");
//		detectionService.checkExsit(map);
//		System.out.println(detectionService.checkExsit(map)>0);
//		System.out.println("ending...........");
	}
	
	
	@Override
	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-auth/service/sysRegisterUserService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		SysRegisterUserServiceTest.service = (SysRegisterUserService) factory.create(
				SysRegisterUserService.class, url);
	}

}
