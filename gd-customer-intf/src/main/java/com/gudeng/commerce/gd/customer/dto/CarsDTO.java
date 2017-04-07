package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.CarsEntity;



public class CarsDTO extends CarsEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5607934240228511936L;
	
	private String nickName;
	
	private String carTypeName;
 
	private String createTimeString;
	
	private String updateTimeString;
	
	//add by yanghaoyu 2015-10-26
	//车辆类型中文全称
	private String carTypeString;
	//农速通 APP联系人 姓名+先生 先生中文
	private String contact;
	
	private Long carTotal;
	
	private String app;
    private Long userType;
    
    private String userTypeName;
    
    private String companyName;
    
    //运输类型(0:干线，1：同城)
    private String transportTypeString;
   //同城运输车辆类型:0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货
    private String  transportCarTypeString;
    
    private String interfaceType;//接口类型 1是干线选车 2是认证中心选车
    
	public Long getUserType() {
		return userType;
	}



	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}


	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}



	public String getApp() {
		return app;
	}



	public void setApp(String app) {
		this.app = app;
	}



	public Long getCarTotal() {
		return carTotal;
	}



	public void setCarTotal(Long carTotal) {
		this.carTotal = carTotal;
	}



	public String getContact() {
		return contact;
	}



	public void setContact(String contact) {
		this.contact = contact;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getCarTypeString() {
		return carTypeString;
	}


	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getCarTypeName() {
		return carTypeName;
	}


	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}


	public String getCreateTimeString() {
		return createTimeString;
	}


	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}


	public String getUpdateTimeString() {
		return updateTimeString;
	}


	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}



	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getTransportTypeString() {
		return transportTypeString;
	}



	public void setTransportTypeString(String transportTypeString) {
		this.transportTypeString = transportTypeString;
	}



	public String getTransportCarTypeString() {
		return transportCarTypeString;
	}



	public void setTransportCarTypeString(String transportCarTypeString) {
		this.transportCarTypeString = transportCarTypeString;
	}



	public String getInterfaceType() {
		return interfaceType;
	}



	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	
	
	
}
