package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 订单出场关联表
 * @author Ailen
 *
 */
@Entity(name = "re_order_outmark")
public class ReOrderOutmarkEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4045896643580461481L;

	private Long OrderNo;

	private Long omId;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "OrderNo")
	public Long getOrderNo() {

		return this.OrderNo;
	}

	public void setOrderNo(Long OrderNo) {

		this.OrderNo = OrderNo;
	}

	@Column(name = "omId")
	public Long getOmId() {

		return this.omId;
	}

	public void setOmId(Long omId) {

		this.omId = omId;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
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
