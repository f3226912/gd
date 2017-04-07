package com.gudeng.commerce.gd.promotion.dto;

/**
 * 订单活动返回结果DTO
 * @author TerryZhang
 */
public class GdOrderActivityResultDTO implements java.io.Serializable{

	private static final long serialVersionUID = 3974030193542551755L;
	
	/** 订单号 */
	private String orderNo;

	/** 商铺id */
	private Integer businessId;
	
	/** 市场id */
	private Integer marketId;

	/** 买家id */
	private Integer buyerId;
	
	/** 卖家id */
	private Integer sellerId;
	
	/** 买家活动信息列表 */
	private GdOrderActivityDetailDTO buyerActInfo;
	
	/** 卖家佣金补贴信息列表 */
	private GdOrderActivityDetailDTO sellerActInfo;

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

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public GdOrderActivityDetailDTO getBuyerActInfo() {
		return buyerActInfo;
	}

	public void setBuyerActInfo(GdOrderActivityDetailDTO buyerActInfo) {
		this.buyerActInfo = buyerActInfo;
	}

	public GdOrderActivityDetailDTO getSellerActInfo() {
		return sellerActInfo;
	}

	public void setSellerActInfo(GdOrderActivityDetailDTO sellerActInfo) {
		this.sellerActInfo = sellerActInfo;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Activity query result info, ");
		sb.append(", buyerId: " + getBuyerId());
		sb.append(", sellerId: " + getSellerId());
		sb.append(", marketId: " + getMarketId());
		sb.append(", businessId: " + getBusinessId());
		sb.append(", orderNo: " + getOrderNo());
		sb.append(", buyer act info: " + getBuyerActInfo().toString());
		sb.append(", seller act info: " + getSellerActInfo().toString());
		return sb.toString();
	}
}
