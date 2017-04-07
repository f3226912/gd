package com.gudeng.commerce.gd.supplier.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.gudeng.commerce.gd.supplier.entity.ProductEntity;

public class ProductSinxinDTO extends ProductEntity {

	private static final long serialVersionUID = 1304481685390588978L;

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String categoryId; // 分类ID
	
	private String cateName; // 分类名称
	
	private String name; // 产品名称

	private String macAddr; // 秤mac
	
	private String lastUpdateTime; // 最近更新时间 格式yyyy-MM-dd HH:mm:ss

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUpdateTimeView() {
		if (getUpdateTime() != null) {
			return DATE_FORMAT.format(getUpdateTime());
		}
		return null;
	}

}
