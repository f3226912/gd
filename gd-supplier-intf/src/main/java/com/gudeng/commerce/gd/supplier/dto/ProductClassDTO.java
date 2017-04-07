package com.gudeng.commerce.gd.supplier.dto;

import java.io.Serializable;

public class ProductClassDTO implements Serializable {

	private static final long serialVersionUID = -4555125434906622415L;
	private String cateName;
	
	private String categoryId;

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
}
