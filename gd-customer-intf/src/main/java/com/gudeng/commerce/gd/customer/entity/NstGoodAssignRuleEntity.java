package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
/**
 * 农速通货源分配规则
 * @author xiaojun
 */
public class NstGoodAssignRuleEntity implements Serializable {
	private static final long serialVersionUID = 4302264351515922869L;
	/**
	 * 货源分配规则表主键id
	 */
	private Long id;
	/**
	 * 省份Id
	 */
	private Long provinceId;
	/**
	 * 农速通常用城市id
	 */
	private String cityId;
	/**
	 * 信息部（公司）对应memberId
	 */
	private Long memberId;
	/**
	 * 信息部名称（公司）
	 */
	private String companyName;
	/**
	 * 当日分配上限(条)
	 */
	private Integer dayAssignMax;
	/**
	 * monthAssignMax
	 */
	private Integer monthAssignMax;
	/**
	 * 分配有效开始时间
	 */
	private String assignStartTime;
	/**
	 * 分配有效结束日期
	 */
	private String assignEndTime;
	/**
	 * 已分配数量
	 */
	private String assignedNum;
	/**
	 * 是否生效（0，生效，1，失效）
	 */
	private Integer isEffective;
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 创建人员id
	 */
	private String createUserId;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 修改人员id
	 */
	private String updateUserId;

	@Id
	@Column(name="id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="provinceId")
	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name="memberId")
	public Long getMemberId() {
		return memberId;
	}
	@Column(name="cityId")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	@Column(name="companyName")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(name="dayAssignMax")
	public Integer getDayAssignMax() {
		return dayAssignMax;
	}

	public void setDayAssignMax(Integer dayAssignMax) {
		this.dayAssignMax = dayAssignMax;
	}
	@Column(name="monthAssignMax")
	public Integer getMonthAssignMax() {
		return monthAssignMax;
	}

	public void setMonthAssignMax(Integer monthAssignMax) {
		this.monthAssignMax = monthAssignMax;
	}
	@Column(name="assignStartTime")
	public String getAssignStartTime() {
		return assignStartTime;
	}

	public void setAssignStartTime(String assignStartTime) {
		this.assignStartTime = assignStartTime;
	}
	@Column(name="assignEndTime")
	public String getAssignEndTime() {
		return assignEndTime;
	}

	public void setAssignEndTime(String assignEndTime) {
		this.assignEndTime = assignEndTime;
	}
	@Column(name="assignedNum")
	public String getAssignedNum() {
		return assignedNum;
	}

	public void setAssignedNum(String assignedNum) {
		this.assignedNum = assignedNum;
	}
	@Column(name="isEffective")
	public Integer getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(Integer isEffective) {
		this.isEffective = isEffective;
	}
	@Column(name="createTime")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name="createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name="updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name="updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
}
