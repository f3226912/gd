package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;

/**
 * 
 * usercollectProductTool接口
 * @author Administrator
 *
 */
public interface UsercollectProductToolService {

 
	/**
	 * 关注产品
	 * @param userId
	 * @param productId
	 * @param categoryId
	 * @return
	 */
	public  Long  addFocus  (Long userId, Long productId,Long categoryId) throws Exception;
	
	/**
	 * 取消关注产品
	 * @param userId
	 * @param productId
	 */
	public  void  cancelFocus  (Long userId, Long productId ) throws Exception;
	
	/**
	 * 根据UserId productId 
	 * 获得关注信息
	 * 用于判断是否关注
	 * @param userId
	 * @param productId
	 * @return
	 */
	public UsercollectProductDTO getCollect(Long userId,Long productId) throws Exception;
	
	
	
}
