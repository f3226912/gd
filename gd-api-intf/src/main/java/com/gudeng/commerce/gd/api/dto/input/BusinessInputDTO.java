package com.gudeng.commerce.gd.api.dto.input;

public class BusinessInputDTO {

	/** 店铺id  */
	private Long businessId;
	
	/** 铺主id */
	private Long memberId;
	
	/** 市场id  */
	private Long marketId;
	
	/** 收货地城市名  */
	private String receiptCityName;
	
	/** 收货地城市 id */
	private String receiptCityId;
	
	/** 商铺id list 多个商铺id用#_#隔开  */
	private String businessIdListStr;

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getReceiptCityName() {
		return receiptCityName;
	}

	public void setReceiptCityName(String receiptCityName) {
		this.receiptCityName = receiptCityName;
	}

	public String getReceiptCityId() {
		return receiptCityId;
	}

	public void setReceiptCityId(String receiptCityId) {
		this.receiptCityId = receiptCityId;
	}

	public String getBusinessIdListStr() {
		return businessIdListStr;
	}

	public void setBusinessIdListStr(String businessIdListStr) {
		this.businessIdListStr = businessIdListStr;
	}
}
