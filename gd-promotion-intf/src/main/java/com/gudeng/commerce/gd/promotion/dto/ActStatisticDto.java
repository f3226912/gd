package com.gudeng.commerce.gd.promotion.dto;

import java.io.Serializable;
import java.util.Date;

public class ActStatisticDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userid;//用户账号
    private String activityId;
    private String mobile;
    private Date minTime;
    private Integer countShare;
    private Integer countStars;
    private Integer countRegister;
    private Integer countJp;

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getMinTime() {
		return minTime;
	}
	public void setMinTime(Date minTime) {
		this.minTime = minTime;
	}
	public Integer getCountShare() {
		return countShare;
	}
	public void setCountShare(Integer countShare) {
		this.countShare = countShare;
	}
	public Integer getCountStars() {
		return countStars;
	}
	public void setCountStars(Integer countStars) {
		this.countStars = countStars;
	}
	public Integer getCountRegister() {
		return countRegister;
	}
	public void setCountRegister(Integer countRegister) {
		this.countRegister = countRegister;
	}
	public Integer getCountJp() {
		return countJp;
	}
	public void setCountJp(Integer countJp) {
		this.countJp = countJp;
	}
    
    
    

}
