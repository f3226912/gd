package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 单个商铺内购物项
 * 
 * @date 2016年11月3日
 */
public class BusinessCartItemDetailDTO implements Serializable {

	private static final long serialVersionUID = 5554206694663268750L;

	public BusinessCartItemDetailDTO(Long businessId) {
		this.businessId = businessId;
	}
	
	public BusinessCartItemDetailDTO(Long businessId,String businessName) {
		this.businessId = businessId;
		this.businessName=businessName;
	}

	private Long businessId;
	private String businessName;
	private List<ShoppingCartItem> shoppingItems;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<ShoppingCartItem> getShoppingItems() {
		return shoppingItems;
	}

	public void setShoppingItems(List<ShoppingCartItem> cartItems) {
		this.shoppingItems = cartItems;
	}

}
