package com.gudeng.commerce.gd.api.dto.ditui;

public class GrandGiftInputDTO {
	
	Integer grdUserId;//地堆用户id
	Integer marketId;//地推所属市场
//	private Integer giftstoreId;//地推人员所属仓库id
	String type;//1现场发放，0集中发放
	String mobile;//买家的手机号码
	String customerId;//客户id
	String carNo;//车牌号
	String orderDetails;//订单明细
	String giftDeatils;//礼品明细
	
	String nstOrderDetails;//农速通订单明细
//	String sourceType;//1表示农批，2表示农速通
	String description;//农速通业务的描述	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNstOrderDetails() {
		return nstOrderDetails;
	}
	public void setNstOrderDetails(String nstOrderDetails) {
		this.nstOrderDetails = nstOrderDetails;
	}
//	public String getSourceType() {
//		return sourceType;
//	}
//	public void setSourceType(String sourceType) {
//		this.sourceType = sourceType;
//	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
//	public Integer getGiftstoreId() {
//		return giftstoreId;
//	}
//	public void setGiftstoreId(Integer giftstoreId) {
//		this.giftstoreId = giftstoreId;
//	}
	public Integer getGrdUserId() {
		return grdUserId;
	}
	public void setGrdUserId(Integer grdUserId) {
		this.grdUserId = grdUserId;
	}
		public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getGiftDeatils() {
		return giftDeatils;
	}
	public void setGiftDeatils(String giftDeatils) {
		this.giftDeatils = giftDeatils;
	}
	
	
}
