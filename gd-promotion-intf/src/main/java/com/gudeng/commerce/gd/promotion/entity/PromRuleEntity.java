package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "prom_rule")
public class PromRuleEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2506006114157785315L;

	private Integer ruleId;

	private Integer actId;

	private Integer maxProdNum;

	private Double prepayAmt;

	private Date createTime;

	private String createUserId;

	private Date updateTime;

	private String updateUserId;

	@Id
	@Column(name = "ruleId")
	public Integer getRuleId() {

		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {

		this.ruleId = ruleId;
	}

	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "maxProdNum")
	public Integer getMaxProdNum() {

		return this.maxProdNum;
	}

	public void setMaxProdNum(Integer maxProdNum) {

		this.maxProdNum = maxProdNum;
	}

	@Column(name = "prepayAmt")
	public Double getPrepayAmt() {

		return this.prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {

		this.prepayAmt = prepayAmt;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}
}
