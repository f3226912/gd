package com.gudeng.commerce.gd.order.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "car_baseinfo")
public class CarBaseinfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7939950569801316652L;

	private Long carId;

	private String carNumber;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;
	
	private Long cwpId;

	@Id
	@Column(name = "carId")
	public Long getCarId() {

		return this.carId;
	}

	public void setCarId(Long carId) {

		this.carId = carId;
	}

	@Column(name = "carNumber")
	public String getCarNumber() {

		return this.carNumber;
	}

	public void setCarNumber(String carNumber) {

		this.carNumber = carNumber;
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

	@Column(name = "cwpid")
	public Long getCwpId() {
		return cwpId;
	}

	public void setCwpId(Long cwpId) {
		this.cwpId = cwpId;
	}
	
	
}
