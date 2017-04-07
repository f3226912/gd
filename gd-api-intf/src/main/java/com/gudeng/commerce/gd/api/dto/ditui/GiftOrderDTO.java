package com.gudeng.commerce.gd.api.dto.ditui;

public class GiftOrderDTO {
	
	private String orderNo;
	private String orderStatus;
	private Double orderPrice;
//	private String orderTime;
	private String orderTimeStr;//方便APP端接受str值在列表展示，并在提交的时候，直接使用str,orderTime会和服务端Date类型的orderTime不匹配
	private Integer businessId;
	private String shopName;
	private String type;//  2表示订单 ,3表示注册所产生的虚拟订单
	private String mobile;
	private String realName;
	private Long marketId;

	public Long getMarketId() {
		return marketId;
	}
	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
	public String getOrderTimeStr() {
		return orderTimeStr;
	}
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}
//	public String getOrderTime() {
//		return orderTime;
//	}
//	public void setOrderTime(String orderTime) {
//		this.orderTime = orderTime;
//	}
	
	
}
