package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.CartInfoEntity;
/**
 * 购物车DTO
 * @date 2016年11月2日
 */
public class CartInfoDTO extends CartInfoEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4232758008396124070L;
	private String updateTimeStr;  //更新时间
	private Double newPrice; //商品最新价格
	private String newState; //商品最新状态 售罄0、已下架-1、正常1
	private String unitName; //商品单位中文
	
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public String getNewState() {
		return newState;
	}
	public void setNewState(String newState) {
		this.newState = newState;
	}
	
	

	

}