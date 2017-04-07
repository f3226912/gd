package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "prom_baseinfo")
public class PromBaseinfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -907829851135486665L;

	private Integer actId;

	private String name;

	private String introduction;

	/**
	 * 类型  1产销
	 */
	private String type;

	/**
	 * 1 已报名 2 3 
	 */
	private String status;

	private Date startTime;

	private Date endTime;

	private Integer version;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;
	
	private String url;

	/**
	 * 是否最新 1新 0旧
	 */
	private String isLastest;

	@Id
	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "name")
	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "introduction")
	public String getIntroduction() {

		return this.introduction;
	}

	public void setIntroduction(String introduction) {

		this.introduction = introduction;
	}

	@Column(name = "type")
	public String getType() {

		return this.type;
	}

	public void setType(String type) {

		this.type = type;
	}

	@Column(name = "status")
	public String getStatus() {

		return this.status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "startTime")
	public Date getStartTime() {

		return this.startTime;
	}

	public void setStartTime(Date startTime) {

		this.startTime = startTime;
	}


	@Column(name = "endTime")
	public Date getEndTime() {

		return this.endTime;
	}

	public void setEndTime(Date endTime) {

		this.endTime = endTime;
	}

	@Column(name = "version")
	public Integer getVersion() {

		return this.version;
	}

	public void setVersion(Integer version) {

		this.version = version;
	}

	@Column(name = "createTime")
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

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "isLastest")
	public String getIsLastest() {

		return this.isLastest;
	}

	public void setIsLastest(String isLastest) {

		this.isLastest = isLastest;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
