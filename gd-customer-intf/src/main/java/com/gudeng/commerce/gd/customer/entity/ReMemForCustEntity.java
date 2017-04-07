package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "re_mem_for_cust")
public class ReMemForCustEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8733115650452563419L;

	private Long id;
	
	private String name;
	
	private Long busiMemberId;

	private Long custMemberId;

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

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "busiMemberId")
	public Long getBusiMemberId() {

		return this.busiMemberId;
	}

	public void setBusiMemberId(Long busiMemberId) {

		this.busiMemberId = busiMemberId;
	}

	@Column(name = "custMemberId")
	public Long getCustMemberId() {

		return this.custMemberId;
	}

	public void setCustMemberId(Long custMemberId) {

		this.custMemberId = custMemberId;
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
