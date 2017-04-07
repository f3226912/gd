package com.gudeng.commerce.gd.customer.dto;

public class BusinessAreaDTO extends AreaDTO {

	private static final long serialVersionUID = 6302776840402009056L;

	private Integer businessId;
	
	private Integer marketId;
	
	private Integer marketCityId;
	
	private Integer businessCityId;

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getMarketCityId() {
		return marketCityId;
	}

	public void setMarketCityId(Integer marketCityId) {
		this.marketCityId = marketCityId;
	}

	public Integer getBusinessCityId() {
		return businessCityId;
	}

	public void setBusinessCityId(Integer businessCityId) {
		this.businessCityId = businessCityId;
	}
}
