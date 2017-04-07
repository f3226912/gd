package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "order_act_relation")
public class OrderActRelationEntity implements java.io.Serializable {

	private static final long serialVersionUID = -8704511967113500452L;

	/** 主键id */
	private Integer id;

	/** 订单号 */
	private Long orderNo;

	/** 活动id */
	private Integer actId;

	/** 商品id */
	private Integer productId;
	
	/** 活动版本 */
	private Integer actVersion;
	
	private Date createTime;
	
	private String createUserId;
	/** 活动类型（1刷卡补贴，2市场，3平台，4预付款违约金，5物流） */
	private Integer actType;

	@Column(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="orderNo")
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name="actId")
	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	@Column(name="actVersion")
	public Integer getActVersion() {
		return actVersion;
	}

	public void setActVersion(Integer actVersion) {
		this.actVersion = actVersion;
	}

	@Column(name="createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name="productId")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name="actType")
	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}
}
