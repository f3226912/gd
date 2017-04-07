package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_fee_by_act")
public class PromFeeByActEntity implements java.io.Serializable {

	private static final long serialVersionUID = 9041746556109846001L;

	/** 主键id */
	private Integer id;

	/** 活动id */
	private Integer actId;

	/** 订单号 */
	private Long orderNo;

	/** 费用类型 0手续费 */
	private String feeType;

	/** 用户id */
	private Integer memberId;

	/** 费用金额 */
	private Double feeAmt;

	/** 创建时间 */
	private Date createTime;

	/** 创建用户id */
	private String createUserId;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "actId")
	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "feeType")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "memberId")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "feeAmt")
	public Double getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
}
