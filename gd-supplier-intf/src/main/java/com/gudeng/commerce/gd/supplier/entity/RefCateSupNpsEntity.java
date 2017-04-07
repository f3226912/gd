package com.gudeng.commerce.gd.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 农批商与供应商分类关联
 * @author Ailen
 *
 */
@Entity(name = "ref_cate_sup_nps")
public class RefCateSupNpsEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7352208447666879262L;

	private Long refId;

	private Long suppCategoryId;

	private Long npsCategoryId;

	private String status;

	private Integer orderNum;

	private Long marketId;

	private Date createTime;

	private String createUserId;

	private String updateUserId;

	private Date updateTime;
	
	private String type;

	@Column(name = "refId")
	public Long getRefId() {

		return this.refId;
	}

	public void setRefId(Long refId) {

		this.refId = refId;
	}

	@Column(name = "suppCategoryId")
	public Long getSuppCategoryId() {

		return this.suppCategoryId;
	}

	public void setSuppCategoryId(Long suppCategoryId) {

		this.suppCategoryId = suppCategoryId;
	}

	@Column(name = "npsCategoryId")
	public Long getNpsCategoryId() {

		return this.npsCategoryId;
	}

	public void setNpsCategoryId(Long npsCategoryId) {

		this.npsCategoryId = npsCategoryId;
	}

	@Column(name = "status")
	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	@Column(name = "orderNum")
	public Integer getOrderNum() {

		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {

		this.orderNum = orderNum;
	}

	@Column(name = "marketId")
	public Long getMarketId() {

		return this.marketId;
	}

	public void setMarketId(Long marketId) {

		this.marketId = marketId;
	}

	@Column(name = "createtime")
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
	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
