package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

public class PushNstMessageInfoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//推送消息
	private Long messageId;
	private Long mb_Id;
	private Long cl_Id;
	private Long memberId;
	private String isCertify;
	private String carTypeString;
	private String hundredweightString;
	private String mcity;
	private double distance;
	
	/**
	 * 判读货源是否删除
	 */
	private String isDeleted;
	public String getMcity() {
		return mcity;
	}
	public void setMcity(String mcity) {
		this.mcity = mcity;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getHundredweightString() {
		return hundredweightString;
	}
	public void setHundredweightString(String hundredweightString) {
		this.hundredweightString = hundredweightString;
	}
	public String getCarTypeString() {
		return carTypeString;
	}
	public void setCarTypeString(String carTypeString) {
		this.carTypeString = carTypeString;
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
	public Long getMessageId() {
		
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getMb_Id() {
		return mb_Id;
	}
	public void setMb_Id(Long mb_Id) {
		this.mb_Id = mb_Id;
	}
	public Long getCl_Id() {
		return cl_Id;
	}
	public void setCl_Id(Long cl_Id) {
		this.cl_Id = cl_Id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Integer getCarType() {
		return carType;
	}
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	public Double getCarLength() {
		return carLength;
	}
	public void setCarLength(Double carLength) {
		this.carLength = carLength;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	private String goodsName;
	private String totalWeight;
	private Integer carType;
	private Double carLength;
	private Long s_provinceId;
	private Long s_cityId;
	private Long s_areaId;          
	private Long f_provinceId;
	private Long f_cityId;
	private Long f_areaId;
	
	
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

	private String createTime;
	
	
	private String e_provinceIdString;
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
	private String e_cityIdString;
	private String e_areaIdString;
	
	private String s_provinceIdString2;
	private String s_cityIdString2;
	private String s_areaIdString2;
	private String e_provinceIdString2;
	private String e_cityIdString2;
	private String e_areaIdString2;
	
	
	private String s_provinceIdString3;
	private String s_cityIdString3;
	private String s_areaIdString3;
	private String e_provinceIdString3;
	private String e_cityIdString3;
	private String e_areaIdString3;
	
	private String e_provinceIdString4;
	private String e_cityIdString4;
	private String e_areaIdString4;
	private String e_provinceIdString5;
	private String e_cityIdString5;
	private String e_areaIdString5;


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
	
	
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	private String  mobile;
	private String contact;
	
	
	private String s_provinceIdString;
	private String s_cityIdString;
	private String s_areaIdString;          
	private String f_provinceIdString;
	private String f_cityIdString;
	private String f_areaIdString;
	
	
}
