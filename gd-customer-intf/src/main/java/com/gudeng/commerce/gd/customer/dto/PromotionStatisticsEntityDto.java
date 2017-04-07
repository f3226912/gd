package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.PromotionStatisticsEntity;

public class PromotionStatisticsEntityDto extends PromotionStatisticsEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136628913039363252L;
	
	private Integer count;

	private String name;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
}
