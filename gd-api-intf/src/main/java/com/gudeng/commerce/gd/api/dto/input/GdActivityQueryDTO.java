package com.gudeng.commerce.gd.api.dto.input;

import java.util.List;

import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;



public class GdActivityQueryDTO extends GdOrderActivityQueryDTO implements java.io.Serializable{

	private static final long serialVersionUID = -7156173526091910920L;
	
	private Integer businessId;
	private Double orderAmount;
	private Double payAmount;
	private Integer SellerId;
	private Integer buyerId;
	private Integer marketId;
	private List<GdProductActInfoDTO> productList;
	private boolean hasProduct;
	private boolean ordered;
	
	
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Integer getSellerId() {
		return SellerId;
	}
	public void setSellerId(Integer sellerId) {
		SellerId = sellerId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public List<GdProductActInfoDTO> getProductList() {
		return productList;
	}
	public void setProductList(List<GdProductActInfoDTO> productList) {
		this.productList = productList;
	}
	public boolean isHasProduct() {
		return hasProduct;
	}
	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}
	public boolean isOrdered() {
		return ordered;
	}
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
	
	
}
