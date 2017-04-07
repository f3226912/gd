package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

public class ActivityEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private Date startTime;
	
	private Date endTime;
	
	private Long limitIntegral;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getLimitIntegral() {
		return limitIntegral;
	}

	public void setLimitIntegral(Long limitIntegral) {
		this.limitIntegral = limitIntegral;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
