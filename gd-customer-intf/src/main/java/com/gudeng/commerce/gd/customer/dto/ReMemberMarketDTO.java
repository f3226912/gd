package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.ReMemberMarketEntity;


public class ReMemberMarketDTO extends ReMemberMarketEntity  implements Serializable, Comparator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8271396881692216975L;
	
	/**
	 * 市场名
	 */
	private String marketName;

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	} 
	 
}
