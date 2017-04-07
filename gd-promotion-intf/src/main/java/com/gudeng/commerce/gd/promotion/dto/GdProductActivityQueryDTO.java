package com.gudeng.commerce.gd.promotion.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品配送活动查询DTO
 * @author TerryZhang
 */
public class GdProductActivityQueryDTO implements java.io.Serializable{

	private static final long serialVersionUID = -6223720171046498868L;
	
	/** 商铺id */
	private Integer businessId;
	
	/** 市场id */
	private Integer marketId;

	/** 买家id */
	private Integer buyerId;

	/** 商品id */
	private Integer productId;
	
	/** 配送方式 0自提(默认) 1平台配送 2商家配送*/
	private List<Integer> distributeModeList;
	
	/** 是否有平台配送*/
	private boolean hasPlatMode = false;
	
	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}


	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<Integer> getDistributeModeList() {
		if(distributeModeList == null){
			distributeModeList = new ArrayList<>();
		}
		return distributeModeList;
	}


	public void setDistributeModeList(List<Integer> distributeModeList) {
		this.distributeModeList = distributeModeList;
	}

	public boolean isHasPlatMode() {
		return hasPlatMode;
	}

	public void setHasPlatMode(boolean hasPlatMode) {
		this.hasPlatMode = hasPlatMode;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Product mode query params info, ");
		sb.append(", buyerId: " + getBuyerId());
		sb.append(", marketId: " + getMarketId());
		sb.append(", businessId: " + getBusinessId());
		sb.append(", productId: " + getProductId());
		sb.append(", hasPlatMode: " + isHasPlatMode());
		sb.append(", distributeModeList: " + getDistributeModeList());
		return sb.toString();
	}
}
