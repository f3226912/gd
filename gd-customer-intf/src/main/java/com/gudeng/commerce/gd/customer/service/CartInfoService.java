package com.gudeng.commerce.gd.customer.service;

import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.CartInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.entity.CartInfoEntity;

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
}