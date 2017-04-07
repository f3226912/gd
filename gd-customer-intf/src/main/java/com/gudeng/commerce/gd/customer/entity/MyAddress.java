package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * MyAddress entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "my_address")
public class MyAddress implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4444020463626934113L;
	private Integer id;
	private Integer memberId;
	private String district1;
	private String district2;
	private String district3;
	private String detail;
	private Date createTime;
	private Long createUser;
	private Date updateTime;
	private Long updateUser;
	private String status;
	private String linkman;
	private String gender;
	private String mobile;
	private String prefer;

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "prefer")
	public String getPrefer() {
		return prefer;
	}

	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}

	@Column(name = "linkman")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "memberId")
	public Integer getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
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