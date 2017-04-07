package com.gudeng.commerce.gd.api.dto.output;

/**
 * 采购订单活动信息
 * @author TerryZhang
 */
public class PromotionDetail {
	
	/**
	 * 活动id
	 */
	private Integer promId;

	/**
	 * 活动名称
	 */
	private String promName;
	
	/**
	 * 活动类型名称: "产销汇"
	 */
	private String promTypeName;

	public String getPromName() {
		return promName;
	}

	public void setPromName(String promName) {
		this.promName = promName;
	}

	public Integer getPromId() {
		return promId;
	}

	public void setPromId(Integer promId) {
		this.promId = promId;
	}

	public String getPromTypeName() {
		return promTypeName;
	}

	public void setPromTypeName(String promTypeName) {
		this.promTypeName = promTypeName;
	}
}
