package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.customer.entity.MemberAddressEntity;


public class MemberAddressDTO extends MemberAddressEntity  implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6471148965342187843L;


	private String s_provinceName;

    private String  s_cityName;
    
	private String s_areaName;
	
	private String f_provinceName;
    
    private String  f_cityName;
    
	private String f_areaName;

	private String isPersonalLine;

	
	private String carTypeString;
	
	//货物类型(0:常温货；1：冷藏；2：鲜活水产；3：其他)
	private String goodsTypeString;
	
	//发运方式(0:零担;1:整车;2:其他)
	private String sendGoodsTypeString;

	private String createTimeString;
	
	private String updateTimeString;
    
    private String s_provinceIdS;
    private String s_cityIdS;
    private String s_areaIdS;  
	private String e_provinceIdS;
    private String e_cityIdS;
    private String e_areaIdS;
    
	private String startPlace;

	private String endPlace;
	private String app;
	private String timebefore;
	/**
	 * 公司电话
	 */
	private String companyMobile;
	private String companyContact;
	private String companyName;
	
	private String userTypeName;
	
    //是否认证
    private String isCertify;
    
	private String  oldCreateUserId;
	private Integer startRow=0;
	private Integer endRow=10;


	//显示距离多少千米
	private Double distance;
	
	//手机GPS所在的经纬度
	private Double mlng;
	private Double mlat;
	
	//城市的经纬度
	private Double clng;
	private Double clat;
	
	
	//查询条件 mCity
	private String mCity;
	
	//城市的经纬度
	private Double bjlng;
	private Double bjlat;
	
	//城市的经纬度
	private Double cqlng;
	private Double cqlat;
	
	//城市的经纬度
	private Double shlng;
	private Long ccityId;
	
	//常用城市
	private String baseCity;
	
	/**
	 * 保留推荐信息的定位开始城市ID
	 */
	private String cityId;
	
	/**
	 * 货源ID
	 */
	private String mbId;
	
	/**
	 * 推送信息，线路字表cl_id的集合
	 */
	private List<Long> carLineIds;
	/*
	 * add by yanghaoyu 2016-03-10 两个城市之间驾车线路
	 */
	private String mileage;
	
	/**
	 * 新增查询条件
	 * @return
	 */
	private String carLengthCondition1;
	private String carLengthCondition2;
	private String carLengthString;
	private String weightCondition1;
	private String weightCondition2;
	private String sizeCondition1;
	private String sizeCondition2;
	private String orderStatus;
	private String ifOrder;
	private String andupurl;
	private String companyAndupurl;
	private String carTypeStringCondition;
	private String totalWeightString;
	
	private String beginTime;
	private String endTime;
	
	private Integer dayLimit;
	private Integer monthLimit;
	private Integer dayCount;
	private Integer monthCount;
	private String clients;
	private Integer nstRule;
	
	private String s_address_detail;
	
	private String f_address_detail;
	
	private String isOrderDeleted ;
	private String orderNo;
	
	private String shipperName;
	/**
	 * 货主电话
	 */
	private String shipperMobile;
	
	private String supplySourceType;
	
	private Boolean showRejectAndCal;
	
	private String nst_cardPhotoUrl;
	
	private String nst_vehiclePhotoUrl;
	
	private String nst_driverPhotoUrl;
	
	private String carNumber;
	
	private Integer orderStatusInt;
	
	private Integer PDDNum; //货物订单数量
	
	private String ownerName; //车主
	
	private String ownerMobile; //车主电话
	
	private String nobId;
	
	private String contactMobile; //
	
	/**
	 * <p>信息来源
	 * <p>0: 干线, 1: 同城
	 */
	private Integer source;	
	
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getNstRule() {
		return nstRule;
	}

	public void setNstRule(Integer nstRule) {
		this.nstRule = nstRule;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public Integer getDayLimit() {
		return dayLimit;
	}

	public void setDayLimit(Integer dayLimit) {
		this.dayLimit = dayLimit;
	}

	public Integer getMonthLimit() {
		return monthLimit;
	}

	public void setMonthLimit(Integer monthLimit) {
		this.monthLimit = monthLimit;
	}

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}

	public Integer getMonthCount() {
		return monthCount;
	}

	public void setMonthCount(Integer monthCount) {
		this.monthCount = monthCount;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTotalWeightString() {
		return totalWeightString;
	}

	public void setTotalWeightString(String totalWeightString) {
		this.totalWeightString = totalWeightString;
	}

	public String getCarTypeStringCondition() {
		return carTypeStringCondition;
	}

	public void setCarTypeStringCondition(String carTypeStringCondition) {
		this.carTypeStringCondition = carTypeStringCondition;
	}

	public String getCarLengthString() {
		return carLengthString;
	}

	public void setCarLengthString(String carLengthString) {
		this.carLengthString = carLengthString;
	}

	public String getAndupurl() {
		return andupurl;
	}

	public void setAndupurl(String andupurl) {
		this.andupurl = andupurl;
	}

	public String getCompanyAndupurl() {
		return companyAndupurl;
	}

	public void setCompanyAndupurl(String companyAndupurl) {
		this.companyAndupurl = companyAndupurl;
	}

	public String getIfOrder() {
		return ifOrder;
	}

	public void setIfOrder(String ifOrder) {
		this.ifOrder = ifOrder;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCarLengthCondition1() {
		return carLengthCondition1;
	}

	public void setCarLengthCondition1(String carLengthCondition1) {
		this.carLengthCondition1 = carLengthCondition1;
	}

	public String getCarLengthCondition2() {
		return carLengthCondition2;
	}

	public void setCarLengthCondition2(String carLengthCondition2) {
		this.carLengthCondition2 = carLengthCondition2;
	}

	public String getWeightCondition1() {
		return weightCondition1;
	}

	public void setWeightCondition1(String weightCondition1) {
		this.weightCondition1 = weightCondition1;
	}

	public String getWeightCondition2() {
		return weightCondition2;
	}

	public void setWeightCondition2(String weightCondition2) {
		this.weightCondition2 = weightCondition2;
	}

	public String getSizeCondition1() {
		return sizeCondition1;
	}

	public void setSizeCondition1(String sizeCondition1) {
		this.sizeCondition1 = sizeCondition1;
	}

	public String getSizeCondition2() {
		return sizeCondition2;
	}

	public void setSizeCondition2(String sizeCondition2) {
		this.sizeCondition2 = sizeCondition2;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public List<Long> getCarLineIds() {
		return carLineIds;
	}

	public void setCarLineIds(List<Long> carLineIds) {
		this.carLineIds = carLineIds;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Long getCcityId() {
		return ccityId;
	}

	public void setCcityId(Long ccityId) {
		this.ccityId = ccityId;
	}

	public Double getBjlng() {
		return bjlng;
	}

	public void setBjlng(Double bjlng) {
		this.bjlng = bjlng;
	}

	public Double getBjlat() {
		return bjlat;
	}

	public void setBjlat(Double bjlat) {
		this.bjlat = bjlat;
	}

	public Double getCqlng() {
		return cqlng;
	}

	public void setCqlng(Double cqlng) {
		this.cqlng = cqlng;
	}

	public Double getCqlat() {
		return cqlat;
	}

	public void setCqlat(Double cqlat) {
		this.cqlat = cqlat;
	}

	public Double getShlng() {
		return shlng;
	}

	public void setShlng(Double shlng) {
		this.shlng = shlng;
	}

	public Double getShlat() {
		return shlat;
	}

	public void setShlat(Double shlat) {
		this.shlat = shlat;
	}

	public Double getTjlng() {
		return tjlng;
	}

	public void setTjlng(Double tjlng) {
		this.tjlng = tjlng;
	}

	public Double getTjlat() {
		return tjlat;
	}

	public void setTjlat(Double tjlat) {
		this.tjlat = tjlat;
	}

	private Double shlat;
	
	//城市的经纬度
	private Double tjlng;
	private Double tjlat;
	
	
	public String getmCity() {
		return mCity;
	}

	public void setmCity(String mCity) {
		this.mCity = mCity;
	}

	public Double getMlng() {
		return mlng;
	}

	public void setMlng(Double mlng) {
		this.mlng = mlng;
	}

	public Double getMlat() {
		return mlat;
	}

	public void setMlat(Double mlat) {
		this.mlat = mlat;
	}

	public Double getClng() {
		return clng;
	}

	public void setClng(Double clng) {
		this.clng = clng;
	}

	public Double getClat() {
		return clat;
	}

	public void setClat(Double clat) {
		this.clat = clat;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public String getOldCreateUserId() {
		return oldCreateUserId;
	}

	public void setOldCreateUserId(String oldCreateUserId) {
		this.oldCreateUserId = oldCreateUserId;
	}

	public String getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(String companyContact) {
		this.companyContact = companyContact;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public String getTimebefore() {
		return timebefore;
	}

	public void setTimebefore(String timebefore) {
		this.timebefore = timebefore;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getS_provinceIdS() {
		return s_provinceIdS;
	}

	public void setS_provinceIdS(String s_provinceIdS) {
		this.s_provinceIdS = s_provinceIdS;
	}

	public String getS_cityIdS() {
		return s_cityIdS;
	}

	public void setS_cityIdS(String s_cityIdS) {
		this.s_cityIdS = s_cityIdS;
	}

	public String getS_areaIdS() {
		return s_areaIdS;
	}

	public void setS_areaIdS(String s_areaIdS) {
		this.s_areaIdS = s_areaIdS;
	}

	public String getE_provinceIdS() {
		return e_provinceIdS;
	}

	public void setE_provinceIdS(String e_provinceIdS) {
		this.e_provinceIdS = e_provinceIdS;
	}

	public String getE_cityIdS() {
		return e_cityIdS;
	}

	public void setE_cityIdS(String e_cityIdS) {
		this.e_cityIdS = e_cityIdS;
	}

	public String getE_areaIdS() {
		return e_areaIdS;
	}

	public void setE_areaIdS(String e_areaIdS) {
		this.e_areaIdS = e_areaIdS;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	private String sendDateString;
	private String endDateString;
	
	//发车时间类别(0:上午,1:中午,2:下午,3:晚上)
	private String sendDateTypeString;
	private String endDateTypeString;
	private String priceString;
	
	public String getEndDateTypeString() {
		return endDateTypeString;
	}

	public void setEndDateTypeString(String endDateTypeString) {
		this.endDateTypeString = endDateTypeString;
	}

	private String nickName;
	
	private String hundredweightString;
	
	private String s_provinceIdString;

    private String  s_cityIdString;
    
	private String s_areaIdString;
	
	private String f_provinceIdString;
    
    private String  f_cityIdString;
    
	private String f_areaIdString;

	/**
	 * add by yanghaoyu 10/22
	 * @return
	 */
	private String priceUnitTypeString;
	
	/**
	 * add by yanghaoyu 10/22 货源列表上,显示的姓名+先生的
	 */
	private String contact;
	public String getS_provinceIdString() {
		return s_provinceIdString;
	}

	public void setS_provinceIdString(String s_provinceIdString) {
		this.s_provinceIdString = s_provinceIdString;
	}

	public String getS_cityIdString() {
		return s_cityIdString;
	}

	public void setS_cityIdString(String s_cityIdString) {
		this.s_cityIdString = s_cityIdString;
	}

	public String getS_areaIdString() {
		return s_areaIdString;
	}

	public void setS_areaIdString(String s_areaIdString) {
		this.s_areaIdString = s_areaIdString;
	}

	public String getF_provinceIdString() {
		return f_provinceIdString;
	}

	public void setF_provinceIdString(String f_provinceIdString) {
		this.f_provinceIdString = f_provinceIdString;
	}

	public String getF_cityIdString() {
		return f_cityIdString;
	}

	public void setF_cityIdString(String f_cityIdString) {
		this.f_cityIdString = f_cityIdString;
	}

	public String getF_areaIdString() {
		return f_areaIdString;
	}

	public void setF_areaIdString(String f_areaIdString) {
		this.f_areaIdString = f_areaIdString;
	}

	public String getHundredweightString() {
		return hundredweightString;
	}

	public void setHundredweightString(String hundredweightString) {
		this.hundredweightString = hundredweightString;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPriceUnitTypeString() {
		return priceUnitTypeString;
	}

	public void setPriceUnitTypeString(String priceUnitTypeString) {
		this.priceUnitTypeString = priceUnitTypeString;
	}

	public String getS_provinceName() {
		return s_provinceName;
	}

	public void setS_provinceName(String s_provinceName) {
		this.s_provinceName = s_provinceName;
	}

	public String getS_cityName() {
		return s_cityName;
	}

	public void setS_cityName(String s_cityName) {
		this.s_cityName = s_cityName;
	}

	public String getS_areaName() {
		return s_areaName;
	}

	public void setS_areaName(String s_areaName) {
		this.s_areaName = s_areaName;
	}

	public String getF_provinceName() {
		return f_provinceName;
	}

	public void setF_provinceName(String f_provinceName) {
		this.f_provinceName = f_provinceName;
	}

	public String getF_cityName() {
		return f_cityName;
	}

	public void setF_cityName(String f_cityName) {
		this.f_cityName = f_cityName;
	}

	public String getF_areaName() {
		return f_areaName;
	}

	public void setF_areaName(String f_areaName) {
		this.f_areaName = f_areaName;
	}

	public String getIsPersonalLine() {
		return isPersonalLine;
	}

	public void setIsPersonalLine(String isPersonalLine) {
		this.isPersonalLine = isPersonalLine;
	}
	
	public String getCarTypeString() {
		return carTypeString;
	}

	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
	}

	
	public String getGoodsTypeString() {
		return goodsTypeString;
	}

	public void setGoodsTypeString(String goodsTypeString) {
		this.goodsTypeString = goodsTypeString;
	}

	public String getSendGoodsTypeString() {
		return sendGoodsTypeString;
	}

	public void setSendGoodsTypeString(String sendGoodsTypeString) {
		this.sendGoodsTypeString = sendGoodsTypeString;
	}
	
	public String getSendDateString() {
		return sendDateString;
	}

	public void setSendDateString(String sendDateString) {
		this.sendDateString = sendDateString;
	}

	public String getSendDateTypeString() {
		return sendDateTypeString;
	}

	public void setSendDateTypeString(String sendDateTypeString) {
		this.sendDateTypeString = sendDateTypeString;
	}

	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getCompanyMobile() {
		return companyMobile;
	}

	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public String getIsCertify() {
		return isCertify;
	}

	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}

	public String getBaseCity() {
		return baseCity;
	}

	public void setBaseCity(String baseCity) {
		this.baseCity = baseCity;
	}

	public String getIsOrderDeleted() {
		return isOrderDeleted;
	}

	public void setIsOrderDeleted(String isOrderDeleted) {
		this.isOrderDeleted = isOrderDeleted;
	}

	public String getS_address_detail() {
		return s_address_detail;
	}

	public void setS_address_detail(String s_address_detail) {
		this.s_address_detail = s_address_detail;
	}

	public String getF_address_detail() {
		return f_address_detail;
	}

	public void setF_address_detail(String f_address_detail) {
		this.f_address_detail = f_address_detail;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getShipperMobile() {
		return shipperMobile;
	}

	public void setShipperMobile(String shipperMobile) {
		this.shipperMobile = shipperMobile;
	}

	public String getSupplySourceType() {
		return supplySourceType;
	}

	public void setSupplySourceType(String supplySourceType) {
		this.supplySourceType = supplySourceType;
	}

	public Boolean getShowRejectAndCal() {
		return showRejectAndCal;
	}

	public void setShowRejectAndCal(Boolean showRejectAndCal) {
		this.showRejectAndCal = showRejectAndCal;
	}

	public String getNst_cardPhotoUrl() {
		return nst_cardPhotoUrl;
	}

	public void setNst_cardPhotoUrl(String nst_cardPhotoUrl) {
		this.nst_cardPhotoUrl = nst_cardPhotoUrl;
	}

	public String getNst_vehiclePhotoUrl() {
		return nst_vehiclePhotoUrl;
	}

	public void setNst_vehiclePhotoUrl(String nst_vehiclePhotoUrl) {
		this.nst_vehiclePhotoUrl = nst_vehiclePhotoUrl;
	}

	public String getNst_driverPhotoUrl() {
		return nst_driverPhotoUrl;
	}

	public void setNst_driverPhotoUrl(String nst_driverPhotoUrl) {
		this.nst_driverPhotoUrl = nst_driverPhotoUrl;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Integer getOrderStatusInt() {
		return orderStatusInt;
	}

	public void setOrderStatusInt(Integer orderStatusInt) {
		this.orderStatusInt = orderStatusInt;
	}

	public Integer getPDDNum() {
		return PDDNum;
	}

	public void setPDDNum(Integer pDDNum) {
		PDDNum = pDDNum;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerMobile() {
		return ownerMobile;
	}

	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}

	public String getNobId() {
		return nobId;
	}

	public void setNobId(String nobId) {
		this.nobId = nobId;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}	
}
