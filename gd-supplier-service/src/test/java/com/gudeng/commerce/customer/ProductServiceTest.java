package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.service.ProductService;

public class ProductServiceTest extends TestCase{
	
	private static ProductService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-supplier/service/productService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ProductService) factory.create(ProductService.class, url);				
	}
/*	public void testGetProductById() throws Exception{
		ProductService service = this.getService();
		ProductDto dto = service.getById("46736");
//		dto.setEditSign("1");
		if(null != dto){
			System.out.println(dto.getNickName() + ":" +dto.getProductName()+":"+dto.getAddress()+":"+dto.getDescription());
		}
		service.updateProduct(dto);
	}*/
	
	@Test
	public void testGetTotal() throws Exception{
		ProductService service = this.getService();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stateString", "4,5");
//		map.put("stateList", Arrays.asList(new String[]{"4","5"}));
		map.put("startRow", 0);
		map.put("endRow", 10);
		List<ProductDto> list = service.getList(map);
		System.out.println(list);
	}
	
/*	public void testAdd() throws Exception{
		ProductService service = this.getService();
		ProductDto dto = new ProductDto();
		dto.setCateId(Long.valueOf(0));
		dto.setBusinessId(Long.valueOf(0));
		dto.setProductName("测试mm");
		dto.setPrice(Double.valueOf(5000));
		dto.setState("0");
		dto.setUnit("0");
//		dto.setProvinceId(Long.valueOf(0));
//		dto.setCityId(Long.valueOf(0));
//		dto.setAreaId(Long.valueOf(0));
		dto.setAddress("那个地方");
		dto.setExpirationDateString(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATEHOURMIN));
		dto.setInfoLifeDay("aaaaa");
		dto.setCreateUserId("0");
		dto.setCreateTimeString(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATEHOURMIN));
		dto.setDescription("description");
		dto.setContent("xxxxxxx");
		dto.setKeyword("ccccc");
		dto.setLogisticsRemark("logisticsRemark");
		dto.setStockCount(Double.valueOf(223));
		dto.setMinBuyCount(Double.valueOf(121));
		ProductDto p = service.persistProduct(dto);
		System.out.println(p.getProductName());
	}*/
	
/*	public void testUpdate() throws Exception{
		ProductService service = this.getService();
		ProductDto dto = new ProductDto();
		dto.setProductId(Long.valueOf(9));
		dto.setTypeId(Long.valueOf(0));
		dto.setBusinessId(Long.valueOf(0));
		dto.setProductName("测试xx");
		dto.setPrice(Double.valueOf(5000));
		dto.setState(0);
		dto.setUnit("0");
		dto.setProvinceId(Long.valueOf(0));
		dto.setCityId(Long.valueOf(0));
		dto.setAreaId(Long.valueOf(0));
		dto.setAddress("那个地方");
		dto.setExpirationDateString(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATEHOURMIN));
		dto.setInfoLifeDay("aaaaa");
		dto.setCreateUserId(Long.valueOf(0));
		dto.setCreateTimeString(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_DATEHOURMIN));
		dto.setDescription("description");
		dto.setContent("xxxxxxx");
		dto.setKeyword("ccccc");
		dto.setLogisticsRemark("logisticsRemark");
		dto.setStockCount(Double.valueOf(999));
		dto.setMinBuyCount(Double.valueOf(999));
		System.out.println(service.updateProduct(dto));
		System.out.println();
	}*/
}
