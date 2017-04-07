package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

/**
 *  快速制单产品列表
 * @author gcwu
 */
public class QuickSheetCategoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer categoryId;
	
	private String  categoryName;
	
	
	
	private List<QuickSheetProductDTO> productList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<QuickSheetProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<QuickSheetProductDTO> productList) {
		this.productList = productList;
	}

	
	
}
