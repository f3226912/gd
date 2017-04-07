package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.gudeng.commerce.gd.order.enm.EPayStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;

public class PaySerialnumberDTO extends PaySerialnumberEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2631038145910069271L;

	private Double discountAmount; // 余额抵扣
	private Double orderAmount; // 合计
	private Double payAmount; // 实付款
	private Integer businessId;
	private String payTimeView; // 支付时间视图
	private String payStatusView; // 支付状态
	private String payTypeView; // 支付类型

	public enum PAY_TYPE {
		QBYE("1"), XXSK("2"), QBYE_AND_XXSK("3");

		PAY_TYPE(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	public enum PAY_STATUS {
		NOT_PAID("0"), PAY_SUCCESS("1"), PAY_FAIL("2");

		PAY_STATUS(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	private Integer count;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getPayTimeView() {
		return payTimeView;
	}

	public void setPayTimeView(String payTimeView) {
		this.payTimeView = payTimeView;
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

	public String getPayStatusView() {
		EPayStatus status = EPayStatus.getEnumByCode(this.getPayStatus());
		switch (status) {
		case WAIT_PAY:
			this.setPayStatusView(EPayStatus.WAIT_PAY.getDesc());
			break;
		case PAID:
			this.setPayStatusView(EPayStatus.PAID.getDesc());
			break;
		case PAY_FAIL:
			this.setPayStatusView(EPayStatus.PAY_FAIL.getDesc());
			break;
		}
		return payStatusView;
	}

	public void setPayStatusView(String payStatusView) {
		this.payStatusView = payStatusView;
	}

	public String getPayTypeView() {
		EPayType payType = EPayType.getEnumByCode(this.getPayType());
		this.setPayTypeView(payType.getDesc());
		return payTypeView;
	}

	public void setPayTypeView(String payTypeView) {
		this.payTypeView = payTypeView;
	}

}
