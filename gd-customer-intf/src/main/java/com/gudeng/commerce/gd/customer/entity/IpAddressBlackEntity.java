package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ipaddress_black")
public class IpAddressBlackEntity implements Serializable {

	private static final long serialVersionUID = 8467489813342802L;

	private Integer id;
	
	private String type;

	private String ipAddress;

	private String chennel;

	private String state;

	private Date createTime;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ipAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "chennel")
	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}

	@Column(name = "type")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
