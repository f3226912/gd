package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_fee")
public class PromFeeEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4783362735448241577L;

	private Integer id;

	private Integer actId;

	private String feeSource;

	private String feeType;

	private Double fee;

	private Double orderPayAmt;

	private Float feeScale;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;

	@Id
	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "feeSource")
	public String getFeeSource() {

		return this.feeSource;
	}

	public void setFeeSource(String feeSource) {

		this.feeSource = feeSource;
	}

	@Column(name = "feeType")
	public String getFeeType() {

		return this.feeType;
	}

	public void setFeeType(String feeType) {

		this.feeType = feeType;
	}

	@Column(name = "fee")
	public Double getFee() {

		return this.fee;
	}

	public void setFee(Double fee) {

		this.fee = fee;
	}

	@Column(name = "orderPayAmt")
	public Double getOrderPayAmt() {

		return this.orderPayAmt;
	}

	public void setOrderPayAmt(Double orderPayAmt) {

		this.orderPayAmt = orderPayAmt;
	}

	@Column(name = "feeScale")
	public Float getFeeScale() {

		return this.feeScale;
	}

	public void setFeeScale(Float feeScale) {

		this.feeScale = feeScale;
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

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}
}
