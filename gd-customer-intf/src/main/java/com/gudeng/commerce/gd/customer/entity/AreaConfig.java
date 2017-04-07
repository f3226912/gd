package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 
 * 后台配置区域城市不选择企业发布货源信息
 */
@Entity(name = "area_config")
public class AreaConfig implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1945852023955341405L;

	/**
	 * 
	 */

	@Id
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "areaID")
	private String areaID;

	@Column(name = "areaName")
	private String areaName;

	//状态（0：启用；1：停用）
	@Column(name = "status")
    private String status;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaID() {
		return areaID;
	}

	public void setAreaID(String areaID) {
		this.areaID = areaID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
