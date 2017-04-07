package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 *被推荐人
 */
public class RecommendedUserDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5322112630363096344L;

	
	//推荐人手机号
    private String mobile;

    //推荐人
    private String userName;
    
    //被推荐人手机号
    private String recommendedMobile;
    
  //被推荐人手机号
    private String recommendedUserName;
    
    //用户类型
    private String userType;
    
    //角色
    private String levelType;
    
    //公司名称
    private String companyName;
    
     //联系人
    private String linkMan;
    
    //被推荐人车牌
    private String carNumber;
    
    //被推荐人所有车辆数量
    private int  carCount;
    
    //发布线路数量
    private int  carLineCount;
    
    //被推荐人注册时间
    private Date createUserTime;
    
    //被推荐人车辆发布时间
    private Date createCarTime;
    
    //被推荐人线路发布时间
    private Date createCarLineTime;
    
    //被推荐人货源发布时间
    private Date createAddressTime;
    
    //被推荐人拨号时间
    private Date createCallTime;
    
    //被推荐人拨号次数
    private int   callCount;
    
	//会员所属区域
	private String areaName;
	
	//农速通认证状态
	private String nstStatus;
	
	//货源状态
	private String isAddressDeleted;
	
	//拨号类型(干线,同城)
	private String source;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	private Long s_provinceId;

	private Long s_cityId;

	private Long s_areaId;
	
	private Long f_provinceId;

	private Long f_cityId;

	private Long f_areaId;
	
	private String startPlace;

	private String endPlace;
	
	//是否有推荐人
	private Long isReferees;
	
	public Long getIsReferees() {
		return isReferees;
	}

	public void setIsReferees(Long isReferees) {
		this.isReferees = isReferees;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecommendedMobile() {
		return recommendedMobile;
	}

	public void setRecommendedMobile(String recommendedMobile) {
		this.recommendedMobile = recommendedMobile;
	}

	
	public String getRecommendedUserName() {
		return recommendedUserName;
	}

	public void setRecommendedUserName(String recommendedUserName) {
		this.recommendedUserName = recommendedUserName;
	}

	public int getCarLineCount() {
		return carLineCount;
	}

	public void setCarLineCount(int carLineCount) {
		this.carLineCount = carLineCount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}


	public Date getCreateUserTime() {
		return createUserTime;
	}

	public void setCreateUserTime(Date createUserTime) {
		this.createUserTime = createUserTime;
	}

	public Date getCreateCarTime() {
		return createCarTime;
	}

	public void setCreateCarTime(Date createCarTime) {
		this.createCarTime = createCarTime;
	}

	public Date getCreateCarLineTime() {
		return createCarLineTime;
	}

	public void setCreateCarLineTime(Date createCarLineTime) {
		this.createCarLineTime = createCarLineTime;
	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public int getCarCount() {
		return carCount;
	}

	public void setCarCount(int carCount) {
		this.carCount = carCount;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Date getCreateAddressTime() {
		return createAddressTime;
	}
	
	

	public Date getCreateCallTime() {
		return createCallTime;
	}

	public void setCreateCallTime(Date createCallTime) {
		this.createCallTime = createCallTime;
	}

	public void setCreateAddressTime(Date createAddressTime) {
		this.createAddressTime = createAddressTime;
	}

	public String getNstStatus() {
		return nstStatus;
	}

	public void setNstStatus(String nstStatus) {
		this.nstStatus = nstStatus;
	}

	public String getIsAddressDeleted() {
		return isAddressDeleted;
	}

	public void setIsAddressDeleted(String isAddressDeleted) {
		this.isAddressDeleted = isAddressDeleted;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	
	public Long getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(Long s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public Long getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(Long s_cityId) {
		this.s_cityId = s_cityId;
	}

	public Long getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(Long s_areaId) {
		this.s_areaId = s_areaId;
	}

	public Long getF_provinceId() {
		return f_provinceId;
	}

	public void setF_provinceId(Long f_provinceId) {
		this.f_provinceId = f_provinceId;
	}

	public Long getF_cityId() {
		return f_cityId;
	}

	public void setF_cityId(Long f_cityId) {
		this.f_cityId = f_cityId;
	}

	public Long getF_areaId() {
		return f_areaId;
	}

	public void setF_areaId(Long f_areaId) {
		this.f_areaId = f_areaId;
	}

	
	
	
    

}
