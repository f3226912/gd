package com.gudeng.commerce.gd.order.dto;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.entity.OrderOutmarketinfoEntity;

public class OrderOutmarketInfoDTO extends OrderOutmarketinfoEntity {

	private static final long serialVersionUID = -6829541820698172540L;
	
	private String account;
	
	private String mobile;

	private Long orderNo; // 订单号

	private Long cwpid; // 车辆类型ID

	private String weighType; // 未过磅默认载重情况 1:0%, 2:30% 3:50% 4:100%
	
	private List<Long> orderNoList;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getCwpid() {
		return cwpid;
	}

	public void setCwpid(Long cwpid) {
		this.cwpid = cwpid;
	}

	public String getWeighType() {
		return weighType;
	}

	public void setWeighType(String weighType) {
		this.weighType = weighType;
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

	public List<Long> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<Long> orderNoList) {
		this.orderNoList = orderNoList;
	}

}
