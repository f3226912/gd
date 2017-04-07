package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;



public class TrunkCarLineDTO implements Serializable{

	private static final long serialVersionUID = 7335690812689115710L;

	private Long id;
	private String sendGoodsType;
	private String carType;
    private Double maxLoad;
	private Double carLength;
    private Long userType;
    private Date createTime;
    private String timeDiffString;
    private Date sentDate;
    private String sentDateType;
    private String linkMan;
    private Double price;
    private String unitType;
    private String createUserId;
    private String mobile;
    
    
    //是否认证
    private String isCertify;
	private Long memberId;
	
	private Long s_provinceId;
    private Long s_cityId;
    private Long s_areaId;  
	private Long e_provinceId;
    private Long e_cityId;
    private Long e_areaId;
	private Long s_provinceId2;
    private Long s_cityId2;
    private Long s_areaId2;  
	private Long e_provinceId2;
    private Long e_cityId2;
    private Long e_areaId2;
	private Long s_provinceId3;
    private Long s_cityId3;
    private Long s_areaId3;  
	private Long e_provinceId3;
    private Long e_cityId3;
    private Long e_areaId3;
    private Long e_provinceId4;
    private Long e_cityId4;
    private Long e_areaId4;
    private Long e_provinceId5;
    private Long e_cityId5;
    private Long e_areaId5;
	//显示距离多少千米
	private Double distance;
	
	//手机GPS所在的经纬度
	private Double mlng;
	private Double mlat;
	
	//车辆类型
	private String carTypeString;
		
