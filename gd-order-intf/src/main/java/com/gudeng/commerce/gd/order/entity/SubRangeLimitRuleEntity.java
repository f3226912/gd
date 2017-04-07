/**
 * @Title: SubRangeLimitRuleEntity.java
 * @Package com.gudeng.commerce.gd.customer.entity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午5:01:59
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sub_range_limit_rule")
public class SubRangeLimitRuleEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 7771724719376159934L;
	
	private Long id; // 主键

	private Long ruleId; // 规格ID

	private String limitType; // 类型 1车次限制 2补贴用户额度限制 3补贴用户次数限制 4用户间交易频率限制 5补贴总额度限制

	private Double amount; // 金额

	private Integer count; // 次数

	private Integer timeRange; // 时间维度 1每天 2每周 3每月 4整个活动

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ruleId")
	public Long getRuleId() {

		return this.ruleId;
	}

	public void setRuleId(Long ruleId) {

		this.ruleId = ruleId;
	}

	@Column(name = "limitType")
	public String getLimitType() {

		return this.limitType;
	}

	public void setLimitType(String limitType) {

		this.limitType = limitType;
	}

	@Column(name = "amount")
	public Double getAmount() {

		return this.amount;
	}

	public void setAmount(Double amount) {

		this.amount = amount;
	}

	@Column(name = "count")
	public Integer getCount() {

		return this.count;
	}

	public void setCount(Integer count) {

		this.count = count;
	}

	@Column(name = "timeRange")
	public Integer getTimeRange() {

		return this.timeRange;
	}

	public void setTimeRange(Integer timeRange) {

		this.timeRange = timeRange;
	}
}
