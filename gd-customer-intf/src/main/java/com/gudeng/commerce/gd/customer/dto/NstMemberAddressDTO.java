package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.MemberAddressEntity;


public class NstMemberAddressDTO extends MemberAddressEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2325373940458595877L;
	
	//会员
	private String userName ;
	
	//账号
    private String account; ;
    
	private String startPlace;

	private String endPlace;
	
	//代发布人
	private String publishUserName ;
		
	//发布人账号
    private String publishAccount;
	
    //公司名称
	private String companyName;
	
	//订单状态
	private String orderStatus;
	
	//起运地所在市
	private String cityName;
	
	//起运地所在市
	private Long cCityId;
	
	//所属区域
	private String area;
	
	//发布来源
	private String clients;
	
	//分配类型;
	private String distributeType;
	
	private String queryStartDate;
	private String queryEndDate;
	
    //总重量
    private String weightString;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

	public String getPublishUserName() {
		return publishUserName;
	}

	public void setPublishUserName(String publishUserName) {
		this.publishUserName = publishUserName;
	}

	public String getPublishAccount() {
		return publishAccount;
	}

	public void setPublishAccount(String publishAccount) {
		this.publishAccount = publishAccount;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDistributeType() {
		return distributeType;
	}

	public void setDistributeType(String distributeType) {
		this.distributeType = distributeType;
	}

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public String getWeightString() {
		return weightString;
	}

	public void setWeightString(String weightString) {
		this.weightString = weightString;
	}

	public Long getcCityId() {
		return cCityId;
	}

	public void setcCityId(Long cCityId) {
		this.cCityId = cCityId;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}
	
	
	

    
}
