package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.customer.entity.Area;

public class AreaDTO extends Area implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 是否含有子节点
	 */
	private boolean isParent;

	private String parentName;// 父节点名称

	private String pParentId;// 父节点的父节点的Id
	private String pParentName;// 父节点的父节点的名称

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getpParentId() {
		return pParentId;
	}

	public void setpParentId(String pParentId) {
		this.pParentId = pParentId;
	}

	public String getpParentName() {
		return pParentName;
	}

	public void setpParentName(String pParentName) {
		this.pParentName = pParentName;
	}
}
