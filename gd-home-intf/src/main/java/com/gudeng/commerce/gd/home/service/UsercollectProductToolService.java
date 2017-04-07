package com.gudeng.commerce.gd.home.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;

/**
 * web层 
 * usercollectProductTool接口
 * @author Administrator
 *
 */
public interface UsercollectProductToolService {

 
	public List<PushProductDTO> getList(Long userId,Long businessId,Long marketId,Long productId, int startRow,int endRow) throws Exception;

	/**
	 * 获取用户关注的单品
	 * @param memberId
	 * @return
	 */
	public List<UsercollectProductDTO> getProductList(Long memberId,Long marketId) throws Exception;
	
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
	
	/**
	 * 根据参数获得总数
	 * @param parammap
	 * @return
	 * @throws Exception
	 */
	public Integer getCount(Map<String, Object> parammap) throws Exception;
	
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
	public List<PushProductDTO> getCollectList(Long userId,Long businessId,Long marketId,Long productId, int startRow,int endRow) throws Exception;

	/**
	 * 取消多个产品关注
	 * @param productIds
	 * @return 执行结果返回
	 */
	public Integer cancelMoreFocus(Long userId, String productIds) throws Exception;
	
}
