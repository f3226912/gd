package com.gudeng.commerce.gd.supplier.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.PricesDTO;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {
//		"classpath*:conf/spring/spring-hessian.xml",
//		"classpath*:conf/spring/spring-servlet.xml",
//		"classpath*:conf/spring/spring-cache.xml",
//		"classpath*:conf/spring/spring-da.xml",
//		"classpath*:conf/spring/spring-exception.xml",
//		"classpath*:conf/spring/spring-impl.xml",
//		"classpath*:conf/spring/spring-res.xml" })
public class PricesServiceTest extends TestCase {

	private static PricesService pricesService;

	@Test
	public void test() {
		System.out.println("111111111111");
		System.out.println(pricesService);
	}

	@Test
	public void testgetPricesList() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("startRow", 5);
		map.put("endRow", 15);
		
		List<PricesDTO> pricesList = pricesService.getPricesList(map);

		System.out.println("starting..........");
		for (int i = 0; i < pricesList.size(); i++) {
			System.err.println(pricesList.get(i).toString());
		}
		System.out.println("ending...........");
	}
	@Test
	public void testgetById() throws Exception {
		System.out.println("starting..........");
		PricesDTO dto = pricesService.getById("5");
		System.out.println(dto);
		System.out.println("ending...........");
	}

	@Override
	protected void setUp() throws Exception {
		String url = "http://127.0.0.1:8080/gd-supplier/service/pricesService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		PricesServiceTest.pricesService = (PricesService) factory.create(
				PricesService.class, url);
	}

	
	
}
