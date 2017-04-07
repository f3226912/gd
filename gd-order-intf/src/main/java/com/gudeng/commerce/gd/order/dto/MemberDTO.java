
package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

public class MemberDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2212542191853552755L;
	
	private Long memberId;
	
	private Long businessId;
	
	private Long sellerMemberId;
	
	private String shopsName;
	
	private Long marketId;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getSellerMemberId() {
		return sellerMemberId;
	}

	public void setSellerMemberId(Long sellerMemberId) {
		this.sellerMemberId = sellerMemberId;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}


}
