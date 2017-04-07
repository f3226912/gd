package com.gudeng.commerce.gd.order.dto;

import java.util.List;

import com.gudeng.commerce.gd.order.entity.CartInfoEntity;

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
	private String createTimeStr;  //创建时间
	private Double newPrice; //商品最新价格
	private String newState; //商品最新状态 售罄0、已下架-1、正常1
	private String unitName; //商品单位中文
	private Double stockCount;//库存
	private List<String> shoppingItemIds;//购物项，多个Id用，隔开
	private String url170;
	
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
	public Double getStockCount() {
		return stockCount;
	}
	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public List<String> getShoppingItemIds() {
		return shoppingItemIds;
	}
	public void setShoppingItemIds(List<String> shoppingItemIds) {
		this.shoppingItemIds = shoppingItemIds;
	}
	public String getUrl170() {
		return url170;
	}
	public void setUrl170(String url170) {
		this.url170 = url170;
	}
	
	

}