package com.gudeng.commerce.info.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Sysmessage entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "sysmessage")
public class Sysmessage implements java.io.Serializable {

	// Fields

	/** 
    * @Fields serialVersionUID TODO
    * @since Ver 2.0
    */   
    
    private static final long serialVersionUID = 3534687678140556994L;
    private Long id;
	private String title;
	private String content;
	private String type;
	private String state;
	private String createUserId;
	private String createUserName;
	private Date createTime;
	private String updateUserId;
	private String updateUserName;
	private Date updateTime;
	
	private String isread;

	// Constructors

	/** default constructor */
	public Sysmessage() {
	}

	/** full constructor */
	public Sysmessage(String title, String content, String type, String state,
			String createUserId, String createUserName, Date createTime,
			String updateUserId, String updateUserName, Date updateTime) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.state = state;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateUserName = updateUserName;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 2000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "state", length = 2)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "createUserId", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createUserName", length = 50)
	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateUserName", length = 50)
	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}
	
}