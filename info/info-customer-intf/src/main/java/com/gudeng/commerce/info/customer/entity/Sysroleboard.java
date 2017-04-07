package com.gudeng.commerce.info.customer.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Sysroleboard entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "sysroleboard")
public class Sysroleboard implements java.io.Serializable {

	// Fields

	/** 
    * @Fields serialVersionUID TODO
    * @since Ver 2.0
    */   
    
    private static final long serialVersionUID = -2795893725146027704L;
    private String rmId;
	private String roleId;
	private Long boardId;
	private String createUserId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public Sysroleboard() {
	}

	/** full constructor */
	public Sysroleboard(String rmId, String roleId, Long boardId,
			String createUserId, Date createTime) {
		this.rmId = rmId;
		this.roleId = roleId;
		this.boardId = boardId;
		this.createUserId = createUserId;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@Column(name = "rmID", unique = true, nullable = false, length = 32)
	public String getRmId() {
		return this.rmId;
	}

	public void setRmId(String rmId) {
		this.rmId = rmId;
	}

	@Column(name = "roleID", nullable = false, length = 32)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "boardID", nullable = false)
	public Long getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	@Column(name = "createUserID", nullable = false, length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}