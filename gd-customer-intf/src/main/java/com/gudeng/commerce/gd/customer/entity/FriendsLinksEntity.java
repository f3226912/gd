package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity(name = "friendslinks")
public class FriendsLinksEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2212813376798459860L;
	private int id;
	private String linkCate;
	private String linkType;
	private String linkName;
	private String linkImage;
	private String linkUrl;
	private String status;
	private String description;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	private String examineUserId;
	private Date examineTime;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "linkCate")
	public String getLinkCate() {
		return linkCate;
	}

	public void setLinkCate(String linkCate) {
		this.linkCate = linkCate;
	}

	@Column(name = "linkType")
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	@Column(name = "linkName")
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Column(name = "linkImage")
	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

	@Column(name = "linkUrl")
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "examineUserId")
	public String getExamineUserId() {
		return examineUserId;
	}

	public void setExamineUserId(String examineUserId) {
		this.examineUserId = examineUserId;
	}

	@Column(name = "examineTime")
	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}
	
	
}
