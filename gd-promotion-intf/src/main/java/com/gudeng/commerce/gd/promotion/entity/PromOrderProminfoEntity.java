package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "prom_order_prominfo")
public class PromOrderProminfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190334457985925551L;

	private Integer orderPromId;

	private Long orderNo;

	private Double prepayAmt;

	private Double restAmt;

	private Double buyerPoundage;

	private Double sellerPoundage;

	private Double totalAmt;

	private Date prepayStartTime;

	private Date prepayEndTime;

	private Date prepayTime;

	private String stateComment;

	private String marketName;

	private Integer deliverMemberId;

	private Date deliverTime;

	private Date createTime;

	private String createUserId;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "orderPromId")
	public Integer getOrderPromId() {

		return this.orderPromId;
	}

	public void setOrderPromId(Integer orderPromId) {

		this.orderPromId = orderPromId;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {

		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {

		this.orderNo = orderNo;
	}

	@Column(name = "prepayAmt")
	public Double getPrepayAmt() {

		return this.prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {

		this.prepayAmt = prepayAmt;
	}

	@Column(name = "restAmt")
	public Double getRestAmt() {

		return this.restAmt;
	}

	public void setRestAmt(Double restAmt) {

		this.restAmt = restAmt;
	}

	@Column(name = "buyerPoundage")
	public Double getBuyerPoundage() {

		return this.buyerPoundage;
	}

	public void setBuyerPoundage(Double buyerPoundage) {

		this.buyerPoundage = buyerPoundage;
	}

	@Column(name = "sellerPoundage")
	public Double getSellerPoundage() {

		return this.sellerPoundage;
	}

	public void setSellerPoundage(Double sellerPoundage) {

		this.sellerPoundage = sellerPoundage;
	}

	@Column(name = "totalAmt")
	public Double getTotalAmt() {

		return this.totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {

		this.totalAmt = totalAmt;
	}

	@Column(name = "prepayStartTime")
	public Date getPrepayStartTime() {

		return this.prepayStartTime;
	}

	public void setPrepayStartTime(Date prepayStartTime) {

		this.prepayStartTime = prepayStartTime;
	}

	@Column(name = "prepayEndTime")
	public Date getPrepayEndTime() {

		return this.prepayEndTime;
	}

	public void setPrepayEndTime(Date prepayEndTime) {

		this.prepayEndTime = prepayEndTime;
	}

	@Column(name = "prepayTime")
	public Date getPrepayTime() {

		return this.prepayTime;
	}

	public void setPrepayTime(Date prepayTime) {

		this.prepayTime = prepayTime;
	}

	@Column(name = "stateComment")
	public String getStateComment() {

		return this.stateComment;
	}

	public void setStateComment(String stateComment) {

		this.stateComment = stateComment;
	}

	@Column(name = "marketName")
	public String getMarketName() {

		return this.marketName;
	}

	public void setMarketName(String marketName) {

		this.marketName = marketName;
	}

	@Column(name = "deliverMemberId")
	public Integer getDeliverMemberId() {

		return this.deliverMemberId;
	}

	public void setDeliverMemberId(Integer deliverMemberId) {

		this.deliverMemberId = deliverMemberId;
	}

	@Column(name = "deliverTime")
	public Date getDeliverTime() {

		return this.deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {

		this.deliverTime = deliverTime;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}
}
