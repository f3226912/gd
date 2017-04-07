package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;

public class NstMemberBaseinfoDTO extends MemberBaseinfoEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9193809674282440232L;

	// 所属区域
	private String areaName;

	// 认证状态
	private String nstStatus;

	// 用户类型
	private String userTypeName;

	//常用城市ID
    private Long cCityId;
    
	// 常用城市
	private String cityName;

	// 账号状态
	private String accountStatus;

	// 注册来源
	private String regType;
	
	//个人或者企业
	private String nstUserType;

	// 公司名称
	private String companyName;
	
	//农速通创建时间
	private Date nstCreateTime;
	
	
	// 推荐人手机号
	private String refereeMobile;

	//推荐人姓名 
	private String refereeRealName;

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getNstStatus() {
		return nstStatus;
	}

	public void setNstStatus(String nstStatus) {
		this.nstStatus = nstStatus;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	
	public Long getcCityId() {
		return cCityId;
	}

	public void setcCityId(Long cCityId) {
		this.cCityId = cCityId;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}


	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getNstUserType() {
		return nstUserType;
	}

	public void setNstUserType(String nstUserType) {
		this.nstUserType = nstUserType;
	}

	public Date getNstCreateTime() {
		return nstCreateTime;
	}

	public void setNstCreateTime(Date nstCreateTime) {
		this.nstCreateTime = nstCreateTime;
	}

	public String getRefereeMobile() {
		return refereeMobile;
	}

	public void setRefereeMobile(String refereeMobile) {
		this.refereeMobile = refereeMobile;
	}

	public String getRefereeRealName() {
		return refereeRealName;
	}

	public void setRefereeRealName(String refereeRealName) {
		this.refereeRealName = refereeRealName;
	}
	
	
}
