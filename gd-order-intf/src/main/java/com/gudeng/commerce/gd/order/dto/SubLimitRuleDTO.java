/**
 * @Title: SubLimitRule.java
 * @Package com.gudeng.commerce.gd.customer.dto
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午4:16:23
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.SubLimitRuleEntity;

/**
 * @ClassName: SubLimitRule
 * @Description: TODO(补贴限制规则实体类)
 * @author mpan
 * @date 2015年11月30日 下午4:16:23
 */
public class SubLimitRuleDTO extends SubLimitRuleEntity implements Serializable {

	private static final long serialVersionUID = 8514075234803753587L;
	
	private String timeStartStr; // 时间范围起
	
	private String timeEndStr; // 时间范围止

	private List<SubRangeLimitRuleDTO> subRangeLimitRules; // 补贴限制区间规则集合
	
	private String whiteList;
	
	private List<String> contacts;
	
	private List<SubLimitRuleWhitelistDTO> wlist;
	
	/**********20160219添加*******/
	private String marketStr;
	
	private String userName;
	
	private Double subAmountBal;
	
	
	
	public String getMarketStr() {
		return marketStr;
	}

	public void setMarketStr(String marketStr) {
		this.marketStr = marketStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getSubAmountBal() {
		return subAmountBal;
	}

	public void setSubAmountBal(Double subAmountBal) {
		this.subAmountBal = subAmountBal;
	}
	/**************************/
	

	public List<SubLimitRuleWhitelistDTO> getWlist() {
		return wlist;
	}

	public void setWlist(List<SubLimitRuleWhitelistDTO> wlist) {
		this.wlist = wlist;
	}

	public String getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}

	public List<String> getContacts() {
		return contacts;
	}

	public void setContacts(List<String> contacts) {
		this.contacts = contacts;
	}

	public String getSubAmountView() {
		if (getSubAmount() != null) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
			return df.format(getSubAmount());
		} else {
			return null;
		}

	}

	public String getTimeStartStr() {
		return timeStartStr;
	}

	public void setTimeStartStr(String timeStartStr) {
		this.timeStartStr = timeStartStr;
	}

	public String getTimeEndStr() {
		return timeEndStr;
	}

	public void setTimeEndStr(String timeEndStr) {
		this.timeEndStr = timeEndStr;
	}

	public List<SubRangeLimitRuleDTO> getSubRangeLimitRules() {
		return subRangeLimitRules;
	}

	public void setSubRangeLimitRules(List<SubRangeLimitRuleDTO> subRangeLimitRules) {
		this.subRangeLimitRules = subRangeLimitRules;
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

}
