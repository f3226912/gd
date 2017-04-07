package com.gudeng.commerce.gd.m.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.service.AdAdvertiseService;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.m.service.AdToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;
import com.gudeng.commerce.gd.supplier.service.RefCateSupNpsService;

public class AdToolServiceImpl implements AdToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private static AdAdvertiseService adAdvertiseService;
	
	private static ProductCategoryService productCategoryService;
	
	private static BusinessBaseinfoService businessBaseinfoService;
	
	private static RefCateSupNpsService refCateSupNpsService;


	/**
	 * 
	 * @param
	 * @return
	 */
	protected AdAdvertiseService getHessianAdAdvertiseService() throws MalformedURLException {
		if(adAdvertiseService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			adAdvertiseService = (AdAdvertiseService) factory.create(AdAdvertiseService.class, gdProperties.getProperties().getProperty("gd.adAdvertiseService.url"));
		}
		return adAdvertiseService;
	}
	
	protected ProductCategoryService getHessianProductCategoryService() throws MalformedURLException {
		if(productCategoryService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(ProductCategoryService.class, gdProperties.getProperties().getProperty("gd.productCategoryService.url"));
		}
		return productCategoryService;
	}	
	
	protected BusinessBaseinfoService getHessianBusinessBaseinfoService() throws MalformedURLException {
		if(businessBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, gdProperties.getProperties().getProperty("gd.businessBaseinfoService.url"));
		}
		return businessBaseinfoService;
	}	
	
	protected RefCateSupNpsService getHessianRefCateSupNpsService() throws MalformedURLException {
		if(refCateSupNpsService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			refCateSupNpsService = (RefCateSupNpsService) factory.create(RefCateSupNpsService.class, gdProperties.getProperties().getProperty("gd.refCateSupNpsService.url"));
		}
		return refCateSupNpsService;
	}		
	
	@Override
	public List<AdAdvertiseDTO> getAdByMenuId(int menuId) throws Exception {
		return getHessianAdAdvertiseService().getAdByMenuId(menuId);
	}

	@Override
	public List<ProductCategoryDTO> getTopCategoryDTO(Long marketId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianProductCategoryService().listTopProductCategoryByMarketId(marketId);
	}

	/**
	 * 获取商铺的主营分类
	 */
	@Override
	public List<ReBusinessCategoryDTO> getShopCategoryList(Long businessId, int start, int size) throws Exception {
		return getHessianBusinessBaseinfoService().getCategoryList(businessId, start, size);
	}

	@Override
	public Long[] getGysCateByShopCate(List<Long> cateIds)  throws Exception  {
		// TODO Auto-generated method stub
		return getHessianRefCateSupNpsService().getRefCateSupNpsForHHY(cateIds);
	}

	@Override
	public List<AdAdvertiseDTO> queryAdvertiseList(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return getHessianAdAdvertiseService().queryAdvertiseList(params);
	}

}
