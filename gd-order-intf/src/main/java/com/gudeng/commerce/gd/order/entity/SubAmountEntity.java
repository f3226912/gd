package com.gudeng.commerce.gd.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

/**
 * 
 * @ToDo: 该实体类用来描述补贴金额表(sub_amount)
 * @author lmzhang@gdeng.cn
 *
 */
public class SubAmountEntity implements Serializable{
	private static final long serialVersionUID = 4736612564250282929L;

	private Integer subAmountId;	//	id
	private Double subAmountBal;	//	剩余补贴额(每次补贴完成后会减去该值)
	private Double subAmountTotal; 	// 	针对该市场总的补贴额
	private Integer marketId;		//	市场ID
	private String marketName;		//	市场名
	private String isAvailable;		//	1:正在使用(可用)； 0:不可用
	private String hasSubBalance;   // 是否有补贴额 1有 0无
	private Date createTime;		//	
	private String createUserId;	//	
	private Date updateTime;		//	
	private String updateUserId;	//
	private Long subRuleId;			// 补贴限制规则ID
	@Column(name = "subAmountId")
	public Integer getSubAmountId() {
		return subAmountId;
	}
	public void setSubAmountId(Integer subAmountId) {
		this.subAmountId = subAmountId;
	}
	@Column(name = "subAmountBal")
	public Double getSubAmountBal() {
		return subAmountBal;
	}
	public void setSubAmountBal(Double subAmountBal) {
		this.subAmountBal = subAmountBal;
	}
	@Column(name = "subAmountTotal")
	public Double getSubAmountTotal() {
		return subAmountTotal;
	}
	public void setSubAmountTotal(Double subAmountTotal) {
		this.subAmountTotal = subAmountTotal;
	}
	@Column(name = "marketId")
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	@Column(name = "marketName")
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	@Column(name = "isAvailable")
	public String getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	@Column(name = "hasSubBalance")
	public String getHasSubBalance() {
		return hasSubBalance;
	}
	public void setHasSubBalance(String hasSubBalance) {
		this.hasSubBalance = hasSubBalance;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	@Column(name = "subRuleId")
	public Long getSubRuleId() {
		return subRuleId;
	}
	public void setSubRuleId(Long subRuleId) {
		this.subRuleId = subRuleId;
	}
	
}
