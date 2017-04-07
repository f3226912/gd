package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "re_order_customer")
public class ReOrderCustomerEntity implements java.io.Serializable{

	private static final long serialVersionUID = 5975662541056330003L;

	private Integer id;
	
	private Long orderNo;
	
	private Integer memberId;
	
	private Integer orderBuyerId;
	
	private String mobile;
	
	private String realName;
	
	private Date createTime;
	
	private String createUserId;

	@Id
    @Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "memberId")
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "orderBuyerId")
	public Integer getOrderBuyerId() {
		return orderBuyerId;
	}

	public void setOrderBuyerId(Integer orderBuyerId) {
		this.orderBuyerId = orderBuyerId;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "realName")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
