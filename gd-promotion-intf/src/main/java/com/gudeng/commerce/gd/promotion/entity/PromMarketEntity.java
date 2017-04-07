package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "prom_market")
public class PromMarketEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3861824289481803716L;

	private Integer id;

	private Integer actId;

	private Integer marketId;

	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "actId")
	public Integer getActId() {

		return this.actId;
	}

	public void setActId(Integer actId) {

		this.actId = actId;
	}

	@Column(name = "marketId")
	public Integer getMarketId() {

		return this.marketId;
	}

	public void setMarketId(Integer marketId) {

		this.marketId = marketId;
	}
}
