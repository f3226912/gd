package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

public class PosBankCardPayRecordDTO implements Serializable{

	private static final long serialVersionUID = 4636082704916955538L;

	/** 系统参考号 */
	private String sysRefeNo;

	/** POS流水终端号 */
	private String clientNo;

	/** 刷卡的商铺终端号 */
	private String posNumber;
	
	/** 已存的商铺终端号 */
	private String shopPosNumber;

	/** 会员id */
	private Long memberid;

	/** 会员账号 */
	private String account;

	/** 交易卡号 */
	private String cardNo;

	/** 支付卡号 */
	private String paymentAcc;

	/** 流水交易金额 */
	private Double tradeMoney;

	/** 订单实付金额 */
	private Double tradeAmount;

	/** 商铺名称 */
	private String shopName;
	
	/** 订单id */
	private Integer persaleId;

	/** 订单号 */
	private Long orderNo;

	/** 银行交易时间 */
	private Date bankTradeTime;

	/** 订单交易时间 */
	private Date orderTradeTime;
	
	/**
	 * 匹配类型 
	 * 0全部 
	 * 1完全匹配 
	 * 2有流水无订单未升级 
	 * 3有流水无订单已升级 
	 * 4有订单无流水 延时
	 */
	private String matchType;

	private String buyerMobile;
	
	
	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getSysRefeNo() {
		return sysRefeNo;
	}

	public void setSysRefeNo(String sysRefeNo) {
		this.sysRefeNo = sysRefeNo;
	}

	public String getClientNo() {
		return clientNo;
	}

	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}

	public String getPosNumber() {
		return posNumber;
	}

	public void setPosNumber(String posNumber) {
		this.posNumber = posNumber;
	}

	public Long getMemberid() {
		return memberid;
	}

	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPaymentAcc() {
		return paymentAcc;
	}

	public void setPaymentAcc(String paymentAcc) {
		this.paymentAcc = paymentAcc;
	}

	public Double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(Double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Date getBankTradeTime() {
		return bankTradeTime;
	}

	public void setBankTradeTime(Date bankTradeTime) {
		this.bankTradeTime = bankTradeTime;
	}

	public Date getOrderTradeTime() {
		return orderTradeTime;
	}

	public void setOrderTradeTime(Date orderTradeTime) {
		this.orderTradeTime = orderTradeTime;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getShopPosNumber() {
		return shopPosNumber;
	}

	public void setShopPosNumber(String shopPosNumber) {
		this.shopPosNumber = shopPosNumber;
	}

	public Integer getPersaleId() {
		return persaleId;
	}

	public void setPersaleId(Integer persaleId) {
		this.persaleId = persaleId;
	}
}
