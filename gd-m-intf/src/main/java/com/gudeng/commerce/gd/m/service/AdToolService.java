package com.gudeng.commerce.gd.m.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdAdvertiseDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

public interface AdToolService {
	
	public List<AdAdvertiseDTO> getAdByMenuId(int menuId)throws Exception;
	
	
	public List<ProductCategoryDTO> getTopCategoryDTO(Long marketId)throws Exception;
	
	
	public List<ReBusinessCategoryDTO> getShopCategoryList(Long businessId,int start, int size)throws Exception;

	public Long[] getGysCateByShopCate(List<Long> cateIds)throws Exception;
	
	public List<AdAdvertiseDTO> queryAdvertiseList(Map<String, Object> params) throws Exception;
}