	/**
	 * 新增查询条件
	 * @return
	 */
	//根据地址查询时,默认的从app端传过来的城市
	private String locationCity; 
	//车长
	private Double carLengthFrom;
	private Double carLengthTo;
	//载重
	private Double maxLoadFrom;
	private Double maxLoadTo;
	//农速通2.0 用户头像
	private String Andupurl;
	//离我最近(选项)
  	private String closest;
  	//里程
    private Double mileage;
    //发货地址拼接
    private String s_provinceStr;
    private String s_cityStr;
    private String s_areaStr;
    private String s_provinceStr2;
    private String s_cityStr2;
    private String s_areaStr2;  
    private String s_provinceStr3;
    private String s_cityStr3;
    private String s_areaStr3;  
    //收货地址拼接
	private String e_provinceStr;
    private String e_cityStr;
    private String e_areaStr;
    private String e_provinceStr2;
    private String e_cityStr2;
    private String e_areaStr2;
    private String e_provinceStr3;
    private String e_cityStr3;
    private String e_areaStr3;
    private String e_provinceStr4;
    private String e_cityStr4;
    private String e_areaStr4;
    private String e_provinceStr5;
    private String e_cityStr5;
    private String e_areaStr5;

	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public Double getMaxLoad() {
		return maxLoad;
	}
	public void setMaxLoad(Double maxLoad) {
		this.maxLoad = maxLoad;
	}
	public Double getCarLength() {
		return carLength;
	}
	public void setCarLength(Double carLength) {
		this.carLength = carLength;
	}
	public Long getUserType() {
		return userType;
	}
	public void setUserType(Long userType) {
		this.userType = userType;
	}
	public String getIsCertify() {
		return isCertify;
	}
	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
	public Long getE_provinceId() {
		return e_provinceId;
	}
	public void setE_provinceId(Long e_provinceId) {
		this.e_provinceId = e_provinceId;
	}
	public Long getE_cityId() {
		return e_cityId;
	}
	public void setE_cityId(Long e_cityId) {
		this.e_cityId = e_cityId;
	}
	public Long getE_areaId() {
		return e_areaId;
	}
	public void setE_areaId(Long e_areaId) {
		this.e_areaId = e_areaId;
	}
	public Long getS_provinceId2() {
		return s_provinceId2;
	}
	public void setS_provinceId2(Long s_provinceId2) {
		this.s_provinceId2 = s_provinceId2;
	}
	public Long getS_cityId2() {
		return s_cityId2;
	}
	public void setS_cityId2(Long s_cityId2) {
		this.s_cityId2 = s_cityId2;
	}
	public Long getS_areaId2() {
		return s_areaId2;
	}
	public void setS_areaId2(Long s_areaId2) {
		this.s_areaId2 = s_areaId2;
	}
	public Long getE_provinceId2() {
		return e_provinceId2;
	}
	public void setE_provinceId2(Long e_provinceId2) {
		this.e_provinceId2 = e_provinceId2;
	}
	public Long getE_cityId2() {
		return e_cityId2;
	}
	public void setE_cityId2(Long e_cityId2) {
		this.e_cityId2 = e_cityId2;
	}
	public Long getE_areaId2() {
		return e_areaId2;
	}
	public void setE_areaId2(Long e_areaId2) {
		this.e_areaId2 = e_areaId2;
	}
	public Long getS_provinceId3() {
		return s_provinceId3;
	}
	public void setS_provinceId3(Long s_provinceId3) {
		this.s_provinceId3 = s_provinceId3;
	}
	public Long getS_cityId3() {
		return s_cityId3;
	}
	public void setS_cityId3(Long s_cityId3) {
		this.s_cityId3 = s_cityId3;
	}
	public Long getS_areaId3() {
		return s_areaId3;
	}
	public void setS_areaId3(Long s_areaId3) {
		this.s_areaId3 = s_areaId3;
	}
	public Long getE_provinceId3() {
		return e_provinceId3;
	}
	public void setE_provinceId3(Long e_provinceId3) {
		this.e_provinceId3 = e_provinceId3;
	}
	public Long getE_cityId3() {
		return e_cityId3;
	}
	public void setE_cityId3(Long e_cityId3) {
		this.e_cityId3 = e_cityId3;
	}
	public Long getE_areaId3() {
		return e_areaId3;
	}
	public void setE_areaId3(Long e_areaId3) {
		this.e_areaId3 = e_areaId3;
	}
	public Long getE_provinceId4() {
		return e_provinceId4;
	}
	public void setE_provinceId4(Long e_provinceId4) {
		this.e_provinceId4 = e_provinceId4;
	}
	public Long getE_cityId4() {
		return e_cityId4;
	}
	public void setE_cityId4(Long e_cityId4) {
		this.e_cityId4 = e_cityId4;
	}
	public Long getE_areaId4() {
		return e_areaId4;
	}
	public void setE_areaId4(Long e_areaId4) {
		this.e_areaId4 = e_areaId4;
	}
	public Long getE_provinceId5() {
		return e_provinceId5;
	}
	public void setE_provinceId5(Long e_provinceId5) {
		this.e_provinceId5 = e_provinceId5;
	}
	public Long getE_cityId5() {
		return e_cityId5;
	}
	public void setE_cityId5(Long e_cityId5) {
		this.e_cityId5 = e_cityId5;
	}
	public Long getE_areaId5() {
		return e_areaId5;
	}
	public void setE_areaId5(Long e_areaId5) {
		this.e_areaId5 = e_areaId5;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
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
	public String getCarTypeString() {
		return carTypeString;
	}
	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
	}
	public String getAndupurl() {
		return Andupurl;
	}
	public void setAndupurl(String andupurl) {
		Andupurl = andupurl;
	}
	public String getClosest() {
		return closest;
	}
	public void setClosest(String closest) {
		this.closest = closest;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public String getS_provinceStr() {
		return s_provinceStr;
	}
	public void setS_provinceStr(String s_provinceStr) {
		this.s_provinceStr = s_provinceStr;
	}
	public String getS_cityStr() {
		return s_cityStr;
	}
	public void setS_cityStr(String s_cityStr) {
		this.s_cityStr = s_cityStr;
	}
	public String getS_areaStr() {
		return s_areaStr;
	}
	public void setS_areaStr(String s_areaStr) {
		this.s_areaStr = s_areaStr;
	}
	public String getS_provinceStr2() {
		return s_provinceStr2;
	}
	public void setS_provinceStr2(String s_provinceStr2) {
		this.s_provinceStr2 = s_provinceStr2;
	}
	public String getS_cityStr2() {
		return s_cityStr2;
	}
	public void setS_cityStr2(String s_cityStr2) {
		this.s_cityStr2 = s_cityStr2;
	}
	public String getS_areaStr2() {
		return s_areaStr2;
	}
	public void setS_areaStr2(String s_areaStr2) {
		this.s_areaStr2 = s_areaStr2;
	}
	public String getS_provinceStr3() {
		return s_provinceStr3;
	}
	public void setS_provinceStr3(String s_provinceStr3) {
		this.s_provinceStr3 = s_provinceStr3;
	}
	public String getS_cityStr3() {
		return s_cityStr3;
	}
	public void setS_cityStr3(String s_cityStr3) {
		this.s_cityStr3 = s_cityStr3;
	}
	public String getS_areaStr3() {
		return s_areaStr3;
	}
	public void setS_areaStr3(String s_areaStr3) {
		this.s_areaStr3 = s_areaStr3;
	}
	public String getE_provinceStr() {
		return e_provinceStr;
	}
	public void setE_provinceStr(String e_provinceStr) {
		this.e_provinceStr = e_provinceStr;
	}
	public String getE_cityStr() {
		return e_cityStr;
	}
	public void setE_cityStr(String e_cityStr) {
		this.e_cityStr = e_cityStr;
	}
	public String getE_areaStr() {
		return e_areaStr;
	}
	public void setE_areaStr(String e_areaStr) {
		this.e_areaStr = e_areaStr;
	}
	public String getE_provinceStr2() {
		return e_provinceStr2;
	}
	public void setE_provinceStr2(String e_provinceStr2) {
		this.e_provinceStr2 = e_provinceStr2;
	}
	public String getE_cityStr2() {
		return e_cityStr2;
	}
	public void setE_cityStr2(String e_cityStr2) {
		this.e_cityStr2 = e_cityStr2;
	}
	public String getE_areaStr2() {
		return e_areaStr2;
	}
	public void setE_areaStr2(String e_areaStr2) {
		this.e_areaStr2 = e_areaStr2;
	}
	public String getE_provinceStr3() {
		return e_provinceStr3;
	}
	public void setE_provinceStr3(String e_provinceStr3) {
		this.e_provinceStr3 = e_provinceStr3;
	}
	public String getE_cityStr3() {
		return e_cityStr3;
	}
	public void setE_cityStr3(String e_cityStr3) {
		this.e_cityStr3 = e_cityStr3;
	}
	public String getE_areaStr3() {
		return e_areaStr3;
	}
	public void setE_areaStr3(String e_areaStr3) {
		this.e_areaStr3 = e_areaStr3;
	}
	public String getE_provinceStr4() {
		return e_provinceStr4;
	}
	public void setE_provinceStr4(String e_provinceStr4) {
		this.e_provinceStr4 = e_provinceStr4;
	}
	public String getE_cityStr4() {
		return e_cityStr4;
	}
	public void setE_cityStr4(String e_cityStr4) {
		this.e_cityStr4 = e_cityStr4;
	}
	public String getE_areaStr4() {
		return e_areaStr4;
	}
	public void setE_areaStr4(String e_areaStr4) {
		this.e_areaStr4 = e_areaStr4;
	}
	public String getE_provinceStr5() {
		return e_provinceStr5;
	}
	public void setE_provinceStr5(String e_provinceStr5) {
		this.e_provinceStr5 = e_provinceStr5;
	}
	public String getE_cityStr5() {
		return e_cityStr5;
	}
	public void setE_cityStr5(String e_cityStr5) {
		this.e_cityStr5 = e_cityStr5;
	}
	public String getE_areaStr5() {
		return e_areaStr5;
	}
	public void setE_areaStr5(String e_areaStr5) {
		this.e_areaStr5 = e_areaStr5;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTimeDiffString() {
		return timeDiffString;
	}
	public void setTimeDiffString(String timeDiffString) {
		this.timeDiffString = timeDiffString;
	}
	public String getSentDateType() {
		return sentDateType;
	}
	public void setSentDateType(String sentDateType) {
		this.sentDateType = sentDateType;
	}
	public Double getCarLengthFrom() {
		return carLengthFrom;
	}
	public void setCarLengthFrom(Double carLengthFrom) {
		this.carLengthFrom = carLengthFrom;
	}
	public Double getCarLengthTo() {
		return carLengthTo;
	}
	public void setCarLengthTo(Double carLengthTo) {
		this.carLengthTo = carLengthTo;
	}
	public Double getMaxLoadFrom() {
		return maxLoadFrom;
	}
	public void setMaxLoadFrom(Double maxLoadFrom) {
		this.maxLoadFrom = maxLoadFrom;
	}
	public Double getMaxLoadTo() {
		return maxLoadTo;
	}
	public void setMaxLoadTo(Double maxLoadTo) {
		this.maxLoadTo = maxLoadTo;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSendGoodsType() {
		return sendGoodsType;
	}
	public void setSendGoodsType(String sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
    
}
