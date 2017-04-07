package com.gudeng.commerce.gd.customer.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;

public interface UsercollectProductCategoryService {

	/**
	 * 用户关注了某个类别
	 * @param categoryId
	 */
	public Long focus(Long categoryId,Long userId);
	
	
	/**
	 * 用户取消关注了某个类别
	 * @param categoryId
	 */
	public void cancelFocus(Long categoryId,Long userId);
	/**
	 * 获取用户关注 的所有分类
	 * @param categoryId
	 */
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId);
	

	/**
	 * 获取用户关注 的所有分类
	 * @param categoryId
	 */
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId,Integer curLevel, Long marketId);
	
	
	
}
