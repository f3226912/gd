package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.MarketEntity;

public class MarketDTO extends MarketEntity implements Serializable {

	public enum MARKET_TYPE {
		STREET_MARKET("1"), MARKET("2"), QTYHTJ("3");

		MARKET_TYPE(String value) {
			this.value = value;
		}

		private final String value;

		public String getValue() {
			return value;
		}
	}

	public enum STATUS {
		USED(0), CLOSE(1), DELETED(2);

		STATUS(Integer value) {
			this.value = value;
		}

		private final Integer value;

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3173816935271081592L;

	private String provinceName;

	private String cityName;

	/**
	 * 用户id
	 */
	private Long memberId;

	private String areaName;

	private String useStatus;

	private String marketTypeName;

	private String createTimeString;

	private String updateTimeString;
	
	private String ids;//列表显示id字段

	public String getProvinceName() {
		return provinceName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getMarketTypeName() {
		return marketTypeName;
	}

	public void setMarketTypeName(String marketTypeName) {
		this.marketTypeName = marketTypeName;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
}
