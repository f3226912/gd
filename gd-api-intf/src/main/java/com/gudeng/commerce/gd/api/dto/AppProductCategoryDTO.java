package com.gudeng.commerce.gd.api.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.supplier.dto.ProductDto;

public class AppProductCategoryDTO extends ProductCategoryDTO implements Serializable {

 
	private static final long serialVersionUID = -4912108033867124993L;
	 
	private List<AppProductCategoryDTO> subCategory;
	private List<ProductDto> listProduct;
	private Integer  hasFocused=0;

	public List<AppProductCategoryDTO> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<AppProductCategoryDTO> subCategory) {
		this.subCategory = subCategory;
	}

	public Integer getHasFocused() {
		return hasFocused;
	}

	public void setHasFocused(Integer hasFocused) {
		this.hasFocused = hasFocused;
	}

	public List<ProductDto> getListProduct() {
		return listProduct;
	}

	public void setListProduct(List<ProductDto> listProduct) {
		this.listProduct = listProduct;
	}
	
	
	
	
	
	
}
