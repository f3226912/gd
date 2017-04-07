package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.QuickSheetCategoryDTO;

public interface QuickMakeSheetService {
	/**
	 * 获取用户快速制单的数量
	 * @return
	 * @throws Exception
	 */
	public Integer getQuickMakeSheetCount(Map<String, Object> map) throws Exception;
	/**
	 * 快速制单列表
	 * @param memberAddressId
	 * @return
	 */
	public List<QuickSheetCategoryDTO> getQuickMakeSheetList() throws Exception;
	
	
	/**
	 * 标准库产品列表
	 * @param memberAddressId
	 * @return
	 */
	public List<QuickSheetCategoryDTO> getStandardLibraryProductList(Map<String, Object> map) throws Exception;
	/**
	 * 快速制单添加产品
	 * @param memberAddressId
	 * @return
	 */
	public Integer addProduct(Map<String, Object> map) throws Exception;
	
	
	/**
	 * 快速制单删除产品
	 * @param memberAddressId
	 * @return
	 */
	public Integer delProduct(Map<String, Object> map) throws Exception;
	
	/**
	 * 添加商品的数量
	 * @param memberAddressId
	 * @return
	 */
	public Integer productCount(Map<String, Object> map) throws Exception;
}
