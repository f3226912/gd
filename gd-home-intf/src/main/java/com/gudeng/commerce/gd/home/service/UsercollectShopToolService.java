package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;

/**
 * 收藏商铺接口
 * @author Administrator
 *
 */
public interface UsercollectShopToolService {
	
	/**
	 * 收藏商铺
	 * @param userId 用户ID
	 * @param businessId 商铺ID
	 * @return
	 */
	public int focusShop(Long userId, Long businessId) throws Exception;
	
	/**
	 * 取消收藏商铺
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public int blurShop(Long userId, Long businessId) throws Exception;
	
	/**
	 * 取消收藏商铺
	 * @param id 主键
	 * @return
	 */
	public int blurShop(Long id) throws Exception;
	
	/**
	 * 查询对应的收藏数据
	 * 判断是否收藏此店铺用
	 * @param userId
	 * @param businessId
	 * @return
	 */
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId) throws Exception;
	
	/**
	 * 获取收藏根据参数
	 * @param map
	 * @return
	 */
	public List<UsercollectShopDTO> getCollectList(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取收藏商铺总数
	 * @param map
	 * @return
	 */
	public Integer getCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取收藏我的用户列表
	 * @param map
	 * @return
	 */
	public List<UsercollectShopDTO> getFocusMeCollectList(Map<String, Object> map) throws Exception;
	
	/**
	 * 获取收藏我的总数
	 * @param map
	 * @return
	 */
	public Integer getFocusMeCount(Map<String, Object> map) throws Exception;
	
	/**
	 * 取消多个收藏商铺
	 * @param map
	 * @return
	 */
	public Integer blurMoreShop(Long userId,String businessIds) throws Exception;
	

}
