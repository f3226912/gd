package com.gudeng.commerce.gd.m.dto;

/**
 * 物流模块请求参数DTO
 * @author TerryZhang
 */
public class NstRequestParamDTO {

	/**
	 * 版本号
	 */
	private String version;
	
	/**
	 * 货源id
	 */
	private Integer transferId;
	
	/**
	 * 会员id
	 */
	private Integer memberId;
	
	/**
	 * 订单id
	 */
	private Integer orderId;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getTransferId() {
		return transferId;
	}

	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
