package com.gudeng.commerce.left_right;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;

public class ProductCategoryProducer extends TestCase{
	
	private  ProductCategoryService getProductCategoryService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-supplier/service/productCategoryService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ProductCategoryService) factory.create(ProductCategoryService.class, url);				
	}
	
	@Test
	public void testCategoryList() throws Exception{
		final ProductCategoryService service = this.getProductCategoryService();
		List<ProductCategoryDTO> toplist = service.listTopProductCategoryByMarketId(1l);
		CategoryCallBack callback = new CategoryCallBack() {
			@Override
			public List<ProductCategoryDTO> getChildProductCategory(Long id) {
				return service.getChildProductCategory(id);
			}

			@Override
			public List<ProductCategoryDTO> getChildProductCategoryByMarketId(Long id, Long marketId) {
				return service.getChildProductCategoryByMarketId(id, marketId);
			}
		};
		List<ProductCategoryDTO> treeList = getAllCategorysInTreeOrder(toplist, callback, 1l);
		
//		traverse(treeList);
		LeftRightGenerator generator = new LeftRightGenerator();
		generator.handle(treeList);
	}
	
	public List<ProductCategoryDTO> getAllCategorysInTreeOrder(List<ProductCategoryDTO> toplist, CategoryCallBack callback, Long marketId) throws MalformedURLException{
		List<ProductCategoryDTO> result = new ArrayList<ProductCategoryDTO>();
		ProductCategoryDTO top = null , second = null, third = null;
		List<ProductCategoryDTO> secondlist = null, thirdlist = null;
		for(Iterator<ProductCategoryDTO> it = toplist.iterator(); it.hasNext();){
			top = it.next() ;
			result.add(top);
//			secondlist = callback.getChildProductCategory(top.getCategoryId());
			secondlist = callback.getChildProductCategoryByMarketId(top.getCategoryId(), marketId);
			for(Iterator<ProductCategoryDTO> it2 = secondlist.iterator(); it2.hasNext();){
				second = it2.next();
				result.add(second);
				thirdlist = callback.getChildProductCategory(second.getCategoryId());
				for(Iterator<ProductCategoryDTO> it3 = thirdlist.iterator(); it3.hasNext();){
					third = it3.next();
					result.add(third);
				}
			}
		}
		return result;
	}
	
	public void traverse(List<ProductCategoryDTO> list){
		ProductCategoryDTO current = null;
		for(Iterator<ProductCategoryDTO> it = list.iterator(); it.hasNext();){
			current = it.next() ;
			System.out.println("分类名称 : " + current.getCateName() + ",    分类id : " + 
					current.getCategoryId() + ",    父分类id : " + current.getParentId());
			
		}
	}
	
	
}
