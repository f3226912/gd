package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 收藏商铺实体
 * @author Administrator
 *
 */
@Entity(name = "usercollect_shop")
public class UsercollectShopEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2498404764092059460L;

	private Long id;

	private Long userId;

	private Long businessId;

	private Date createTime;

	@Column(name = "id")
	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	@Column(name = "userId")
	public Long getUserId() {

		return this.userId;
	}

	public void setUserId(Long userId) {

		this.userId = userId;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}
}
