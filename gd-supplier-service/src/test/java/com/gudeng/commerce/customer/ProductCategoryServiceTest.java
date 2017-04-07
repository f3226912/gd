package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;

public class ProductCategoryServiceTest extends TestCase{
	private static ProductCategoryService getProductCategoryService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-supplier/service/productCategoryService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ProductCategoryService) factory.create(ProductCategoryService.class, url);				
	}
	
	/*@SuppressWarnings("static-access")
	public void testPersistProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		ProductCategoryEntity pc = new ProductCategoryEntity();
		pc.setCateName("粮油作物");
		pc.setTypeIcon("/12232/12323.jpg");
		pc.setOrderNum(1);
		pc.setCreateUserId("2");
		pc.setParentId(8l);
		System.out.println("#################:"+productCategoryService.persistProductCategory(pc));
	}
	
	@SuppressWarnings("static-access")
	public void testDeleteProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		System.out.println("delete:"+productCategoryService.deleteProductCategory(7l));
	}
	
	@SuppressWarnings("static-access")
	public void testuUpdateProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		ProductCategoryDTO pcDTO = new ProductCategoryDTO();
		pcDTO.setCategoryId(7l);
		pcDTO.setCateName("种植果蔬222");
		System.out.println("update:"+productCategoryService.updateProductCategory(pcDTO));
	}
	
	@SuppressWarnings("static-access")
	public void testGetProductCategoryByName()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		ProductCategoryDTO dto = productCategoryService.getProductCategoryByName("种植果蔬");
		if(null != dto){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}*/
	
	@SuppressWarnings("static-access")
	public void testgetProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		ProductCategoryDTO dto = productCategoryService.getProductCategory(1l);
		if(null != dto){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
		
	}
	
	public void testListProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.listProductCategory(2l);
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	public void testListProductCategoryAll()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.listAllProductCategory();
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	public void testListTopProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.listTopProductCategory();
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	@SuppressWarnings("static-access")
	public void testListTopProductCategoryByMarketId()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.listTopProductCategoryByMarketId(1L);
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	@SuppressWarnings("static-access")
	public void testgetChildProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.getChildProductCategory(1L);
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	@SuppressWarnings("static-access")
	public void testgetChildProductCategoryByMarketId()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.getChildProductCategoryByMarketId(1L,1L);
		for(ProductCategoryDTO dto : list){
			System.out.println(dto.getCategoryId()+":"+dto.getCateName());
		}
	}
	
	@SuppressWarnings("static-access")
	public void testGetChildProductCategory()throws Exception {
		ProductCategoryService productCategoryService = getProductCategoryService();
		List<ProductCategoryDTO> list = productCategoryService.listAllProductCategoryByMarketId(1l);
		for(ProductCategoryDTO dto : list){
			System.out.println("1:" + dto.getCategoryId()+":"+dto.getCateName());
			if(null != dto.getChildList() && dto.getChildList().size() > 0){
				for(ProductCategoryDTO dto2 : dto.getChildList()){
					System.out.println("2:" + dto2.getCategoryId()+":"+dto2.getCateName());
					if(null != dto2.getChildList() && dto2.getChildList().size() > 0){
						for(ProductCategoryDTO dto3 : dto2.getChildList()){
							System.out.println("3:" + dto3.getCategoryId()+":"+dto3.getCateName());
						}
					}
				}
			}
		}
	}
	
	
}
