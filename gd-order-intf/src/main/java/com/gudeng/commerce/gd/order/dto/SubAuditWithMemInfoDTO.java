package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.order.entity.SubAuditEntity;

public class SubAuditWithMemInfoDTO extends SubAuditEntity implements Serializable{
	private static final long serialVersionUID = 7077410896450253164L;
	
	private Double buyerSubAmount;	// 买家补贴额
	private Double sellSubAmount;	// 卖家补贴额
	private Double suppSubAmount;	// 供应商补贴额
	
	private Integer buyerMemberId;	// 买家Id
	private Integer sellMemberId;	// 卖家Id
	private Integer suppMemberId;	// 供应商Id
	
	private Integer buyerAccId;		// 买家账户Id
	private Integer sellAccId;		// 卖家账户Id
	private Integer suppAccId;		// 供应商账户Id
	
	private Integer marketId;			// 市场Id
	
	private Long subRuleId;		// 补贴限制规则ID
	
	
	public Double getBuyerSubAmount() {
		return buyerSubAmount;
	}
	public void setBuyerSubAmount(Double buyerSubAmount) {
		this.buyerSubAmount = buyerSubAmount;
	}
	public Double getSellSubAmount() {
		return sellSubAmount;
	}
	public void setSellSubAmount(Double sellSubAmount) {
		this.sellSubAmount = sellSubAmount;
	}
	public Double getSuppSubAmount() {
		return suppSubAmount;
	}
	public void setSuppSubAmount(Double suppSubAmount) {
		this.suppSubAmount = suppSubAmount;
	}
	
	public Integer getBuyerMemberId() {
		return buyerMemberId;
	}
	public void setBuyerMemberId(Integer buyerMemberId) {
		this.buyerMemberId = buyerMemberId;
	}
	public Integer getSellMemberId() {
		return sellMemberId;
	}
	public void setSellMemberId(Integer sellMemberId) {
		this.sellMemberId = sellMemberId;
	}
	public Integer getSuppMemberId() {
		return suppMemberId;
	}
	public void setSuppMemberId(Integer suppMemberId) {
		this.suppMemberId = suppMemberId;
	}
	public Integer getBuyerAccId() {
		return buyerAccId;
	}
	public void setBuyerAccId(Integer buyerAccId) {
		this.buyerAccId = buyerAccId;
	}
	public Integer getSellAccId() {
		return sellAccId;
	}
	public void setSellAccId(Integer sellAccId) {
		this.sellAccId = sellAccId;
	}
	public Integer getSuppAccId() {
		return suppAccId;
	}
	public void setSuppAccId(Integer suppAccId) {
		this.suppAccId = suppAccId;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public Long getSubRuleId() {
		return subRuleId;
	}
	public void setSubRuleId(Long subRuleId) {
		this.subRuleId = subRuleId;
	}
	
	
	
	

}
