package com.gudeng.commerce.gd.api.service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.PushProductAppDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;


public interface UsercollectProductToolService {


	/**
	 * 根据商铺ID，市场ID, 分类id
	 * 获取用户关注的商品
	 * @param userId
	 * @param businessId
	 * @param marketId
	 * @return
	 */
	public List<PushProductAppDTO> getList(Long userId,Long businessId,Long marketId,Long cateId,int startRow,int endRow)  throws MalformedURLException ;

	/**
	 * 根据商铺ID，市场ID获取用户关注的商品总数
	 * @param userId
	 * @param businessId
	 * @param marketId
	 * @param cateId
	 * @return
	 */
	public int getTotal(Long userId,Long businessId,Long marketId,Long cateId)  throws MalformedURLException;

	/**
	 * 根据用户id获取用户关注产品
	 * @param memberId
	 * @return
	 */
	public List<UsercollectProductDTO> getProductList(Long memberId,Long marketId)  throws MalformedURLException;


	/**
	 *  添加关注单品
	 * @param userId
	 * @param productId
	 * @return
	 */
	public Long addFocus(Long userId, Long productId)  throws Exception;


	/**
	 *  取消关注单品
	 * @param userId
	 * @param productId
	 * @return
	 */
	public void cancelFocus(Long userId, Long productId )  throws MalformedURLException;

	public void batAddFocus(Long userId, String productIds);

	/**
	 * 获取用户关注的产品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<UsercollectProductDTO> getProductsOfConcerned(Map<String, Object> params) throws Exception;

	/**
	 * 获取用户关注的产品总数
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getProductsCountOfConcerned(Map<String, Object> params) throws Exception;

}
