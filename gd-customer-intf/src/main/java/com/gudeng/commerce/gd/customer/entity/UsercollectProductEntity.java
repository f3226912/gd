package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author steven
 * 
 */
@Entity(name = "usercollect_product")
public class UsercollectProductEntity implements java.io.Serializable {

	private static final long serialVersionUID = 9048968728391838967L;

	private Long id;

	private Long userId;

	private Date createTime;

	private Long productId;
	private Long cateId;
	

	private Long businessId;
	private Long marketId;
	
	

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "userId")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "productId")
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "cateId")
	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	@Column(name = "marketId")
	public Long getMarketId() {
		return marketId;
	}
	
	@Column(name = "businessId")
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
 

}
