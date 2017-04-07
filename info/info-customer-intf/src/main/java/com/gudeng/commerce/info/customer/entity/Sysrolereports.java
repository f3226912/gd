package com.gudeng.commerce.info.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Sysrolereports entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "sysrolereports")
public class Sysrolereports implements java.io.Serializable {

	// Fields

	/** 
    * @Fields serialVersionUID TODO
    * @since Ver 2.0
    */   
    
    private static final long serialVersionUID = -2359776932295690989L;
    private String rmId;
	private String roleId;
	private Long reportsId;
	private String createUserId;
	private Date createTime;

	// Constructors

	/** default constructor */
	public Sysrolereports() {
	}

	/** full constructor */
	public Sysrolereports(String rmId, String roleId, Long reportsId,
			String createUserId, Date createTime) {
		this.rmId = rmId;
		this.roleId = roleId;
		this.reportsId = reportsId;
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

	@Column(name = "reportsID", nullable = false)
	public Long getReportsId() {
		return this.reportsId;
	}

	public void setReportsId(Long reportsId) {
		this.reportsId = reportsId;
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