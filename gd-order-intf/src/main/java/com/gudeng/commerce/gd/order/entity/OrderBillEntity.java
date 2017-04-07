package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

/**
 * @Description 谷登账单
 * @Project gd-order-intf
 * @ClassName OrderBillEntity.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:19:27
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory 新增银行流水表第13列：payChannelCode ，支付渠道编码 @Author dli@gdeng.cn 2016/11/16 16:18
 */
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

	private Long marketId;
	
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	//银行卡类别
	private String cardType;
	//支付渠道编码
	private String payChannelCode;

	public String getPayChannelCode() {
		return payChannelCode;
	}

	public void setPayChannelCode(String payChannelCode) {
		this.payChannelCode = payChannelCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
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

    public Long getMarketId() {
        return marketId;
    }

    public void setMarketId(Long marketId) {
        this.marketId = marketId;
    }
}
