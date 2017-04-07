package com.gudeng.commerce.gd.api.dto.input;

public class CanTakeOrderInputDTO {

	/**货源id*/
	private Long memberAddressId;
	
    /**专线起始地*/
    private String begin_areaId;
    
    /**专线目的地*/
    private String end_areaId;
    
    /**登录用户id*/
    private Long userId;

	public Long getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(Long memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public String getBegin_areaId() {
		return begin_areaId;
	}

	public void setBegin_areaId(String begin_areaId) {
		this.begin_areaId = begin_areaId;
	}

	public String getEnd_areaId() {
		return end_areaId;
	}

	public void setEnd_areaId(String end_areaId) {
		this.end_areaId = end_areaId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    
}
