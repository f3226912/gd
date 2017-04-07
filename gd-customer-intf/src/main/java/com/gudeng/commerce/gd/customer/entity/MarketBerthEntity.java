package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

public class MarketBerthEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 684710770657242877L;
	@ExcelConf(excelHeader="No.", sort = 1)
	private int id;
	private int marketId;
	private String area;
	private String build;
	private String layer;
	private String berth;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	@ExcelConf(excelHeader="市场铺位编号", sort = 6)
	private String berthCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMarketId() {
		return marketId;
	}
	public void setMarketId(int marketId) {
		this.marketId = marketId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBuild() {
		return build;
	}
	public void setBuild(String build) {
		this.build = build;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getBerth() {
		return berth;
	}
	public void setBerth(String berth) {
		this.berth = berth;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getBerthCode() {
		return berthCode;
	}
	public void setBerthCode(String berthCode) {
		this.berthCode = berthCode;
	}
	
	
}	
