package com.gudeng.commerce.gd.search.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * ProductCategory Entity
 * 
 */
@Entity(name = "product_category")
public class ProductCategoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8260363541267417429L;
	// Fields
	private Long categoryId;
	private String cateName;
	private Integer curLevel;
	private Long parentId;
	private Integer orderNum;
	private String typeIcon;
	private String status;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;

	/** Default constructor */
	public ProductCategoryEntity() {
	}

	@Id
	@Column(name = "categoryId", unique = true, nullable = false)
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "cateName")
	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	@Column(name = "curLevel")
	public Integer getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(Integer curLevel) {
		this.curLevel = curLevel;
	}

	@Column(name = "parentId")
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "typeIcon")
	public String getTypeIcon() {
		return typeIcon;
	}

	public void setTypeIcon(String typeIcon) {
		this.typeIcon = typeIcon;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
