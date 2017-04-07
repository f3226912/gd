package com.gudeng.commerce.gd.order.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Description: TODO(会员补贴结果)
 * @author mpan
 * @date 2015年12月20日 下午9:16:27
 */
public class MemberSubResultDTO {

	private String subStatus; // 补贴状态

	private String auditDesc; // 补贴审核日志

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
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
