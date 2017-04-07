package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdInstoragedetailEntity;

public class GrdInstoragedetailDTO extends GrdInstoragedetailEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2506106114157787915L;
	
	private String unit;
	private String giftNo;
	private Integer marketId;
	private String type;
	private String name;
	private Integer warehouseId;
	private String status;
	private Integer stockTotal;
	private Integer stockAvailable;
	private String createUserId;
	private String updateUserId;
	private String reason;
	private Integer instockcount;
	private Integer count;
	private String createUserName;
	private String updateUserName;
	private Long giftId;
	private String flag;
	private String purchaseNO;
	private Integer balance;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	public Integer getMarketId() {
		return marketId;
	}
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getStockTotal() {
		return stockTotal;
	}
	public void setStockTotal(Integer stockTotal) {
		this.stockTotal = stockTotal;
	}
	public Integer getStockAvailable() {
		return stockAvailable;
	}
	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getInstockcount() {
		return instockcount;
	}
	public void setInstockcount(Integer instockcount) {
		this.instockcount = instockcount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public Long getGiftId() {
		return giftId;
	}
	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getPurchaseNO() {
		return purchaseNO;
	}
	public void setPurchaseNO(String purchaseNO) {
		this.purchaseNO = purchaseNO;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	
}
