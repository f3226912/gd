package com.gudeng.commerce.gd.supplier.entity;

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
	/**
	 * 一级分类对应的父分类id值
	 */
	public static final Long FIRST_LEVEL_CORRESPONDING_PARENTID = -1l;
	// Fields
	private Long categoryId;
	private String cateName;
	private Integer curLevel;
	private Long parentId;
	private Integer orderNum;
	private String typeIcon;
	private String webTypeIcon;
	private Long marketId;
	private String status;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	private int lefts;
	private int rights;
	

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

	@Column(name = "marketId")
	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	@Column(name = "webTypeIcon")
	public String getWebTypeIcon() {
		return webTypeIcon;
	}

	public void setWebTypeIcon(String webTypeIcon) {
		this.webTypeIcon = webTypeIcon;
	}

	@Column(name = "lefts")
	public int getLefts() {
		return lefts;
	}
	public void setLefts(int lefts) {
		this.lefts = lefts;
	}
	@Column(name = "rights")
	public int getRights() {
		return rights;
	}
	public void setRights(int rights) {
		this.rights = rights;
	}
	

}
