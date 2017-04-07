package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.NstGoodAssignRuleEntity;

/**
 * 农速通货源分配规则DTO
 * @author xiaojun
 */
public class NstGoodAssignRuleDTO extends NstGoodAssignRuleEntity {
	
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 分配有效开始时间  查询开始
	 */
	private String assignStartBeginTime;
	/**
	 * 分配有效开始时间  查询结束
	 */
	private String assignStartEndTime;
	/**
	 * 分配有效结束时间  查询开始
	 */
	private String assignEndBeginTime;
	/**
	 * 分配有效结束时间  查询结束
	 */
	private String assignEndEndTime;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 信息部名称+账号
	 */
	private String deptName;
	
	/**
	 * 插入货源分配规则拼接多个memberId
	 */
	private String memberIdString;
	/**
	 * 插入货源分配规则拼接多个companyName
	 */
	private String companyNameString;
	
	/**
	 * 规则类型
	 */
	private String code;
	/**
	 * 选用规则  0 直接发布货源  1 代发布货源规则
	 */
	private String rule;
	/**
	 * 查询已分配数开始时间
	 */
	private String assignSumQueryStartTime;
	/**
	 * 查询已分配数结束时间
	 */
	private String assignSumQueryEndTime;
	
	public String getAssignSumQueryStartTime() {
		return assignSumQueryStartTime;
	}

	public void setAssignSumQueryStartTime(String assignSumQueryStartTime) {
		this.assignSumQueryStartTime = assignSumQueryStartTime;
	}

	public String getAssignSumQueryEndTime() {
		return assignSumQueryEndTime;
	}

	public void setAssignSumQueryEndTime(String assignSumQueryEndTime) {
		this.assignSumQueryEndTime = assignSumQueryEndTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getMemberIdString() {
		return memberIdString;
	}

	public void setMemberIdString(String memberIdString) {
		this.memberIdString = memberIdString;
	}

	public String getCompanyNameString() {
		return companyNameString;
	}

	public void setCompanyNameString(String companyNameString) {
		this.companyNameString = companyNameString;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAssignStartBeginTime() {
		return assignStartBeginTime;
	}

	public void setAssignStartBeginTime(String assignStartBeginTime) {
		this.assignStartBeginTime = assignStartBeginTime;
	}

	public String getAssignStartEndTime() {
		return assignStartEndTime;
	}

	public void setAssignStartEndTime(String assignStartEndTime) {
		this.assignStartEndTime = assignStartEndTime;
	}

	public String getAssignEndBeginTime() {
		return assignEndBeginTime;
	}

	public void setAssignEndBeginTime(String assignEndBeginTime) {
		this.assignEndBeginTime = assignEndBeginTime;
	}

	public String getAssignEndEndTime() {
		return assignEndEndTime;
	}

	public void setAssignEndEndTime(String assignEndEndTime) {
		this.assignEndEndTime = assignEndEndTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
