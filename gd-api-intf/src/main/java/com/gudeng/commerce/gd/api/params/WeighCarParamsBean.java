package com.gudeng.commerce.gd.api.params;

/**
 * 过磅请求参数
 * @author wind
 *
 */
public class WeighCarParamsBean {
	
	/**
	 * 过磅记录id
	 */
	private Long weighCarId;
	
	private Long memberId;
	
	/**
	 * 皮重过磅员id
	 */
	private Long tareMemberId;
	
	/**
	 * 总重过磅员id
	 */
	private Long totalMemberId;
	
	/**
	 * 类型（1、货主商，2、采购商）
	 */
	private String type;
	
	private String mobile;
	
	private String realName;
	
	private String carNumber;
	
	/**
	 * 皮重
	 */
	private Double tareWeight;
	
	/**
	 * 总重
	 */
	private Double totalWeight;
	
	/**
	 * 过磅状态 0:未进场,1:已进场,2:已出场
	 */
	private String status;
	
	/**
	 * 未过磅默认载重情况 1:0%, 2:30% 3:50% 4:100%
	 */
	private String weighType;
	
	/**
	 * 车辆类型ID
	 */
	private Long cwpId;
	
	/**
	 * 市场id
	 */
	private Long marketId;

	public Long getWeighCarId() {
		return weighCarId;
	}

	public void setWeighCarId(Long weighCarId) {
		this.weighCarId = weighCarId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getTareMemberId() {
		return tareMemberId;
	}

	public void setTareMemberId(Long tareMemberId) {
		this.tareMemberId = tareMemberId;
	}

	public Long getTotalMemberId() {
		return totalMemberId;
	}

	public void setTotalMemberId(Long totalMemberId) {
		this.totalMemberId = totalMemberId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWeighType() {
		return weighType;
	}

	public void setWeighType(String weighType) {
		this.weighType = weighType;
	}

	public Long getCwpId() {
		return cwpId;
	}

	public void setCwpId(Long cwpId) {
		this.cwpId = cwpId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
	
}