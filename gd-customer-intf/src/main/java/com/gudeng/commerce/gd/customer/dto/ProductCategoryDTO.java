package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.ProductCategoryEntity;


/**
 * ProductCategoryDTO Entity
 * 
 */
public class ProductCategoryDTO extends ProductCategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -170099000429488270L;

	private String businessName;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	
}
