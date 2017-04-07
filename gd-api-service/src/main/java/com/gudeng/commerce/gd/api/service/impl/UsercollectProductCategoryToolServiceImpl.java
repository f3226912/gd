package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.UsercollectProductCategoryToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectProductCategoryService;


@Service
public class UsercollectProductCategoryToolServiceImpl implements UsercollectProductCategoryToolService{
	
	private static Logger logger = LoggerFactory.getLogger(UsercollectProductServiceImpl.class);
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	@Autowired
	public UsercollectProductToolService usercollectProductToolService;
	@Autowired
	public ProductToolServiceImpl productToolServiceImpl;

	private static UsercollectProductCategoryService usercollectProductCategoryService;
	
	@Override
	public Long focus(Long categoryId,Long userId) throws Exception {
			
		List<UsercollectProductCategoryDTO> focusedList = getUserProductCategoryService()
				.getFocusCategory(userId);
		List<Long> allFocusCateId = new ArrayList<>();
		for (UsercollectProductCategoryDTO singleFoucsCate : focusedList) {
			allFocusCateId.add(singleFoucsCate.getProductCategoryId());
		}
		//已经关注了,就不用再添加数据
		if (allFocusCateId.contains(categoryId)) {
			logger.error("已经关注了,就不用再添加数据  categoryId   "+categoryId);
			return null;
		}
		else {
			  getUserProductCategoryService().focus(categoryId,userId);
		}
		
		return null;
	}
	
	public Long singleFocus(Long categoryId,Long userId) throws MalformedURLException {
		
		List<UsercollectProductCategoryDTO> focusedList = getUserProductCategoryService()
				.getFocusCategory(userId);
		List<Long> allFocusCateId = new ArrayList<>();
		for (UsercollectProductCategoryDTO singleFoucsCate : focusedList) {
			allFocusCateId.add(singleFoucsCate.getProductCategoryId());
		}
		//已经关注了,就不用再添加数据
		if (allFocusCateId.contains(categoryId)) {
			logger.error("已经关注了,就不用再添加数据  categoryId   "+categoryId);
			return null;
		}
		else {
			  getUserProductCategoryService().focus(categoryId,userId);
		}
		
		return null;
	}
	
	

	@Override
	public void cancelFocus(Long categoryId, Long userId)  throws Exception{
		getUserProductCategoryService().cancelFocus(categoryId, userId);
	}
	
	@Override
	public void batchCancelFocus(Long userId, String categoryIds) throws Exception{
		String[] categoryIdArr = categoryIds.split(",");
		for(int i=0, len=categoryIdArr.length; i<len; i++){
			Long categoryId = Long.parseLong(categoryIdArr[i]);
			cancelFocus(categoryId, userId);
		}
	}
	
	/**
	 * 获取用户关注的所有分类
	 * @param categoryId
	 * @param userId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId) {
		try {
			List listFocusedCate=getUserProductCategoryService().getFocusCategory(userId);
			return listFocusedCate;
		} catch (MalformedURLException e) {
			logger.warn("获取用户关注的所有分类异常",e);
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId,Integer curLevel, Long marketId) throws MalformedURLException {
			List listFocusedCate=getUserProductCategoryService().getFocusCategory(userId,curLevel, marketId);
			return listFocusedCate;
	}
	
	
	private UsercollectProductCategoryService getUserProductCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getUserProductCategoryUrl();
		if (usercollectProductCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectProductCategoryService = (UsercollectProductCategoryService) factory.create(
					UsercollectProductCategoryService.class, hessianUrl);
		}
		return usercollectProductCategoryService;
	}

	@Override
	public void batchAddFocus(Long userId,String categoryIds) throws Exception {
		 List<String> categoryLst=Arrays.asList(categoryIds.split(","));
		 for (String categoryId : categoryLst) {
			focus(Long.valueOf(categoryId), userId);
		}
	}


}
