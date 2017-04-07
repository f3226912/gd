package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "push_notice")
public class PushNoticeEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 广告轮播名称
	 */
	private String title;
	
	/**
	 * 农批中心ID
	 */
	private String content;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 链接URL
	 */
	private String linkUrl;

	/**
	 * 创建人员ID
	 */
	private String createUserId;
	
	/**
	 * 创建人员name
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改人员ID
	 */
	private String updateUserId;
	
	/**
	 * 修改人员ID
	 */
	private String updateUserName;
	
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	//所属客户端
	private String client;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	@Column(name = "createUserName")
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	@Column(name = "updateUserName")
	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "linkUrl")
	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Column(name = "client")
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	
}
