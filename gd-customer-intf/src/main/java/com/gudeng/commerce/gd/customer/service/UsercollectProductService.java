package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;

public interface UsercollectProductService {

	/**
	 * 分页获取用户关注的单品
	 * @param userId
	 * @param businessId
	 * @param marketId
	 * @param cateId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<PushProductDTO> getList(Long userId,Long businessId,Long marketId,Long cateId, int startRow,int endRow);

	/**
	 * 获取用户关注的单品总数
	 * @param userId
	 * @param businessId
	 * @param marketId
	 * @param cateId
	 * @return
	 */
	public int getTotal(Long userId, Long businessId, Long marketId, Long cateId);

	/**
	 * 获取用户关注的单品列表
	 * @param memberId
	 * @return
	 */
	public List<UsercollectProductDTO> getProductList(Long memberId,Long marketId);

	public  Long  addFocus  (Long userId, Long productId,Long categoryId);

	public  void  cancelFocus  (Long userId, Long productId );

	/**
	 * 根据UserId productId
	 * 获得关注信息
	 * 用于判断是否关注
	 * @param userId
	 * @param productId
	 * @return
	 */
	public UsercollectProductDTO getCollect(Long userId,Long productId);

	/**
	 * 根据参数获得总数
	 * @param paramMap
	 * @return
	 */
	public Integer getCount(Map<String, Object> paramMap);

	/**
	 * 获得关联产品数据
	 * @param userId
	 * @param businessId
	 * @param marketId
	 * @param productId
	 * @param startRow
	 * @param endRow
	 * @return
	 */
	public List<PushProductDTO> getCollectList(Long userId,Long businessId,Long marketId,Long productId, int startRow,int endRow);

	/**
	 * 取消多个产品关注
	 * @param productIds
	 * @return 执行结果返回
	 */
	public Integer cancelMoreFocus(Long userId, String productIds);


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
