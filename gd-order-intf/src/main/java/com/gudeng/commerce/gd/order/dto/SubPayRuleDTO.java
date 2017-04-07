package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.SubPayRuleEntity;

public class SubPayRuleDTO extends SubPayRuleEntity implements Serializable {

	private static final long serialVersionUID = 5324176715579413418L;

	private List<SubRangePayRuleDTO> ranges;

	private String userName;

	private Long productId; // 产品ID
	
	private Double curDaySubAmount = 0.00;//该条规则的当天补贴的金额
	
	private Double sumSubAmount = 0.00;//该条规则的总补贴金额
	
	private Double mkSubAmount = 0.00;//该市场的的总补贴金额
	
	private String category ="";
	
	private String marketStr="";
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<SubRangePayRuleDTO> getRanges() {
		return ranges;
	}

	public void setRanges(List<SubRangePayRuleDTO> ranges) {
		this.ranges = ranges;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getCurDaySubAmount() {
		return curDaySubAmount;
	}

	public void setCurDaySubAmount(Double curDaySubAmount) {
		this.curDaySubAmount = curDaySubAmount;
	}

	public Double getSumSubAmount() {
		return sumSubAmount;
	}

	public void setSumSubAmount(Double sumSubAmount) {
		this.sumSubAmount = sumSubAmount;
	}

	public Double getMkSubAmount() {
		return mkSubAmount;
	}

	public void setMkSubAmount(Double mkSubAmount) {
		this.mkSubAmount = mkSubAmount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String getMarketStr() {
		return marketStr;
	}

	public void setMarketStr(String marketStr) {
		this.marketStr = marketStr;
	}
	
	
	

}
