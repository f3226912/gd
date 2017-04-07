package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

/**
 * 统计参数DTO
 * @author xiaojun
 */
public class PublishCountDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 线路ID
	 */
	private Long id;
	/**
	 * 推荐人姓名
	 */
	private String name;
	/**
	 * 推荐人手机
	 */
	private String mobile;
	/**
	 * 被推荐人姓名
	 */
	private String name_ed;
	/**
	 * 被推荐人手机
	 */
	private String mobile_ed;
	/**
	 * 用户类型
	 */
	private String userType;
	/**
	 * 被推荐人角色
	 */
	private String level;
	/**
	 * 区域名称
	 */
	private String areaName;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 发布线路信息时间
	 */
	private String lineCreateTime;
	/**
	 * 认证状态
	 */
	private String nstStatus;
	/**
	 * 被推荐人注册时间
	 */
	private String memberCreateTime;
	/**
	 * 信息是否删除
	 */
	private String isDeleted;
	/**
	 * 被推荐人注册查询开始时间
	 */
	private String memberCreateBeginTime;
	/**
	 * 被推荐人注册查询结束时间
	 */
	private String memberCreateEndTime;
	/**
	 * 发布线路信息查询开始时间
	 */
	private String lineCreateBeginTime;
	/**
	 * 发布线路信息查询结束时间
	 */
	private String lineCreateEndTime;
	/**
	 * 发布车辆信息时间
	 */
	private String carCreateTime;
	/**
	 * 发布车辆信息查询开始时间
	 */
	private String carCreateBeginTime;
	/**
	 * 发布车辆信息查询结束时间
	 */
	private String carCreateEndTime;
	/**
	 * 发货省会Id
	 */
	private String s_provinceId;
	/**
	 * 发货城市Id
	 */
	private String s_cityId;
	/**
	 * 发货区域Id
	 */
	private String s_areaId;
	/**
	 * 收货省会Id
	 */
	private String e_provinceId;
	/**
	 * 收货城市Id
	 */
	private String e_cityId;
	/**
	 * 收货区域Id
	 */
	private String e_areaId;
	
	/**
	 * 起始地（全部）
	 */
	private String s_startAddress;
	/**
	 * 目的地（全部）
	 */
	private String e_endAddress;
	
	public String getS_startAddress() {
		return s_startAddress;
	}

	public void setS_startAddress(String s_startAddress) {
		this.s_startAddress = s_startAddress;
	}

	public String getE_endAddress() {
		return e_endAddress;
	}

	public void setE_endAddress(String e_endAddress) {
		this.e_endAddress = e_endAddress;
	}

	public String getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(String s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public String getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(String s_cityId) {
		this.s_cityId = s_cityId;
	}

	public String getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(String s_areaId) {
		this.s_areaId = s_areaId;
	}

	public String getE_provinceId() {
		return e_provinceId;
	}

	public void setE_provinceId(String e_provinceId) {
		this.e_provinceId = e_provinceId;
	}

	public String getE_cityId() {
		return e_cityId;
	}

	public void setE_cityId(String e_cityId) {
		this.e_cityId = e_cityId;
	}

	public String getE_areaId() {
		return e_areaId;
	}

	public void setE_areaId(String e_areaId) {
		this.e_areaId = e_areaId;
	}

	public String getCarCreateTime() {
		return carCreateTime;
	}

	public void setCarCreateTime(String carCreateTime) {
		this.carCreateTime = carCreateTime;
	}

	public String getCarCreateBeginTime() {
		return carCreateBeginTime;
	}

	public void setCarCreateBeginTime(String carCreateBeginTime) {
		this.carCreateBeginTime = carCreateBeginTime;
	}

	public String getCarCreateEndTime() {
		return carCreateEndTime;
	}

	public void setCarCreateEndTime(String carCreateEndTime) {
		this.carCreateEndTime = carCreateEndTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName_ed() {
		return name_ed;
	}

	public void setName_ed(String name_ed) {
		this.name_ed = name_ed;
	}

	public String getMobile_ed() {
		return mobile_ed;
	}

	public void setMobile_ed(String mobile_ed) {
		this.mobile_ed = mobile_ed;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLineCreateTime() {
		return lineCreateTime;
	}

	public void setLineCreateTime(String lineCreateTime) {
		this.lineCreateTime = lineCreateTime;
	}

	public String getNstStatus() {
		return nstStatus;
	}

	public void setNstStatus(String nstStatus) {
		this.nstStatus = nstStatus;
	}

	public String getMemberCreateTime() {
		return memberCreateTime;
	}

	public void setMemberCreateTime(String memberCreateTime) {
		this.memberCreateTime = memberCreateTime;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberCreateBeginTime() {
		return memberCreateBeginTime;
	}

	public void setMemberCreateBeginTime(String memberCreateBeginTime) {
		this.memberCreateBeginTime = memberCreateBeginTime;
	}

	public String getMemberCreateEndTime() {
		return memberCreateEndTime;
	}

	public void setMemberCreateEndTime(String memberCreateEndTime) {
		this.memberCreateEndTime = memberCreateEndTime;
	}

	public String getLineCreateBeginTime() {
		return lineCreateBeginTime;
	}

	public void setLineCreateBeginTime(String lineCreateBeginTime) {
		this.lineCreateBeginTime = lineCreateBeginTime;
	}

	public String getLineCreateEndTime() {
		return lineCreateEndTime;
	}

	public void setLineCreateEndTime(String lineCreateEndTime) {
		this.lineCreateEndTime = lineCreateEndTime;
	}
}
