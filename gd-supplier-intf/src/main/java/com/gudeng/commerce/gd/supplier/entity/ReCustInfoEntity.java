package com.gudeng.commerce.gd.supplier.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "re_cust_info")
public class ReCustInfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7473395302036429238L;

	private Long id;

	private Long reCustId;

	private String mobile;

	private String address;

	private String type;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "id")
	public Long getId() {

		return this.id;
	}

	public void setId(Long id) {

		this.id = id;
	}

	@Column(name = "reCustId")
	public Long getReCustId() {

		return this.reCustId;
	}

	public void setReCustId(Long reCustId) {

		this.reCustId = reCustId;
	}

	@Column(name = "mobile")
	public String getMobile() {

		return this.mobile;
	}

	public void setMobile(String mobile) {

		this.mobile = mobile;
	}

	@Column(name = "address")
	public String getAddress() {

		return this.address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	@Column(name = "type")
	public String getType() {

		return this.type;
	}

	public void setType(String type) {

		this.type = type;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}
}
