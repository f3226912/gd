/**
 * @Title: SubRangeLimitRule.java
 * @Package com.gudeng.commerce.gd.customer.dto
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午4:17:12
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.SubRangeLimitRuleEntity;

/**
 * @ClassName: SubRangeLimitRule
 * @Description: TODO(补贴限制区间规则实体类)
 * @author mpan
 * @date 2015年11月30日 下午4:17:12
 */
public class SubRangeLimitRuleDTO extends SubRangeLimitRuleEntity implements Serializable {
	private static final long serialVersionUID = -4527748881762932100L;
	
	public String getAmountView() {
		if (getAmount() != null) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
			return df.format(getAmount());
		} else {
			return null;
		}

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
