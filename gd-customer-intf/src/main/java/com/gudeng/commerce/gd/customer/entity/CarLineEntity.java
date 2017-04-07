package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "carline")
public class CarLineEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4093955265606678147L;

	private Long id;

	private Long carId;

	private Long memberId;

	private String startCity;

	private String endCity;

	private String name;

	private String phone;

	private Date sentDate;

	private String sentDateType;

	private Integer onLineHours;

	private Date endDate;
	
	private String endDateType;

	private String sendGoodsType;
	
	private Double price;
	
	private Integer unitType;
	
	private String createUserId;


	private Date createTime;

	
	private String updateUserId;

        //始发地2
	    private Long s_provinceId2;
	    private Long s_cityId2;
	    private Long s_areaId2;          
	    
	    //始发地3
	    private Long s_provinceId3;
	    private Long s_cityId3;
	    private Long s_areaId3;          
	    
	    //目的地2
	    private Long e_provinceId2;
	    private Long e_cityId2;
	    private Long e_areaId2;
	    
	    //目的地3
	    private Long e_provinceId3;
	    private Long e_cityId3;
	    private Long e_areaId3;
	    
	    //目的地4
	    private Long e_provinceId4;
	    private Long e_cityId4;
	    private Long e_areaId4;
	    
	    //目的地5
	    private Long e_provinceId5;
	    private Long e_cityId5;
	    private Long e_areaId5;
	    
	    private String  source;
	    
	    /**
		 * 判读货源是否删除
		 */
	    private String isDeleted;

	    //备注
	    private String remark;
	   
	private Date updateTime;
    private Long s_provinceId=0L;
    private Long s_cityId=0L;
    private Long s_areaId=0L;          
    private Long e_provinceId=0L;
    private Long e_cityId=0L;
    private Long e_areaId=0L;
    
    
	@Column(name = "s_provinceId")
	public Long getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(Long s_provinceId) {
		this.s_provinceId = s_provinceId;
	}
	@Column(name = "s_cityId")
	public Long getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(Long s_cityId) {
		this.s_cityId = s_cityId;
	}
	@Column(name = "s_areaId")
	public Long getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(Long s_areaId) {
		this.s_areaId = s_areaId;
	}
	@Column(name = "e_provinceId")
	public Long getE_provinceId() {
		return e_provinceId;
	}
 
	public void setE_provinceId(Long e_provinceId) {
		this.e_provinceId = e_provinceId;
	}
	@Column(name = "e_cityId")
	public Long getE_cityId() {
		return e_cityId;
	}

	public void setE_cityId(Long e_cityId) {
		this.e_cityId = e_cityId;
	}
	@Column(name = "e_areaId")
	public Long getE_areaId() {
		return e_areaId;
	}

	public void setE_areaId(Long e_areaId) {
		this.e_areaId = e_areaId;
	}
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name = "carId")
	public Long getCarId() {

		return this.carId;
	}


	public void setCarId(Long carId) {

		this.carId = carId;
	}

	@Column(name = "memberId")
	public Long getMemberId() {

		return this.memberId;
	}

	public void setMemberId(Long memberId) {

		this.memberId = memberId;
	}

	@Column(name = "startCity")
	public String getStartCity() {

		return this.startCity;
	}

	public void setStartCity(String startCity) {

		this.startCity = startCity;
	}

	@Column(name = "endCity")
	public String getEndCity() {

		return this.endCity;
	}

	public void setEndCity(String endCity) {

		this.endCity = endCity;
	}

	@Column(name = "name")
	public String getName() {

		return this.name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Column(name = "phone")
	public String getPhone() {

		return this.phone;
	}

	public void setPhone(String phone) {

		this.phone = phone;
	}

	@Column(name = "sentDate")
	public Date getSentDate() {

		return this.sentDate;
	}

	public void setSentDate(Date sentDate) {

		this.sentDate = sentDate;
	}

	@Column(name = "sentDateType")
	public String getSentDateType() {

		return this.sentDateType;
	}

	public void setSentDateType(String sentDateType) {

		this.sentDateType = sentDateType;
	}

	@Column(name = "onLineHours")
	public Integer getOnLineHours() {
		return onLineHours;
	}

	public void setOnLineHours(Integer onLineHours) {
		this.onLineHours = onLineHours;
	}

	@Column(name = "endDate")
	public Date getEndDate() {

		return this.endDate;
	}

	@Column(name = "endDateType")
	public String getEndDateType() {
		return endDateType;
	}

	public void setEndDateType(String endDateType) {
		this.endDateType = endDateType;
	}

	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}
	
	@Column(name = "sendGoodsType")
	public String getSendGoodsType() {

		return this.sendGoodsType;
	}

	public void setSendGoodsType(String sendGoodsType) {

		this.sendGoodsType = sendGoodsType;
	}
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	@Column(name = "unitType")
	public Integer getUnitType() {
		return unitType;
	}

	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}
	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "s_provinceId2")
	public Long getS_provinceId2() {
		return s_provinceId2;
	}

	public void setS_provinceId2(Long s_provinceId2) {
		this.s_provinceId2 = s_provinceId2;
	}

	@Column(name = "s_cityId2")
	public Long getS_cityId2() {
		return s_cityId2;
	}

	public void setS_cityId2(Long s_cityId2) {
		this.s_cityId2 = s_cityId2;
	}

	@Column(name = "s_areaId2")
	public Long getS_areaId2() {
		return s_areaId2;
	}

	public void setS_areaId2(Long s_areaId2) {
		this.s_areaId2 = s_areaId2;
	}

	@Column(name = "s_provinceId3")
	public Long getS_provinceId3() {
		return s_provinceId3;
	}

	public void setS_provinceId3(Long s_provinceId3) {
		this.s_provinceId3 = s_provinceId3;
	}

	@Column(name = "s_cityId3")
	public Long getS_cityId3() {
		return s_cityId3;
	}

	public void setS_cityId3(Long s_cityId3) {
		this.s_cityId3 = s_cityId3;
	}

	@Column(name = "s_areaId3")
	public Long getS_areaId3() {
		return s_areaId3;
	}

	public void setS_areaId3(Long s_areaId3) {
		this.s_areaId3 = s_areaId3;
	}

	@Column(name = "e_provinceId2")
	public Long getE_provinceId2() {
		return e_provinceId2;
	}

	public void setE_provinceId2(Long e_provinceId2) {
		this.e_provinceId2 = e_provinceId2;
	}

	@Column(name = "e_cityId2")
	public Long getE_cityId2() {
		return e_cityId2;
	}

	public void setE_cityId2(Long e_cityId2) {
		this.e_cityId2 = e_cityId2;
	}

	
	@Column(name = "e_areaId2")
	public Long getE_areaId2() {
		return e_areaId2;
	}

	public void setE_areaId2(Long e_areaId2) {
		this.e_areaId2 = e_areaId2;
	}

	@Column(name = "e_provinceId3")
	public Long getE_provinceId3() {
		return e_provinceId3;
	}

	public void setE_provinceId3(Long e_provinceId3) {
		this.e_provinceId3 = e_provinceId3;
	}
	
	@Column(name = "e_cityId3")
	public Long getE_cityId3() {
		return e_cityId3;
	}

	public void setE_cityId3(Long e_cityId3) {
		this.e_cityId3 = e_cityId3;
	}

	@Column(name = "e_areaId3")
	public Long getE_areaId3() {
		return e_areaId3;
	}

	public void setE_areaId3(Long e_areaId3) {
		this.e_areaId3 = e_areaId3;
	}

	@Column(name = "e_provinceId4")
	public Long getE_provinceId4() {
		return e_provinceId4;
	}

	public void setE_provinceId4(Long e_provinceId4) {
		this.e_provinceId4 = e_provinceId4;
	}

	@Column(name = "e_cityId4")
	public Long getE_cityId4() {
		return e_cityId4;
	}

	public void setE_cityId4(Long e_cityId4) {
		this.e_cityId4 = e_cityId4;
	}

	@Column(name = "e_areaId4")
	public Long getE_areaId4() {
		return e_areaId4;
	}

	public void setE_areaId4(Long e_areaId4) {
		this.e_areaId4 = e_areaId4;
	}

	@Column(name = "e_provinceId5")
	public Long getE_provinceId5() {
		return e_provinceId5;
	}

	public void setE_provinceId5(Long e_provinceId5) {
		this.e_provinceId5 = e_provinceId5;
	}

	@Column(name = "e_cityId5")
	public Long getE_cityId5() {
		return e_cityId5;
	}

	public void setE_cityId5(Long e_cityId5) {
		this.e_cityId5 = e_cityId5;
	}

	@Column(name = "e_areaId5")
	public Long getE_areaId5() {
		return e_areaId5;
	}

	public void setE_areaId5(Long e_areaId5) {
		this.e_areaId5 = e_areaId5;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "isDeleted")
	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
