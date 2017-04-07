package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;
import com.gudeng.commerce.gd.supplier.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.api.dto.AppProductCategoryDTO;
import com.gudeng.commerce.gd.api.service.ProCategoryService;
import com.gudeng.commerce.gd.api.service.ProductToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.util.BeanUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.UserSettingPropertyUtils;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

public class ProCategoryServiceImpl implements ProCategoryService {
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(ProCategoryServiceImpl.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	
	
	

	private static ProductCategoryService productCategoryService;
	@Autowired
	private UsercollectProductCategoryToolService usercollectProductCategoryToolService;
	@Autowired
	private ProductToolService productToolService;
	@Autowired
	private UsercollectProductToolService usercollectProductToolService;
	@Override
	public List<ProductCategoryDTO> getProductCategory(Long marketId) throws Exception {
		return hessianCategoryService().listProductCategory(marketId);
	}

	@Override
	public Long persistProductCategory(ProductCategoryEntity dto)
			throws Exception {

		return hessianCategoryService().persistProductCategory(dto);
	}

	@Override
	public ProductCategoryEntity updateProductCategory(
			ProductCategoryEntity productCategoryEntity) throws Exception {

		return hessianCategoryService().updateProductCategory(
				productCategoryEntity);
	}

	@Override
	public String deleteProductCategory(Long id) throws Exception {

		return hessianCategoryService().deleteProductCategory(id);
	}

	private ProductCategoryService hessianCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getProductCategoryUrl();
		if (productCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productCategoryService = (ProductCategoryService) factory.create(
					ProductCategoryService.class, hessianUrl);
		}
		return productCategoryService;
	}

	@Override
	public List<AppProductCategoryDTO> listTopProductCategory(Long userId,Long marketId)
			throws Exception {
		List<AppProductCategoryDTO> retList = new ArrayList<>(10);
		// 已经关注的所有
		List<UsercollectProductCategoryDTO> focusedList = usercollectProductCategoryToolService
				.getFocusCategory(userId);
		List<Long> allFocusCateId = new ArrayList<>();
		for (UsercollectProductCategoryDTO singleFoucsCate : focusedList) {
			allFocusCateId.add(singleFoucsCate.getProductCategoryId());
		}

		List<ProductCategoryDTO> topList = hessianCategoryService().listTopProductCategoryByMarketId(marketId);
				
		Integer hasFocusedInteger = 0;
		for (ProductCategoryDTO productCategoryDTO : topList) {
			AppProductCategoryDTO appProductCategoryDTO = new AppProductCategoryDTO();
			BeanUtils.copyProperties(appProductCategoryDTO, productCategoryDTO);
			if (allFocusCateId.contains(productCategoryDTO.getCategoryId())) {
				hasFocusedInteger = 1;
			} else {
				hasFocusedInteger = 0;
			}
			appProductCategoryDTO.setHasFocused(hasFocusedInteger);
			retList.add(appProductCategoryDTO);
		}
		
		
		
		//增加服务器前缀地址
		if(retList != null && retList.size() > 0){
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			for(int i=0, len=retList.size(); i<len; i++){
				AppProductCategoryDTO dto = retList.get(i);
				dto.setTypeIcon(imageHost + dto.getTypeIcon());
			}
		}
		
		return retList;
	}

