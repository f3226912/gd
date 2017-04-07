package com.gudeng.commerce.gd.home.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;

/**
 * ProductCategoryDTO Entity
 * 
 */
public class ProductCategoryDTO extends ProductCategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -170099000429488270L;
	
	private List<ProductCategoryDTO> childList;

	public List<ProductCategoryDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<ProductCategoryDTO> childList) {
		this.childList = childList;
	}
	

}
