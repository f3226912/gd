package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "business_statistics")
public class BusinessStatisticsEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010120658922139740L;

	private Integer ID;

	private Long businessId;

	private Double dayPayAmt;

	private Date statisTime;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "ID")
	public Integer getID() {

		return this.ID;
	}

	public void setID(Integer ID) {

		this.ID = ID;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "dayPayAmt")
	public Double getDayPayAmt() {

		return this.dayPayAmt;
	}

	public void setDayPayAmt(Double dayPayAmt) {

		this.dayPayAmt = dayPayAmt;
	}

	@Column(name = "statisTime")
	public Date getStatisTime() {

		return this.statisTime;
	}

	public void setStatisTime(Date statisTime) {

		this.statisTime = statisTime;
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
