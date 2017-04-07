package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.ActivityUserIntegralChangeEntity;

public class ActivityUserIntegralChangeDTO extends ActivityUserIntegralChangeEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9081684771650805054L;
	
	/**
	 * 保存对象时使用
	 */
	private String memberIds;
	
	/**
	 * 用户账号
	 */
	@ExcelConf(excelHeader="用户账号", sort=1)
	private String memberAccount;
	
	/**
	 * 活动编码：用于查询展示
	 */
	@ExcelConf(excelHeader="活动编号", sort=3)
	private String activityCode;
	
	/**
	 * 活动名称：用于查询展示
	 */
	@ExcelConf(excelHeader="活动名称", sort=4)
	private String activityName;
	
	/**
	 * 变更类型： 增加积分/扣减积分
	 */
	@ExcelConf(excelHeader="变更类型", sort=6)
	private String integralTypeName;
	
	/**
	 * 活动类型，用于保存到用户活动积分表
	 */
	private String activityType;
	
	/**
	 * 用户活动累计积分 客户端积分+活动新增积分
	 */
	private String totalIntegral;
	
	/**
	 * 用户活动总的有效积分 客户端积分+活动新增积分+活动扣减积分
	 */
	private String doIntegral;
	
	/**
	 * 积分变更操作时间：yyyy-mm-dd hh:mm:ss
	 */
	@ExcelConf(excelHeader="积分获取时间", sort=7)
	private String createTimeStr;
	
	/**
	 * 查询条件：开始时间
	 */
	private String startTime;
	
	/**
	 * 查询条件：结束时间
	 */
	private String endTime;
	
	public String getMemberIds() {
		return memberIds;
	}

	public void setMemberIds(String memberIds) {
		this.memberIds = memberIds;
	}

	public String getMemberAccount() {
		return memberAccount;
	}

	public void setMemberAccount(String memberAccount) {
		this.memberAccount = memberAccount;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(String totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public String getDoIntegral() {
		return doIntegral;
	}

	public void setDoIntegral(String doIntegral) {
		this.doIntegral = doIntegral;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getIntegralTypeName() {
		return integralTypeName;
	}

	public void setIntegralTypeName(String integralTypeName) {
		this.integralTypeName = integralTypeName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