	@Override
	public List<AppProductCategoryDTO> getChildProductCategory(Long id,
			Long userId,Long marketId) throws Exception {
		List<AppProductCategoryDTO> retList = new ArrayList<>(10);
		List<Long> allFocusCateId = new ArrayList<>();
		List<UsercollectProductCategoryDTO> focusedList = usercollectProductCategoryToolService
				.getFocusCategory(userId);
		for (UsercollectProductCategoryDTO singleFoucsCate : focusedList) {
			allFocusCateId.add(singleFoucsCate.getProductCategoryId());
		}
		//获取二级类目
		List<ProductCategoryDTO> childList = hessianCategoryService()
				.getChildProductCategoryByMarketId(id,marketId);
		Integer hasFocusedInteger = 0;
		
		
		
		if(retList != null && retList.size() > 0){
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			for(int i=0, len=retList.size(); i<len; i++){
				AppProductCategoryDTO dto = retList.get(i);
				dto.setTypeIcon(imageHost + dto.getTypeIcon());
			}
		}
		
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		
		
		for (ProductCategoryDTO productCategoryDTO : childList) {
			AppProductCategoryDTO appProductCategoryDTO = new AppProductCategoryDTO();
			BeanUtils.copyProperties(appProductCategoryDTO, productCategoryDTO);
			if (allFocusCateId.contains(productCategoryDTO.getCategoryId())) {
				hasFocusedInteger = 1;
			} else {
				hasFocusedInteger = 0;
			}
			appProductCategoryDTO.setHasFocused(hasFocusedInteger);
			
			appProductCategoryDTO.setTypeIcon(imageHost + productCategoryDTO.getTypeIcon());
			retList.add(appProductCategoryDTO);
		}
		return retList;
	}

	public List<AppProductCategoryDTO> listTopCateWithSub() {
		List<AppProductCategoryDTO> retList = new ArrayList<>(10);
		// try {
		// List<ProductCategoryDTO> topList = listTopProductCategory();
		// for (ProductCategoryDTO productCategoryDTO : topList) {
		// Long categoryId = productCategoryDTO.getCategoryId();
		// List<ProductCategoryDTO> subCateList =
		// getChildProductCategory(categoryId);
		// AppProductCategoryDTO appProductCategoryDTO = new
		// AppProductCategoryDTO();
		// BeanUtils.copyProperties(appProductCategoryDTO,
		// productCategoryDTO);
		// appProductCategoryDTO.setSubCategory(subCateList);
		// retList.add(appProductCategoryDTO);
		// }
		//
		// } catch (Exception e) {
		//
		// logger.info("获取品种列表出错", e);
		// }
		return retList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getTotalCateList(Long userId,Long marketId) {
		List<AppProductCategoryDTO> topList =null;
		List<UsercollectProductCategoryDTO> focusedList = usercollectProductCategoryToolService
				.getFocusCategory(userId);
		//用户所有的关注的分类的cateId
		List<Long> allFocusCateId = new ArrayList<>();
		for (UsercollectProductCategoryDTO singleFoucsCate : focusedList) {
			allFocusCateId.add(singleFoucsCate.getProductCategoryId());
		}
		
		try {
			//查询顶级分类
			topList= listTopProductCategory(userId,marketId);
			for (AppProductCategoryDTO topCategory : topList) {
				//查询第二级分类
				List<AppProductCategoryDTO> secondList = getChildProductCategory(
						topCategory.getCategoryId(), userId,marketId);
				for (AppProductCategoryDTO secondCategoryDTO : secondList) {
					Long secondCateId = secondCategoryDTO.getCategoryId();
					
					
					//查询第3级分类
					List<AppProductCategoryDTO> thirdList = getChildProductCategory(
							secondCategoryDTO.getCategoryId(), userId,marketId);
					
						for (AppProductCategoryDTO thirdCate: thirdList) {
							if (allFocusCateId.contains(thirdCate.getCategoryId()))
							{
								thirdCate.setHasFocused(1);
							}
						}
					
					
					if (allFocusCateId.contains(secondCateId))
					{
						secondCategoryDTO.setHasFocused(1);
					}
					secondCategoryDTO.setSubCategory(thirdList);
				}
				if (allFocusCateId.contains(topCategory.getCategoryId()))
				{
					topCategory.setHasFocused(1);
				}
				topCategory.setSubCategory(secondList);
			}
		} catch (Exception e) {
			logger.warn("获取分类总表失败", e);
		}
		return topList;
	}

	@Override
	public ProductCategoryDTO getProductCategoryByName(String name) throws MalformedURLException {
		return hessianCategoryService().getProductCategoryByName(name);
	}

	@Override
	public List<ProductCategoryDTO> getListTopProductCategoryByMarketId(
			Long marketId) throws Exception {
		return hessianCategoryService().listTopProductCategoryByMarketId(marketId);
	}

}
