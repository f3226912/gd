package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "member_address")
public class MemberAddressEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8785292455756478530L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "userId")
	private Long userId;

	@Column(name = "type")
	private int type;

	@Column(name = "personalLine")
	private int personalLine;

	@Column(name = "s_provinceId")
	private Long s_provinceId;

	@Column(name = "s_cityId")
	private Long s_cityId;

	@Column(name = "s_areaId")
	private Long s_areaId;

	@Column(name = "s_detail")
	private String s_detail;

	@Column(name = "s_receiver")
	private String s_receiver;

	@Column(name = "s_zipCode")
	private String s_zipCode;

	@Column(name = "s_mobile")
	private String s_mobile;

	@Column(name = "s_telPhone")
	private String s_telPhone;

	@Column(name = "f_provinceId")
	private Long f_provinceId;

	@Column(name = "f_cityId")
	private Long f_cityId;

	@Column(name = "f_areaId")
	private Long f_areaId;

	@Column(name = "f_detail")
	private String f_detail;

	@Column(name = "f_receiver")
	private String f_receiver;

	@Column(name = "f_zipCode")
	private String f_zipCode;

	@Column(name = "f_mobile")
	private String f_mobile;

	@Column(name = "f_telPhone")
	private String f_telPhone;

	@Column(name = "isDefault")
	private String isDefault;

	@Column(name = "isDeleted")
	private String isDeleted;

	@Column(name = "createUserId")
	private String createUserId;

	@Column(name = "createTime")
	private Date createTime;

	@Column(name = "updateUserId")
	private String updateUserId;

	@Column(name = "updateTime")
	private Date updateTime;

	@Column(name = "sendDate")
	private Date sendDate;

	@Column(name = "sendDateType")
	private Integer sendDateType;

	@Column(name = "goodsType")
	private Integer goodsType;

	@Column(name = "totalWeight")
	private Double totalWeight;

	@Column(name = "totalSize")
	private Integer totalSize;

	@Column(name = "carType")
	private Integer carType;

	@Column(name = "sendGoodsType")
	private Integer sendGoodsType;

	@Column(name = "price")
	private Double price;

	@Column(name = "priceUnitType")
	private Integer priceUnitType;

	@Column(name = "goodsName")
	private String goodsName;

	@Column(name = "userMobile")
	private String userMobile;

	@Column(name = "hundredweight")
	private Integer hundredweight;

	@Column(name = "endDate")
	private Date endDate;

	@Column(name = "endDateType")
	private Integer endDateType;

	@Column(name = "userType")
	private Integer userType;
	
	 //备注
	@Column(name = "remark")
    private String remark;
	
	private Double carLength ;
	
	/**
	 * 非农速通一手货源联系人
	 */
	private String contactName;
	/**
	 * 发货地详细地址(发)
	 */
	private String s_detailed_address;
	/**
	 * 收货地详细地址(收)
	 */
	private String f_detailed_address;
	@Column(name = "s_detailed_address")
	public String getS_detailed_address() {
		return s_detailed_address;
	}
	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}
	@Column(name = "f_detailed_address")
	public String getF_detailed_address() {
		return f_detailed_address;
	}
	public void setF_detailed_address(String f_detailed_address) {
		this.f_detailed_address = f_detailed_address;
	}
	@Column(name = "contactName")
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Column(name = "carLength")
	public Double getCarLength() {
		return carLength;
	}
	
	public void setCarLength(Double carLength) {
		this.carLength = carLength;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEndDateType() {
		return endDateType;
	}

	public void setEndDateType(Integer endDateType) {
		this.endDateType = endDateType;
	}

	public Integer getHundredweight() {
		return hundredweight;
	}

	public void setHundredweight(Integer hundredweight) {
		this.hundredweight = hundredweight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {

		return this.userId;
	}

	public void setUserId(Long userId) {

		this.userId = userId;
	}

	public int getType() {

		return this.type;
	}

	public void setType(int type) {

		this.type = type;
	}

	public int getPersonalLine() {

		return this.personalLine;
	}

	public void setPersonalLine(int personalLine) {

		this.personalLine = personalLine;
	}

	public Long getS_provinceId() {

		return this.s_provinceId;
	}

	public void setS_provinceId(Long s_provinceId) {

		this.s_provinceId = s_provinceId;
	}

	public Long getS_cityId() {

		return this.s_cityId;
	}

	public void setS_cityId(Long s_cityId) {

		this.s_cityId = s_cityId;
	}

	public Long getS_areaId() {

		return this.s_areaId;
	}

	public void setS_areaId(Long s_areaId) {

		this.s_areaId = s_areaId;
	}

	public String getS_detail() {

		return this.s_detail;
	}

	public void setS_detail(String s_detail) {

		this.s_detail = s_detail;
	}

	public String getS_receiver() {

		return this.s_receiver;
	}

	public void setS_receiver(String s_receiver) {

		this.s_receiver = s_receiver;
	}

	public String getS_zipCode() {

		return this.s_zipCode;
	}

	public void setS_zipCode(String s_zipCode) {

		this.s_zipCode = s_zipCode;
	}

	public String getS_mobile() {

		return this.s_mobile;
	}

	public void setS_mobile(String s_mobile) {

		this.s_mobile = s_mobile;
	}

	public String getS_telPhone() {

		return this.s_telPhone;
	}

	public void setS_telPhone(String s_telPhone) {

		this.s_telPhone = s_telPhone;
	}

	public Long getF_provinceId() {

		return this.f_provinceId;
	}

	public void setF_provinceId(Long f_provinceId) {

		this.f_provinceId = f_provinceId;
	}

	public Long getF_cityId() {

		return this.f_cityId;
	}

	public void setF_cityId(Long f_cityId) {

		this.f_cityId = f_cityId;
	}

	public Long getF_areaId() {

		return this.f_areaId;
	}

	public void setF_areaId(Long f_areaId) {

		this.f_areaId = f_areaId;
	}

	public String getF_detail() {

		return this.f_detail;
	}

	public void setF_detail(String f_detail) {

		this.f_detail = f_detail;
	}

	public String getF_receiver() {

		return this.f_receiver;
	}

	public void setF_receiver(String f_receiver) {

		this.f_receiver = f_receiver;
	}

	public String getF_zipCode() {

		return this.f_zipCode;
	}

	public void setF_zipCode(String f_zipCode) {

		this.f_zipCode = f_zipCode;
	}

	public String getF_mobile() {

		return this.f_mobile;
	}

	public void setF_mobile(String f_mobile) {

		this.f_mobile = f_mobile;
	}

	public String getF_telPhone() {

		return this.f_telPhone;
	}

	public void setF_telPhone(String f_telPhone) {

		this.f_telPhone = f_telPhone;
	}

	public String getIsDefault() {

		return this.isDefault;
	}

	public void setIsDefault(String isDefault) {

		this.isDefault = isDefault;
	}

	public String getIsDeleted() {

		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {

		this.isDeleted = isDeleted;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getSendDateType() {
		return sendDateType;
	}

	public void setSendDateType(Integer sendDateType) {
		this.sendDateType = sendDateType;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public Integer getSendGoodsType() {
		return sendGoodsType;
	}

	public void setSendGoodsType(Integer sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getPriceUnitType() {
		return priceUnitType;
	}

	public void setPriceUnitType(Integer priceUnitType) {
		this.priceUnitType = priceUnitType;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
