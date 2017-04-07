package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.customer.entity.NstSameCityCarlineEntity;

public class NstSameCityCarlineEntityDTO extends NstSameCityCarlineEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682095549655496689L;
    
	private Long userId;//用户id
	
    private String carNumber;//车牌号码
	
	private String carType;//车牌种类
	
	private Long userType;//农速通用户1,个人,2 公司
	
	private String distance;//距离(两地经纬度计算) 单位公里
	
	private String carLength;//车长
	
	private String realName;//司机真实姓名
	
	private String mobile;//司机电话
	
	private String dayNum;//离发布日期计算出来的天数
	
	private String minLoad;//最小车辆载重 用于查询
	
	private String maxLoad;//最大车辆载重 用于查询
	
	private String cityName;//手机端传过来的中文城市 用于查询
	
	private String queryFlag;//查询条件 time时间查询  range距离查询
	
	private String transportCarType;//同城运输车辆类型(中文表示):0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货
	
	private String transportCarTypeId;//同城运输车辆类型(数字表示)
	
	//价格
	private String priceString;
	/**
	 *企业名称
	 */
	private String companyName;
	
	/**
	 *所属区域
	 */
	private String areaName;
	
	private String s_city_area;//始发地 城市+区
	
	private String e_city_area;//目的地 城市+区
	
	private String sendGoodsTypeString;//发货方式 中文
	
    private String Andupurl; //用户头像
    
    private String isCertify;//是否认证
    
    /**
	 * 我要找货中，标示 货源是否已经接单
	 */
	private String showGoodsStatus;
	/**
	 * 订单状态
	 */
	private Integer orderStatus;
		
	
	/**
	 * 客户端纬度
	 */
	private Double clat;
	/**
	 * 客户端经度
	 */
	private Double clng;
	
	private Integer startRow=0;
	
	private Integer endRow=10;
	
	private String messageId;
	
	/**
	 * 推送信息，线路字表cl_id的集合
	 */
	private List<Long> cityAddressIds;
	
	private String mbId;//推送消息 货源id
	
	private String clId;//推送消息 线路id
	/**
	 * 当前登录人memberId
	 */
	private String currentMemberId;
	/**
	 * 发货地省份name
	 */
	private String s_provinceName;
	/**
	 * 发货地城市name
	 */
	private String s_cityName;
	/**
	 * 发货地区域name
	 */
	private String s_areaName;
	
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

	public String getCurrentMemberId() {
		return currentMemberId;
	}

	public void setCurrentMemberId(String currentMemberId) {
		this.currentMemberId = currentMemberId;
	}

	public Double getClat() {
		return clat;
	}

	public void setClat(Double clat) {
		this.clat = clat;
	}

	public Double getClng() {
		return clng;
	}

	public void setClng(Double clng) {
		this.clng = clng;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCarLength() {
		return carLength;
	}

	public void setCarLength(String carLength) {
		this.carLength = carLength;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDayNum() {
		return dayNum;
	}

	public void setDayNum(String dayNum) {
		this.dayNum = dayNum;
	}

	public String getMaxLoad() {
		return maxLoad;
	}

	public void setMaxLoad(String maxLoad) {
		this.maxLoad = maxLoad;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getTransportCarType() {
		return transportCarType;
	}

	public void setTransportCarType(String transportCarType) {
		this.transportCarType = transportCarType;
	}

	public String getTransportCarTypeId() {
		return transportCarTypeId;
	}

	public void setTransportCarTypeId(String transportCarTypeId) {
		this.transportCarTypeId = transportCarTypeId;
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

	public String getMinLoad() {
		return minLoad;
	}

	public void setMinLoad(String minLoad) {
		this.minLoad = minLoad;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getPriceString() {
		return priceString;
	}

	public void setPriceString(String priceString) {
		this.priceString = priceString;
	}

	public String getS_city_area() {
		return s_city_area;
	}

	public void setS_city_area(String s_city_area) {
		this.s_city_area = s_city_area;
	}

	public String getE_city_area() {
		return e_city_area;
	}

	public void setE_city_area(String e_city_area) {
		this.e_city_area = e_city_area;
	}

	public String getSendGoodsTypeString() {
		return sendGoodsTypeString;
	}

	public void setSendGoodsTypeString(String sendGoodsTypeString) {
		this.sendGoodsTypeString = sendGoodsTypeString;
	}

	public String getAndupurl() {
		return Andupurl;
	}

	public void setAndupurl(String andupurl) {
		Andupurl = andupurl;
	}

	public String getIsCertify() {
		return isCertify;
	}

	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}

	public String getShowGoodsStatus() {
		return showGoodsStatus;
	}

	public void setShowGoodsStatus(String showGoodsStatus) {
		this.showGoodsStatus = showGoodsStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Long> getCityAddressIds() {
		return cityAddressIds;
	}

	public void setCityAddressIds(List<Long> cityAddressIds) {
		this.cityAddressIds = cityAddressIds;
	}

	public String getMbId() {
		return mbId;
	}

	public void setMbId(String mbId) {
		this.mbId = mbId;
	}

	public String getClId() {
		return clId;
	}

	public void setClId(String clId) {
		this.clId = clId;
	}

	
}	
	
