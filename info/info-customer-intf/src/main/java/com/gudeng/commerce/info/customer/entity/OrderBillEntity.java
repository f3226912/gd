package com.gudeng.commerce.info.customer.entity;

import java.util.Date;

public class OrderBillEntity implements java.io.Serializable {
	private static final long serialVersionUID = -5211030203567759960L;
	// 商户号
	private String businessNo;
	// 商户名
	private String businessName;
	// 交易类型
	private String tradeType;
	// 交易时间
	private Date tradeDay;
	// 交易卡号
	private String cardNo;
	// 终端号
	private String clientNo;
	// 交易金额
	private Double tradeMoney;
	// 系统参考号
	private String sysRefeNo;
	// 手续费
	private Double fee;
	// 使用状态 1已使用 2未使用
	private String useStatus;
	
	private int marketId;

	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Date getTradeDay() {
		return tradeDay;
	}

	public void setTradeDay(Date tradeDay) {
		this.tradeDay = tradeDay;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public String getSysRefeNo() {
		return sysRefeNo;
	}

	public void setSysRefeNo(String sysRefeNo) {
		this.sysRefeNo = sysRefeNo;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public int getMarketId() {
		return marketId;
	}

	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}
	
	
}
