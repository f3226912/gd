package com.gudeng.commerce.gd.api.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;

public interface UserCollectShopToolService {

	
	/**
	 * 收藏商铺
	 * @param userId 用户ID
	 * @param businessId 商铺ID
	 * @return
	 */
	public int focusShop(Long userId, Long businessId);
	
	/**
	 * 取消收藏商铺
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public int blurShop(Long userId, Long businessId);
	
	/**
	 * 取消收藏商铺
	 * @param id 主键
	 * @return
	 */
	public int blurShop(Long id);
	
	/**
	 * 查询对应的收藏数据
	 * 判断是否收藏此店铺用
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId);
	
	
	
	/**
	 * 获取所有记录
	 * @param userId
	 * @return
	 */
	public List<UsercollectShopDTO>  getAll(Long userId );

}
