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
@Entity(name = "usercollect_product_category")
public class UsercollectProductCategoryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 9048968728391838967L;

	private Long id;

	private Long userId;

	private Date createTime;

	private Long productCategoryId;

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
	@Column(name = "productCategoryId")
	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

}
