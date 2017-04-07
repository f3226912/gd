package com.gudeng.commerce.gd.admin.dto;

import java.util.Date;
import java.util.List;

public class NstResult {
	private String carNumber;
	private Integer carType;
	private String carTypeVar; //车辆类型
	private String companMobile;  //物流公司电话
	private String companyName;   //物流公司名
	private String driverMobile; //司机电话
	private String driverName;   //司机名
	private String iconUrl;
	private String e_detail;     //地址
	private String e_detailed_address;//详细地址
	private String orderNo;     //订单编号
	private String shipperMobile;   //货主电话
	private String shipperName; //货主名
    private List<NstProgressLog> orderInfoTransList; //进度信息
    //private String sourceExamineDTO;  //验货信息
    private String status;//运单状态
    private String orderStatus;//物流状态
    private String confirmGoodsTime;//确认收货时间，完成时间
    private String confirmOrderTime;//确认订单时间，创建时间
    private String closeTime; //关闭时间
    private String totalWeight;  //载重
    
    
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public String getCarTypeVar() {
		return carTypeVar;
	}
	public void setCarTypeVar(String carTypeVar) {
		this.carTypeVar = carTypeVar;
	}
	public String getCompanMobile() {
		return companMobile;
	}
	public void setCompanMobile(String companMobile) {
		this.companMobile = companMobile;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getE_detail() {
		return e_detail;
	}
	public void setE_detail(String e_detail) {
		this.e_detail = e_detail;
	}
	public String getE_detailed_address() {
		return e_detailed_address;
	}
	public void setE_detailed_address(String e_detailed_address) {
		this.e_detailed_address = e_detailed_address;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getShipperMobile() {
		return shipperMobile;
	}
	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	/*public String getSourceExamineDTO() {
		return sourceExamineDTO;
	}
	public void setSourceExamineDTO(String sourceExamineDTO) {
		this.sourceExamineDTO = sourceExamineDTO;
	}*/
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	public List<NstProgressLog> getOrderInfoTransList() {
		return orderInfoTransList;
	}
	public void setOrderInfoTransList(List<NstProgressLog> orderInfoTransList) {
		this.orderInfoTransList = orderInfoTransList;
	}
	public String getConfirmGoodsTime() {
		return confirmGoodsTime;
	}
	public void setConfirmGoodsTime(String confirmGoodsTime) {
		this.confirmGoodsTime = confirmGoodsTime;
	}
	public String getConfirmOrderTime() {
		return confirmOrderTime;
	}
	public void setConfirmOrderTime(String confirmOrderTime) {
		this.confirmOrderTime = confirmOrderTime;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
   
   
}