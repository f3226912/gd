package com.gudeng.commerce.gd.admin.dto;

/**
 * 对应补贴审核查询中间Bean
 * @author Ailen
 *
 */
public class SubAuditQueryBean {
	
	private String orderNo;			// 订单号
	
	private Double orderAmount; 	// 订单金额
	
	private String payType;			// order_baseinfo.payType,支付方式: 1钱包余额 2线下刷卡 3钱包+线下刷卡
	
	private String buyerName;		// 买家姓名
	
	private String orderStatus;		// order_baseinfo.orderStatus, 订单状态 1待付款 2部分付款 3已付款 4已出场 5待确认 8已取消 9已作废
	
	private String subStatus;		// 订单状态(补贴状态 1待补贴 2系统驳回 3审核通过 4审核不通过)
	
	private String buyerShop;		// 卖家店铺	
	
	private String orderLike;		// 订单详情搜索
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getBuyerShop() {
		return buyerShop;
	}

	public void setBuyerShop(String buyerShop) {
		this.buyerShop = buyerShop;
	}

	public String getOrderLike() {
		return orderLike;
	}

	public void setOrderLike(String orderLike) {
		this.orderLike = orderLike;
	}
	
}
