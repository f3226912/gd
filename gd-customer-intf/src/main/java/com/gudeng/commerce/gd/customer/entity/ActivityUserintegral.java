package com.gudeng.commerce.gd.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 活动用户积分表
 */
@Entity(name = "activity_userintegral")
public class ActivityUserintegral implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer memberId;
	private String activityType;
	private Integer totalIntegral;
	private Integer doIntegral;
	private Date createTime;
	private String status;

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "memberId", nullable = false)
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	@Column(name = "activityType", length = 1)
	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Column(name = "totalIntegral")
	public Integer getTotalIntegral() {
		return this.totalIntegral;
	}

	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	@Column(name = "doIntegral")
	public Integer getDoIntegral() {
		return this.doIntegral;
	}

	public void setDoIntegral(Integer doIntegral) {
		this.doIntegral = doIntegral;
	}

	@Column(name = "createTime", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "status", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}