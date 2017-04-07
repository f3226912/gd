package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "order_refund_record")
public class OrderRefundRecordEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7631732407745392673L;

	private Integer id;

	private Long orderNo;

	private String memberId;

	private String type;

	private Double amount;

	private String reason;

	private Date refund_time;

	private String status;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;
	
	private String refundNo;

	@Id
	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {

		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {

		this.orderNo = orderNo;
	}

	@Column(name = "memberId")
	public String getMemberId() {

		return this.memberId;
	}

	public void setMemberId(String memberId) {

		this.memberId = memberId;
	}

	@Column(name = "type")
	public String getType() {

		return this.type;
	}

	public void setType(String type) {

		this.type = type;
	}

	@Column(name = "amount")
	public Double getAmount() {

		return this.amount;
	}

	public void setAmount(Double amount) {

		this.amount = amount;
	}

	@Column(name = "reason")
	public String getReason() {

		return this.reason;
	}

	public void setReason(String reason) {

		this.reason = reason;
	}

	@Column(name = "refund_time")
	public Date getRefund_time() {

		return this.refund_time;
	}

	public void setRefund_time(Date refund_time) {

		this.refund_time = refund_time;
	}

	@Column(name = "status")
	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {

		this.status = status;
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

	@Column(name = "refundNo")
	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	
	
}
