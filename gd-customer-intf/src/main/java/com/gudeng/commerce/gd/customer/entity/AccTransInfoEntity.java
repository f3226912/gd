package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易记录
 * 
 * @author steven
 * 
 */
public class AccTransInfoEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4207909811939591235L;
	private Integer sId /* 流水id */;
	private Integer accId /* 账户id */;
	private Integer memberId /* 会员id */;
	private Long orderNo;
	private Long statementId /* 流水号 */;
	private String tradeType /* 1余额抵扣 2用户补贴 3提现 */;
	private String peType /* 1收入 2支出 */;
	private Double tradeAmount;
	private String paymentAcc;
	private String recipientAcc;
	private Date createTime /* 创建时间 */;
	private String createUserId;
	private Date updateTime /* 更新时间 */;
	private String updateUserId;

	public Integer getsId() {
		return sId;
	}

	public void setsId(Integer sId) {
		this.sId = sId;
	}

	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getStatementId() {
		return statementId;
	}

	public void setStatementId(Long statementId) {
		this.statementId = statementId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getPeType() {
		return peType;
	}

	public void setPeType(String peType) {
		this.peType = peType;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getPaymentAcc() {
		return paymentAcc;
	}

	public void setPaymentAcc(String paymentAcc) {
		this.paymentAcc = paymentAcc;
	}

	public String getRecipientAcc() {
		return recipientAcc;
	}

	public void setRecipientAcc(String recipientAcc) {
		this.recipientAcc = recipientAcc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}
