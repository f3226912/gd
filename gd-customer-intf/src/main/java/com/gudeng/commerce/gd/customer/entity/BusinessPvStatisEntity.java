package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "business_pv_statis")
public class BusinessPvStatisEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 168950334682769131L;

	private Integer id;

	private Long businessId;

	private String fromPage;

	private Long pv;

	private Date statisTime;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Long businessId) {

		this.businessId = businessId;
	}

	@Column(name = "fromPage")
	public String getFromPage() {

		return this.fromPage;
	}

	public void setFromPage(String fromPage) {

		this.fromPage = fromPage;
	}

	@Column(name = "pv")
	public Long getPv() {

		return this.pv;
	}

	public void setPv(Long pv) {

		this.pv = pv;
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
