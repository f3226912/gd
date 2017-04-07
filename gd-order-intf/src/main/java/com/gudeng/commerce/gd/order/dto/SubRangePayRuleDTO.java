package com.gudeng.commerce.gd.order.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.SubRangePayRuleEntity;

/**
 * @Description: TODO(补贴发放区间规则数据传输类)
 * @author mpan
 * @date 2015年12月7日 上午10:38:12
 */
public class SubRangePayRuleDTO extends SubRangePayRuleEntity {
	

	private static final long serialVersionUID = -8209654234754509889L;
	
	private String carCate;//车类型名称
	
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

	public String getCarCate() {
		return carCate;
	}

	public void setCarCate(String carCate) {
		this.carCate = carCate;
	}
	
	
}
