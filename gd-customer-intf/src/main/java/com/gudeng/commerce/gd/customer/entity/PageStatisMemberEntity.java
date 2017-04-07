package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "page_statis_member")
public class PageStatisMemberEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7774004902989106633L;

	private Integer ID;

	private String pageType;

	private Date statisTime;

	private Long memberId;

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

	@Column(name = "pageType")
	public String getPageType() {

		return this.pageType;
	}

	public void setPageType(String pageType) {

		this.pageType = pageType;
	}

	@Column(name = "statisTime")
	public Date getStatisTime() {

		return this.statisTime;
	}

	public void setStatisTime(Date statisTime) {

		this.statisTime = statisTime;
	}

	@Column(name = "memberId")
	public Long getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Long memberId) {

		this.memberId = memberId;
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
