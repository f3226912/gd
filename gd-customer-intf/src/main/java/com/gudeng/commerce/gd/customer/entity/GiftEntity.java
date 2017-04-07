package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity(name = "gift")
public class GiftEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2212813376798459860L;
	private Long id;
	private String name;
	private Long integral;
	private Integer type;
	private Date createTime;
	private String createUserId;
	private String updateUserId;
	private Date updateTime;
	private Long activityId;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "integral")
	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "activityId")
	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	
	
}
