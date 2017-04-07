package com.gudeng.commerce.gd.api.dto.input;

public class AreaAppInputDTO {

	/**
	 * 城市名字
	 */
	private String cityName;
	
	/**
	 * 城市纬度
	 */
	private String cityLng;
	
	/**
	 * 城市经度
	 */
	private String cityLat;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityLng() {
		return cityLng;
	}

	public void setCityLng(String cityLng) {
		this.cityLng = cityLng;
	}

	public String getCityLat() {
		return cityLat;
	}

	public void setCityLat(String cityLat) {
		this.cityLat = cityLat;
	}
}
