package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_req_audit")
public class PromReqAuditEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2887147027853791377L;

	private Integer auditId;

	private String auditOpinion;

	/**
	 *  0:待审核(暂未参加活动，默认)1:已审核（参加活动)2:不通过(不参加活动),3 已取消
	 */
	private String auditStatus;

	private Date auditTime;

	private Long memberId;

	private String remark;

	private Date createTime;

	private String createUserId;

	private String updateUserId;

	private Date updateTime;
    
	@Id
	@Column(name = "auditId")
	public Integer getAuditId() {

		return this.auditId;
	}

	public void setAuditId(Integer auditId) {

		this.auditId = auditId;
	}

	@Column(name = "auditOpinion")
	public String getAuditOpinion() {

		return this.auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {

		this.auditOpinion = auditOpinion;
	}

	@Column(name = "auditStatus")
	public String getAuditStatus() {

		return this.auditStatus;
	}

	public void setAuditStatus(String auditStatus) {

		this.auditStatus = auditStatus;
	}

	@Column(name = "auditTime")
	public Date getAuditTime() {

		return this.auditTime;
	}

	public void setAuditTime(Date auditTime) {

		this.auditTime = auditTime;
	}

	@Column(name = "memberId")
	public Long getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Long memberId) {

		this.memberId = memberId;
	}

	@Column(name = "remark")
	public String getRemark() {

		return this.remark;
	}

	public void setRemark(String remark) {

		this.remark = remark;
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
