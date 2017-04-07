package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "prom_order_prod_prominfo")
public class PromOrderProdProminfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8577034036123049163L;

	private Integer id;

	private Long orderNo;

	private Long productId;

	private Integer actId;

	private Double actPrice;

	private Date createTime;

	private String createUserId;

	private String updateUserId;

	private Date updateTime;

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

	@Column(name = "productId")
	public Long getProductId() {

		return this.productId;
	}

	public void setProductId(Long productId) {

		this.productId = productId;
	}

	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "actPrice")
	public Double getActPrice() {

		return this.actPrice;
	}

	public void setActPrice(Double actPrice) {

		this.actPrice = actPrice;
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
