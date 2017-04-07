package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdInstorageEntity;

public class GrdInstorageDTO extends GrdInstorageEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2506106114157287915L;
	
	private Integer marketId;
	
	private Integer warehouseId;
	
	private String statusFlag; 

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	
}
