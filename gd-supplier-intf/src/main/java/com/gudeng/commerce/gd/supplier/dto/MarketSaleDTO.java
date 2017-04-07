package com.gudeng.commerce.gd.supplier.dto;

import com.gudeng.commerce.gd.supplier.entity.MarketSale;

public class MarketSaleDTO extends MarketSale {

	private static final long serialVersionUID = 1669440566180246617L;

	private String createTime_str;// 创建时间
	private String updateTime_str;// 修改时间
	private String recordDate_str; // 记录时间
	private String marketName;//市场名称

	public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCreateTime_str() {
		return createTime_str;
	}

	public void setCreateTime_str(String createTime_str) {
		this.createTime_str = createTime_str;
	}

	public String getUpdateTime_str() {
		return updateTime_str;
	}

	public void setUpdateTime_str(String updateTime_str) {
		this.updateTime_str = updateTime_str;
	}

	public String getRecordDate_str() {
		return recordDate_str;
	}

	public void setRecordDate_str(String recordDate_str) {
		this.recordDate_str = recordDate_str;
	}
}
