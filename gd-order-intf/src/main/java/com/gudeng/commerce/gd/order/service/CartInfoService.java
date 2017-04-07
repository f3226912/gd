package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.CartInfoDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.entity.CartInfoEntity;


/**
 * 购物车服务类
 * @author Administrator
 * @date 2016年11月2日
 */
public interface CartInfoService extends BaseService<CartInfoDTO> {
	
	public Long insert(CartInfoEntity entity) throws Exception;
	
	public CartInfoDTO getCartItemByParam(Long memberId,Long productId) throws Exception;
	
	public int deleteCartItemByParam(Long memberId,Long productId) throws Exception;
	
	public PageQueryResultDTO<CartInfoDTO> getCartItemByParam(Map<String,Object> paramMap) throws Exception;
	
	public int updateShoppingItem(Map<String, Object> paramMap);
	
	public int addCartInfoList(Map<String,Object> paramMap) throws Exception;

	public int updateCartItemByProductId(Map<String, Object> paramMap) throws Exception;

	public int updateCartItemList(List<CartInfoDTO> dtoList);
	
	public int updateCartItemByItemId(Map<String, Object> paramMap) throws Exception;
	
	public PageQueryResultDTO<CartInfoDTO> getUserBusiness(Map<String,Object> paramMap) throws Exception;
	
	public List<CartInfoDTO> getItemByBusiness(Map<String, Object> paramMap) throws Exception;
}