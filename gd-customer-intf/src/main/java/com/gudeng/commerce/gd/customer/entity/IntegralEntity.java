package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="integral")
public class IntegralEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer type;
	
	private Long activityId;
	
	private Long memberId;
	
	private Long memberId_ed;
	
	private Long integral;
	
	private Long giftId;
	
	private Date createTime;
	
	private String createUserId;
	
	private Date updateTime;
	
	private String updateUserId;
	
	private String description;
	
	private Integer isReturn;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name="activityId")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	@Column(name="memberId")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name="memberId_ed")
	public Long getMemberId_ed() {
		return memberId_ed;
	}

	public void setMemberId_ed(Long memberId_ed) {
		this.memberId_ed = memberId_ed;
	}

	@Column(name="integral")
	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	@Column(name="giftId")
	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	@Column(name="createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name="updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="isReturn")
	public Integer getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(Integer isReturn) {
		this.isReturn = isReturn;
	}
	
}
