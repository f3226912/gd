package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.EnPostLogEntity;


public class EnPostLogDTO  extends EnPostLogEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5422017058848702453L;
	
	private Integer businessId;
	
	private Integer sellerId;
	
	private Integer marketId;
	
	private String shopName;

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
