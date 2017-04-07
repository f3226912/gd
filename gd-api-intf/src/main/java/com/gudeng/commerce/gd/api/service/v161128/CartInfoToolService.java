package com.gudeng.commerce.gd.api.service.v161128;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.api.dto.input.ShoppingCartInputDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessCartItemDetailDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.BaseToolService;
import com.gudeng.commerce.gd.order.dto.CartInfoDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.entity.CartInfoEntity;

public interface CartInfoToolService extends BaseToolService<CartInfoDTO> {
	public Long insert(CartInfoEntity entity) throws Exception;

	/**
	 * 购物车添加商品
	 * @param cartInput
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum addProduct(ShoppingCartInputDTO cartInput) throws Exception;

	/**
	 * 购物车删除商品
	 * @param cartInput
	 * @return
	 * @throws Exception 
	 */
	public ErrorCodeEnum deleteProduct(ShoppingCartInputDTO cartInput) throws Exception;

	/**
	 * 查询购物车中购物项
	 * @param cartInput
	 * @return
	 * @throws Exception 
	 */
	public PageQueryResultDTO<BusinessCartItemDetailDTO> getShoppingItems(Map<String, Object> param) throws Exception;

	/**
	 * 修改购物车商品
	 * @param cartInput
	 * @return
	 * @throws Exception
	 */
	public ErrorCodeEnum modifyProduct(ShoppingCartInputDTO cartInput) throws Exception;
	
	/**
	 * 查询购物车中购物项通过商铺分页
	 * @param cartInput
	 * @return
	 * @throws Exception 
	 */
	public PageQueryResultDTO<BusinessCartItemDetailDTO> getItemsByBusiness(Map<String, Object> param) throws Exception;

}