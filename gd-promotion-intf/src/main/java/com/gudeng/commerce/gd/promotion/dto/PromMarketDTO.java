package com.gudeng.commerce.gd.promotion.dto;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.promotion.entity.PromMarketEntity;

@Entity(name = "prom_market")
public class PromMarketDTO extends PromMarketEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3861824289481803716L;

	private String marketName;

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

}
