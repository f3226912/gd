package com.gudeng.commerce.gd.promotion.enums;

public enum GdActivityRuleUserTypeEnums {
	
	ACT_BUYER(1,"活动买家"), 
	ACT_SHOP(2,"活动商铺"), 
	ACT_PRODUCT_CATEGORY(3,"商品类目"), 
	ACT_PRODUCT(4,"活动商品");
	
	private final Integer actTargetType;
	private final String actTargetTypeNname;
	
	GdActivityRuleUserTypeEnums(Integer actTargetType, String actTargetTypeNname){
		this.actTargetType = actTargetType;
		this.actTargetTypeNname = actTargetTypeNname;
	}
	
	public static String getNameByKey(Integer actTargetType){
		for (GdActivityRuleUserTypeEnums item :GdActivityRuleUserTypeEnums.values()){
			if (item.getActTargetType() == actTargetType){
				return item.getActTargetTypeNname();
			}
		}
		return "";
	}
	
	public static GdActivityRuleUserTypeEnums getEnumsBykey(Integer actTargetType){
		for (GdActivityRuleUserTypeEnums item :GdActivityRuleUserTypeEnums.values()){
			if (item.getActTargetType() == actTargetType){
				return item;
			}
		}
		return null;
	}

	public Integer getActTargetType() {
		return actTargetType;
	}

	public String getActTargetTypeNname() {
		return actTargetTypeNname;
	}
}
