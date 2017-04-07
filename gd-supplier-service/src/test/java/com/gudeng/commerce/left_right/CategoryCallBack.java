package com.gudeng.commerce.left_right;

import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

public interface CategoryCallBack {
	
	public List<ProductCategoryDTO> getChildProductCategory(Long id);
	public List<ProductCategoryDTO> getChildProductCategoryByMarketId(Long id,Long marketId) ;
	
}
