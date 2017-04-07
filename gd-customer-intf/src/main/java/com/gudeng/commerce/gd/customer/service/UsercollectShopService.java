package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;

/**
 * 收藏商铺接口
 * @author Administrator
 *
 */
public interface UsercollectShopService {
	
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
	 * 取消多个收藏商铺
	 * @param id 主键
	 * @return
	 */
	public Integer blurMoreShop(Long userId, String businessIds);
	
	/**
	 * 查询对应的收藏数据
	 * 判断是否收藏此店铺用
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId);
	
	/**
	 * 获取收藏根据参数
	 * @param map
	 * @return
	 */
	public List<UsercollectShopDTO> getCollectList(Map<String, Object> map);
	
	/**
	 * 获取收藏我的用户总数
	 * @param map
	 * @return
	 */
	public Integer getFocusMeCount(Map<String, Object> map);
	
	/**
	 * 获取收藏我的用户列表
	 * @param map
	 * @return
	 */
	public List<UsercollectShopDTO> getFocusMeCollectList(Map<String, Object> map);
	
	/**
	 * 获取收藏商铺总数
	 * @param map userId businessId
	 * @return
	 */
	public Integer getCount(Map<String, Object> map);
	
	/**
	 * 获取所有记录
	 * @param userId
	 * @return
	 */
	public List<UsercollectShopDTO> getAll(Long userId);
}
