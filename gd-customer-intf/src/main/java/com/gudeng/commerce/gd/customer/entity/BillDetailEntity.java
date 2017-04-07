package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Entity;

/**
 * 账单明细entity
 * @author humy
 *
 */
@Entity(name = "bill_detail")
public class BillDetailEntity implements java.io.Serializable {

	private static final long serialVersionUID = -3118972434830857027L;

	/**
	 * 主键id
	 */
	private Integer billId;
	
	/**
	 * 会员账号
	 */
	private Integer account;
	
	/**
	 * 订单号
	 */
	private Long orderNo;
	
	/**
	 * 主题
	 */
	private String subject;

	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 交易金额
	 */
	private Double tradeAmount;
	
	/**
	 * 收支类型 1收入 2支出
	 */
	private String revExpType;

	/**
	 * 类别ID
	 */
	private String categoryId;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Double getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Double tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getRevExpType() {
		return revExpType;
	}

	public void setRevExpType(String revExpType) {
		this.revExpType = revExpType;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
}
