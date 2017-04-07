package com.gudeng.commerce.gd.admin.dto;

public class PosBankCardPayRecordQueryDTO {
	
	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 终端号
	 */
	private String clientNo;
	
	/**
	 * 商铺名称
	 */
	private String shopName;
	
	/**
	 * 系统参考号
	 */
	private String sysRefeNo;

	/**
	 * 银行交易开始时间
	 */
	private String billBeginTime;
	/**
	 * 银行交易结束时间
	 */
	private String billEndTime;
	
	/**
	 * 交易金额
	 */
	private Double tradeMoney;
	
	/**
	 * 匹配类型 
	 * 0全部 
	 * 1完全匹配 
	 * 2有流水无订单未升级 
	 * 3有流水无订单已升级 
	 * 4有订单无流水 延时
	 */
	private Integer type;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getSysRefeNo() {
		return sysRefeNo;
	}

	public void setSysRefeNo(String sysRefeNo) {
		this.sysRefeNo = sysRefeNo;
	}

	public String getBillBeginTime() {
		return billBeginTime;
	}

	public void setBillBeginTime(String billBeginTime) {
		this.billBeginTime = billBeginTime;
	}

	public String getBillEndTime() {
		return billEndTime;
	}

	public void setBillEndTime(String billEndTime) {
		this.billEndTime = billEndTime;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
