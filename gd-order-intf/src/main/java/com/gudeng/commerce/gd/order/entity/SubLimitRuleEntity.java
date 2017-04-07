/**
 * @Title: SubLimitRuleEntity.java
 * @Package com.gudeng.commerce.gd.customer.entity
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午4:55:14
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sub_limit_rule")
public class SubLimitRuleEntity implements java.io.Serializable {

	private static final long serialVersionUID = 528683425324826582L;

	private Long ruleId; // 规格ID
	
	private Long marketId; // 市场ID
	
	private Date timeStart; // 时间范围起
	
	private Date timeEnd; // 时间范围止
	
	private Double subAmount; // 补贴总额

	private String vehLimit; // 车辆限制开关 1开 0关

	private String uamountLimit; // 补贴用户额度限制开关 1开 0关

	private String utimesLimit; // 补贴用户次数限制开关 1开 0关
	
	private String twoUtimesLimit; // 用户间交易频率限制 1开 0关

	private String tamountLimit; // 补贴总额数限制开关 1开 0关

	private Date createTime; // 创建时间

	private String createUserId; // 创建人员id

	private Date updateTime; // 更新时间

	private String updateUserId; // 修改人员id
	
	private String whiteLimit;//白名单开关
	
	private String status;//规则状态
	
	private String contact;//紧急联系人

	


	@Column(name = "ruleId")
	public Long getRuleId() {

		return this.ruleId;
	}

	public void setRuleId(Long ruleId) {

		this.ruleId = ruleId;
	}

	@Column(name = "marketId")
	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	@Column(name = "timeStart")
	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	@Column(name = "timeEnd")
	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Column(name = "subAmount")
	public Double getSubAmount() {
		return subAmount;
	}

	public void setSubAmount(Double subAmount) {
		this.subAmount = subAmount;
	}

	@Column(name = "vehLimit")
	public String getVehLimit() {

		return this.vehLimit;
	}

	public void setVehLimit(String vehLimit) {

		this.vehLimit = vehLimit;
	}

	@Column(name = "uamountLimit")
	public String getUamountLimit() {

		return this.uamountLimit;
	}

	public void setUamountLimit(String uamountLimit) {

		this.uamountLimit = uamountLimit;
	}

	@Column(name = "utimesLimit")
	public String getUtimesLimit() {

		return this.utimesLimit;
	}

	public void setUtimesLimit(String utimesLimit) {

		this.utimesLimit = utimesLimit;
	}

	@Column(name = "twoUtimesLimit")
	public String getTwoUtimesLimit() {
		return twoUtimesLimit;
	}

	public void setTwoUtimesLimit(String twoUtimesLimit) {
		this.twoUtimesLimit = twoUtimesLimit;
	}

	@Column(name = "tamountLimit")
	public String getTamountLimit() {

		return this.tamountLimit;
	}

	public void setTamountLimit(String tamountLimit) {

		this.tamountLimit = tamountLimit;
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

	@Column(name = "whiteLimit")
	public String getWhiteLimit() {
		return whiteLimit;
	}

	public void setWhiteLimit(String whiteLimit) {
		this.whiteLimit = whiteLimit;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "contact")
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
	

}
