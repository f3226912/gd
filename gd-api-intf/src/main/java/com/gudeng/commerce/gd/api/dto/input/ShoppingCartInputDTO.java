package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;


/**
 * @description 购物车输入参数
 * @date 2016年11月2日
 */
public class ShoppingCartInputDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2120044797687117681L;
	
	private Long memberId;
	private Long businessId;
	private String productDetails;
	private Long productId;
	private String productIds;
	private Double quantity;
	private String selected;
	private Long shoppingItemId;
	private String shoppingItemIds;
	private String shoppingItemDetails;
	
	
	public String getShoppingItemIds() {
		return shoppingItemIds;
	}
	public void setShoppingItemIds(String shoppingItemIds) {
		this.shoppingItemIds = shoppingItemIds;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public Long getShoppingItemId() {
		return shoppingItemId;
	}
	public void setShoppingItemId(Long shoppingItemId) {
		this.shoppingItemId = shoppingItemId;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getShoppingItemDetails() {
		return shoppingItemDetails;
	}
	public void setShoppingItemDetails(String shoppingItemDetails) {
		this.shoppingItemDetails = shoppingItemDetails;
	}
	
	

}
