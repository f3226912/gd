package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "delivery_address")
public class OrderDeliveryAddress implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5126595116708985457L;
	// Fields

	private Integer id;
	private Long orderNo;
	private String linkman;
	private String gender;
	private String mobile;
	private String district1;
	private String district2;
	private String district3;
	private String detail;
	private Date createTime;
	private Long createUser;
	private Date updateTime;
	private Long updateUser;
	private String status;
	
	private String nstOrderNo; //运单号
	
	private String distributeMode;//配送方式
	
	private Integer companyId;//物流公司Id
	
	private Integer driverId; //司机ID
	
	private Double freight;//运费
	
	private Date arriveTime;//送达时间

	
	
	public String getNstOrderNo() {
		return nstOrderNo;
	}

	public void setNstOrderNo(String nstOrderNo) {
		this.nstOrderNo = nstOrderNo;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "orderNo")
	public Long getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "linkman")
	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "gender")
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "district1")
	public String getDistrict1() {
		return this.district1;
	}

	public void setDistrict1(String district1) {
		this.district1 = district1;
	}

	@Column(name = "district2")
	public String getDistrict2() {
		return this.district2;
	}

	public void setDistrict2(String district2) {
		this.district2 = district2;
	}

	@Column(name = "district3")
	public String getDistrict3() {
		return this.district3;
	}

	public void setDistrict3(String district3) {
		this.district3 = district3;
	}

	@Column(name = "detail")
	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "createUser")
	public Long getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "updateUser")
	public Long getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "status")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}