package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;
import com.gudeng.commerce.gd.customer.entity.MarketBerthEntity;

public class MarketBerthDTO extends MarketBerthEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -91880207832490122L;
	private String marketName;
	private String maxArea;
	private String maxBuild;
	private String maxLayer;
	private String maxBerth;
	@ExcelConf(excelHeader="区", sort = 2)
	private String areaStr;
	@ExcelConf(excelHeader="栋", sort = 3)
	private String buildStr;
	@ExcelConf(excelHeader="层", sort = 4)
	private String layerStr;
	@ExcelConf(excelHeader="铺位", sort = 5)
	private String berthStr;
	
	public String getAreaStr() {
		return getArea()+"区";
	}
	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}
	public String getBuildStr() {
		return getBuild()+"栋";
	}
	public void setBuildStr(String buildStr) {
		this.buildStr = buildStr;
	}
	public String getLayerStr() {
		return getLayer()+"层";
	}
	public void setLayerStr(String layerStr) {
		this.layerStr = layerStr;
	}
	public String getBerthStr() {
		return getBerth()+"号";
	}
	public void setBerthStr(String berthStr) {
		this.berthStr = berthStr;
	}
	public String getMaxArea() {
		return maxArea;
	}
	public void setMaxArea(String maxArea) {
		this.maxArea = maxArea;
	}
	public String getMaxBuild() {
		return maxBuild;
	}
	public void setMaxBuild(String maxBuild) {
		this.maxBuild = maxBuild;
	}
	public String getMaxLayer() {
		return maxLayer;
	}
	public void setMaxLayer(String maxLayer) {
		this.maxLayer = maxLayer;
	}
	public String getMaxBerth() {
		return maxBerth;
	}
	public void setMaxBerth(String maxBerth) {
		this.maxBerth = maxBerth;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
}
