package com.gudeng.commerce.gd.admin.dto;

public class BusinessSteelyardInputDTO {
	/**ID */
	private Long steelyardId;

	/**商铺ID */
	private Long businessId;
	
	/**秤mac */
	private String macAddr;
	
	/**秤ID */
	private String stlydId;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getStlydId() {
		return stlydId;
	}

	public void setStlydId(String stlydId) {
		this.stlydId = stlydId;
	}

	public Long getSteelyardId() {
		return steelyardId;
	}

	public void setSteelyardId(Long steelyardId) {
		this.steelyardId = steelyardId;
	}
}
