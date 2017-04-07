package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;

import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;

public interface UsercollectProductCategoryToolService {

	/**
	 * 用户关注了某个类别
	 * @param categoryId
	 * @throws MalformedURLException 
	 */
	public Long focus(Long categoryId,Long userId) throws Exception;
	
	/**
	 * 用户取消关注了某个类别
	 * @param categoryId
	 */
	public void cancelFocus(Long categoryId,Long userId) throws Exception;
	
	/**
	 * 获取用户关注 的所有分类
	 * @param categoryId
	 */
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId);
	
	/**
	 * 获取用户关注 的所有分类
	 * @param categoryId
	 * @throws MalformedURLException 
	 */
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId, Integer curLevel, Long marketId) throws MalformedURLException;
	
	/**
	 * 批量添加关注分类
	 * @param userId
	 * @param categoryIds
	 * @throws MalformedURLException 
	 * @throws NumberFormatException 
	 */
	public void batchAddFocus(Long userId,String categoryIds) throws Exception;
	
	/**
	 * 批量取消关注
	 * @param userId
	 * @param categoryIds
	 * @throws Exception
	 */
	public void batchCancelFocus(Long userId, String categoryIds) throws Exception;
}
