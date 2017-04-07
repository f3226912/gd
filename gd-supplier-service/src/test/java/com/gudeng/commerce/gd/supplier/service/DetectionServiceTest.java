package com.gudeng.commerce.gd.supplier.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.DetectionDTO;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//		"classpath*:conf/spring/spring-hessian.xml",
//		"classpath*:conf/spring/spring-servlet.xml",
//		"classpath*:conf/spring/spring-cache.xml",
//		"classpath*:conf/spring/spring-da.xml",
//		"classpath*:conf/spring/spring-exception.xml",
//		"classpath*:conf/spring/spring-impl.xml",
//		"classpath*:conf/spring/spring-res.xml" })
public class DetectionServiceTest extends TestCase {

	private static DetectionService detectionService;

	@Test
	public void test() {
		System.out.println("111111111111");
		System.out.println(detectionService);
	}

	@Test
	public void testGetList() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startRow", 5);
		map.put("endRow", 15);
		
		List<DetectionDTO> detectionList = detectionService.getDetectionList(map);

		System.out.println("starting..........");
		for (int i = 0; i < detectionList.size(); i++) {
			System.err.println(detectionList.get(i).toString());
		}
		System.out.println("ending...........");
	}
	@Test
	public void testGetById() throws Exception {
		System.out.println("starting..........");
		DetectionDTO dto = detectionService.getById("5");
		System.out.println(dto);
		System.out.println("ending...........");
	}
	
	@Test
	public void testAdd() throws Exception {
		System.out.println("starting..........");
		DetectionDTO dto = new DetectionDTO();
		dto.setProductName("");
		dto.setMaketId(5L);
		dto.setDetail("三翻四复斯蒂芬");
		dto.setStatus("16");
		dto.setDescription("阿双方发生的方式等多个地方");
		System.out.println(dto);
		detectionService.addDetectionDTO(dto);
		System.out.println("ending...........");
	}
	
	@Test
	public void testUpdate() throws Exception {
		System.out.println("starting..........");
		DetectionDTO dto = new DetectionDTO();
		dto.setProductName("");
		dto.setMaketId(5L);
		dto.setDetail("三翻四复斯蒂芬");
		dto.setStatus("16");
		dto.setId(6L);
		dto.setDescription("阿双方发生的方式等多个地方");
		System.out.println(dto);
		detectionService.updateDetectionDTO(dto);
		System.out.println("ending...........");
	}

	@Test
	public void testCheckExsit() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", 5);
		System.out.println("starting..........");
		detectionService.checkExsit(map);
		System.out.println(detectionService.checkExsit(map)>0);
		System.out.println("ending...........");
	}
	
	
	@Override
	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-supplier/service/detectionService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		DetectionServiceTest.detectionService = (DetectionService) factory.create(
				DetectionService.class, url);
	}

}
