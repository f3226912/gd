package com.gudeng.commerce.gd.promotion.dto;

public class ActProductDTO implements java.io.Serializable{

	private static final long serialVersionUID = 6111631909522927345L;

	private Integer productId;
	
	private Integer businessId;
	
	private Integer cateId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}
}
