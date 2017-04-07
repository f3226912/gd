package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.CarLineEntity;



public class CarLineDTO extends CarLineEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3308193323626605709L;

	
	//发运方式
	private String sendGoodsTypeString;
	
	private String carNumber;
	
	private String carType;

    private Double maxLoad;

	private Double carLength;
	
    private Long userType;

    private String userTypeName;
    
    //是否认证
    private String isCertify;

    private String companyName;
	//
	private String startPlace;
	private int userId;
	private String app;
	private String timeBefore;
	
	private Integer startRow=0;
	private Integer endRow=10;
	private String s_provinceIdS;
    private String s_cityIdS;
    private String s_areaIdS;  
	private String e_provinceIdS;
    private String e_cityIdS;
    private String e_areaIdS;
    
	private String s_provinceIdS2;
    private String s_cityIdS2;
    private String s_areaIdS2;  
	private String e_provinceIdS2;
    private String e_cityIdS2;
    private String e_areaIdS2;
    
    
    
	private String s_provinceIdS3;
    private String s_cityIdS3;
    private String s_areaIdS3;  
	private String e_provinceIdS3;
    private String e_cityIdS3;
    private String e_areaIdS3;
	private String mCity;
	//显示距离多少千米
	private Double distance;
	
	//手机GPS所在的经纬度
	private Double mlng;
	private Double mlat;
	
	//城市的经纬度
	private Double shlng;
	private Double shlat;
	
	

	
	//城市的经纬度
	private Double bjlng;
	private Double bjlat;
	
	//城市的经纬度
	private Double cqlng;
	private Double cqlat;
	
	//城市的经纬度
	private Double tjlng;
	private Long cityId;


	private Long m_cityId;
	private Double tjlat;
	private String cl_id;
	private String cl_Id;
	
	/**
	 * add by yanghaoyu
	 * @return
	 */
	//车主简称 姓名+先生.女士
	private String contact;
	//价格单位,元/斤
	private String unitTypeString;
	//车辆类型
	private String carTypeString;
	
    //同城运输车辆类型:0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货
	private Integer transportCarType;
	
    //运输类型(0:干线，1：同城)
	private Integer transportType;
	
    private String transportTypeString;
	
	//2015-10-24 新增字段 发货省会Id
    private String s_provinceIdString;
	/**
	 * 新增查询条件
	 * @return
	 */
	private String carLengthCondition1;
	private String carLengthCondition2;
	private String weightCondition1;
	private String weightCondition2;
	//农速通2.0 用户头像
	private String Andupurl;
	//农速通2.0 订单是否可以接单
	private String orderStatus;
	private String ifOrder;
	private String carLengthString;

	private String carTypeStringCondition;

	//2015-10-24 新增字段 发货城市Id
    private String s_cityIdString;
    //2015-10-24 新增字段 发货区域Id
    private String s_areaIdString;
    //2015-10-24 新增字段 收货货省会Id
    private String e_provinceIdString;
    //2015-10-24 新增字段 收货城市Id
    private String e_cityIdString;
    //2015-10-24 新增字段 收货区域Id
    private String e_areaIdString;
	
	//end by yanghaoyu
    
    
	//2015-12-01 新增字段 发货城市Id
    private String s_provinceIdString2;
    private String s_cityIdString2;
    //2015-12-24 新增字段 发货区域Id
    private String s_areaIdString2;
    //2015-10-24 新增字段 收货货省会Id
    private String e_provinceIdString2;
    //2015-10-24 新增字段 收货城市Id
    private String e_cityIdString2;
    //2015-10-24 新增字段 收货区域Id
    private String e_areaIdString2;
	
	//end by yanghaoyu
	
    
    
	//2015-12-01 新增字段 发货城市Id
    private String s_provinceIdString3;
    
    private  String beginTime;
    private String endTime;
    
    //会员所属区域
  	private String areaName;
  	/**
  	 * 理我最近(选项)
  	 */
  	private String closest;
  	
  	/**
     * 里程
     */
    private Double mileage;
    
	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getClosest() {
		return closest;
	}

	public void setClosest(String closest) {
		this.closest = closest;
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


	public String getCarLengthString() {
		return carLengthString;
	}


	public void setCarLengthString(String carLengthString) {
		this.carLengthString = carLengthString;
	}


	public String getCarTypeStringCondition() {
		return carTypeStringCondition;
	}


	public void setCarTypeStringCondition(String carTypeStringCondition) {
		this.carTypeStringCondition = carTypeStringCondition;
	}


	public String getAndupurl() {
		return Andupurl;
	}


	public void setAndupurl(String andupurl) {
		Andupurl = andupurl;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getIfOrder() {
		return ifOrder;
	}


	public void setIfOrder(String ifOrder) {
		this.ifOrder = ifOrder;
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


	public Long getCityId() {
		return cityId;
	}


	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}


	public Long getM_cityId() {
		return m_cityId;
	}


	public void setM_cityId(Long m_cityId) {
		this.m_cityId = m_cityId;
	}


	public String getCl_Id() {
		return cl_Id;
	}


	public void setCl_Id(String cl_Id) {
		this.cl_Id = cl_Id;
	}



	public String getCl_id() {
		return cl_id;
	}


	public void setCl_id(String cl_id) {
		this.cl_id = cl_id;
	}


	
	
	

	
	
   

	public String getmCity() {
		return mCity;
	}


	public void setmCity(String mCity) {
		this.mCity = mCity;
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


	public String getTimeBefore() {
		return timeBefore;
	}


	public void setTimeBefore(String timeBefore) {
		this.timeBefore = timeBefore;
	}


	public String getApp() {
		return app;
	}


	public void setApp(String app) {
		this.app = app;
	}


	public Long getB_memberId() {
		return b_memberId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setB_memberId(Long b_memberId) {
		this.b_memberId = b_memberId;
	}


	public void setS_provinceIdS(String s_provinceIdS) {
		this.s_provinceIdS = s_provinceIdS;
	}



    
    
	public String getS_provinceIdS2() {
		return s_provinceIdS2;
	}


	public void setS_provinceIdS2(String s_provinceIdS2) {
		this.s_provinceIdS2 = s_provinceIdS2;
	}


	public String getS_cityIdS2() {
		return s_cityIdS2;
	}


	public void setS_cityIdS2(String s_cityIdS2) {
		this.s_cityIdS2 = s_cityIdS2;
	}


	public String getS_areaIdS2() {
		return s_areaIdS2;
	}


	public void setS_areaIdS2(String s_areaIdS2) {
		this.s_areaIdS2 = s_areaIdS2;
	}


	public String getE_provinceIdS2() {
		return e_provinceIdS2;
	}


	public void setE_provinceIdS2(String e_provinceIdS2) {
		this.e_provinceIdS2 = e_provinceIdS2;
	}


	public String getE_cityIdS2() {
		return e_cityIdS2;
	}


	public void setE_cityIdS2(String e_cityIdS2) {
		this.e_cityIdS2 = e_cityIdS2;
	}


	public String getE_areaIdS2() {
		return e_areaIdS2;
	}


	public void setE_areaIdS2(String e_areaIdS2) {
		this.e_areaIdS2 = e_areaIdS2;
	}


	public String getS_provinceIdS3() {
		return s_provinceIdS3;
	}


	public void setS_provinceIdS3(String s_provinceIdS3) {
		this.s_provinceIdS3 = s_provinceIdS3;
	}


	public String getS_cityIdS3() {
		return s_cityIdS3;
	}


	public void setS_cityIdS3(String s_cityIdS3) {
		this.s_cityIdS3 = s_cityIdS3;
	}


	public String getS_areaIdS3() {
		return s_areaIdS3;
	}


	public void setS_areaIdS3(String s_areaIdS3) {
		this.s_areaIdS3 = s_areaIdS3;
	}


	public String getE_provinceIdS3() {
		return e_provinceIdS3;
	}


	public void setE_provinceIdS3(String e_provinceIdS3) {
		this.e_provinceIdS3 = e_provinceIdS3;
	}


	public String getE_cityIdS3() {
		return e_cityIdS3;
	}


	public void setE_cityIdS3(String e_cityIdS3) {
		this.e_cityIdS3 = e_cityIdS3;
	}


	public String getE_areaIdS3() {
		return e_areaIdS3;
	}


	public void setE_areaIdS3(String e_areaIdS3) {
		this.e_areaIdS3 = e_areaIdS3;
	}


	public String getE_provinceIdS4() {
		return e_provinceIdS4;
	}


	public void setE_provinceIdS4(String e_provinceIdS4) {
		this.e_provinceIdS4 = e_provinceIdS4;
	}


	public String getE_cityIdS4() {
		return e_cityIdS4;
	}


	public void setE_cityIdS4(String e_cityIdS4) {
		this.e_cityIdS4 = e_cityIdS4;
	}


	public String getE_areaIdS4() {
		return e_areaIdS4;
	}


	public void setE_areaIdS4(String e_areaIdS4) {
		this.e_areaIdS4 = e_areaIdS4;
	}


	public String getE_provinceIdS5() {
		return e_provinceIdS5;
	}


	public void setE_provinceIdS5(String e_provinceIdS5) {
		this.e_provinceIdS5 = e_provinceIdS5;
	}


	public String getE_cityIdS5() {
		return e_cityIdS5;
	}


	public void setE_cityIdS5(String e_cityIdS5) {
		this.e_cityIdS5 = e_cityIdS5;
	}


	public String getE_areaIdS5() {
		return e_areaIdS5;
	}


	public void setE_areaIdS5(String e_areaIdS5) {
		this.e_areaIdS5 = e_areaIdS5;
	}


	private String e_provinceIdS4;
    private String e_cityIdS4;
    private String e_areaIdS4;  
	private String e_provinceIdS5;
    private String e_cityIdS5;
    private String e_areaIdS5;
    private Long b_memberId;
    public String getS_provinceIdS() {
		return s_provinceIdS;
	}


	public void setSt_provinceIdS(String s_provinceIdS) {
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



	
	public String getSentDateTypeString() {
		return sentDateTypeString;
	}


	public void setSentDateTypeString(String sentDateTypeString) {
		this.sentDateTypeString = sentDateTypeString;
	}


	private String endPlace;
	
	private String sendDateString; 
	
	private String createTimeString;
	
	private String endDateString;
	
	private String updateTimeString;
	
	private String nickName;
	private String sentDateTypeString;
	private String endDateTypeString;
	
	
	public String getEndDateTypeString() {
		return endDateTypeString;
	}


	public void setEndDateTypeString(String endDateTypeString) {
		this.endDateTypeString = endDateTypeString;
	}



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


	public String getE_provinceIdString() {
		return e_provinceIdString;
	}


	public void setE_provinceIdString(String e_provinceIdString) {
		this.e_provinceIdString = e_provinceIdString;
	}


	public String getE_cityIdString() {
		return e_cityIdString;
	}


	public void setE_cityIdString(String e_cityIdString) {
		this.e_cityIdString = e_cityIdString;
	}


	public String getE_areaIdString() {
		return e_areaIdString;
	}


	public void setE_areaIdString(String e_areaIdString) {
		this.e_areaIdString = e_areaIdString;
	}


    public String getS_provinceIdString2() {
		return s_provinceIdString2;
	}


	public void setS_provinceIdString2(String s_provinceIdString2) {
		this.s_provinceIdString2 = s_provinceIdString2;
	}


	public String getS_cityIdString2() {
		return s_cityIdString2;
	}


	public void setS_cityIdString2(String s_cityIdString2) {
		this.s_cityIdString2 = s_cityIdString2;
	}


	public String getS_areaIdString2() {
		return s_areaIdString2;
	}


	public void setS_areaIdString2(String s_areaIdString2) {
		this.s_areaIdString2 = s_areaIdString2;
	}


	public String getE_provinceIdString2() {
		return e_provinceIdString2;
	}


	public void setE_provinceIdString2(String e_provinceIdString2) {
		this.e_provinceIdString2 = e_provinceIdString2;
	}


	public String getE_cityIdString2() {
		return e_cityIdString2;
	}


	public void setE_cityIdString2(String e_cityIdString2) {
		this.e_cityIdString2 = e_cityIdString2;
	}


	public String getE_areaIdString2() {
		return e_areaIdString2;
	}


	public void setE_areaIdString2(String e_areaIdString2) {
		this.e_areaIdString2 = e_areaIdString2;
	}


	public String getS_provinceIdString3() {
		return s_provinceIdString3;
	}


	public void setS_provinceIdString3(String s_provinceIdString3) {
		this.s_provinceIdString3 = s_provinceIdString3;
	}


	public String getS_cityIdString3() {
		return s_cityIdString3;
	}


	public void setS_cityIdString3(String s_cityIdString3) {
		this.s_cityIdString3 = s_cityIdString3;
	}


	public String getS_areaIdString3() {
		return s_areaIdString3;
	}


	public void setS_areaIdString3(String s_areaIdString3) {
		this.s_areaIdString3 = s_areaIdString3;
	}


	public String getE_provinceIdString3() {
		return e_provinceIdString3;
	}


	public void setE_provinceIdString3(String e_provinceIdString3) {
		this.e_provinceIdString3 = e_provinceIdString3;
	}


	public String getE_cityIdString3() {
		return e_cityIdString3;
	}


	public void setE_cityIdString3(String e_cityIdString3) {
		this.e_cityIdString3 = e_cityIdString3;
	}


	public String getE_areaIdString3() {
		return e_areaIdString3;
	}


	public void setE_areaIdString3(String e_areaIdString3) {
		this.e_areaIdString3 = e_areaIdString3;
	}


	public String getE_provinceIdString4() {
		return e_provinceIdString4;
	}


	public void setE_provinceIdString4(String e_provinceIdString4) {
		this.e_provinceIdString4 = e_provinceIdString4;
	}


	public String getE_cityIdString4() {
		return e_cityIdString4;
	}


	public void setE_cityIdString4(String e_cityIdString4) {
		this.e_cityIdString4 = e_cityIdString4;
	}


	public String getE_areaIdString4() {
		return e_areaIdString4;
	}


	public void setE_areaIdString4(String e_areaIdString4) {
		this.e_areaIdString4 = e_areaIdString4;
	}


	public String getE_provinceIdString5() {
		return e_provinceIdString5;
	}


	public void setE_provinceIdString5(String e_provinceIdString5) {
		this.e_provinceIdString5 = e_provinceIdString5;
	}


	public String getE_cityIdString5() {
		return e_cityIdString5;
	}


	public void setE_cityIdString5(String e_cityIdString5) {
		this.e_cityIdString5 = e_cityIdString5;
	}


	public String getE_areaIdString5() {
		return e_areaIdString5;
	}


	public void setE_areaIdString5(String e_areaIdString5) {
		this.e_areaIdString5 = e_areaIdString5;
	}


	private String s_cityIdString3;
    //2015-10-24 新增字段 发货区域Id
    private String s_areaIdString3;
    //2015-10-24 新增字段 收货货省会Id
    private String e_provinceIdString3;
    //2015-10-24 新增字段 收货城市Id
    private String e_cityIdString3;
    //2015-10-24 新增字段 收货区域Id
    private String e_areaIdString3;
	
	//end by yanghaoyu
	
    
	//2015-10-24 新增字段 发货城市Id
    private String e_provinceIdString4;
    private String e_cityIdString4;
    //2015-10-24 新增字段 发货区域Id
    private String e_areaIdString4;
    //2015-10-24 新增字段 收货货省会Id
    private String e_provinceIdString5;
    //2015-10-24 新增字段 收货城市Id
    private String e_cityIdString5;
    //2015-10-24 新增字段 收货区域Id
    private String e_areaIdString5;
	
	//end by yanghaoyu
	
	
	






	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getUnitTypeString() {
		return unitTypeString;
	}


	public void setUnitTypeString(String unitTypeString) {
		this.unitTypeString = unitTypeString;
	}





	public String getCarTypeString() {
		return carTypeString;
	}


	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
	}


	public String getSendGoodsTypeString() {
		return sendGoodsTypeString;
	}


	public void setSendGoodsTypeString(String sendGoodsTypeString) {
		this.sendGoodsTypeString = sendGoodsTypeString;
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


	public String getSendDateString() {
		return sendDateString;
	}


	public void setSendDateString(String sendDateString) {
		this.sendDateString = sendDateString;
	}

	
	public String getEndDateString() {
		return endDateString;
	}


	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
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


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getIsCertify() {
		return isCertify;
	}


	public void setIsCertify(String isCertify) {
		this.isCertify = isCertify;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getTransportTypeString() {
		return transportTypeString;
	}


	public void setTransportTypeString(String transportTypeString) {
		this.transportTypeString = transportTypeString;
	}


	public Integer getTransportCarType() {
		return transportCarType;
	}


	public void setTransportCarType(Integer transportCarType) {
		this.transportCarType = transportCarType;
	}


	public Integer getTransportType() {
		return transportType;
	}


	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}

   


}
