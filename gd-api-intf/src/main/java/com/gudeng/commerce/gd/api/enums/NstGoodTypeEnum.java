package com.gudeng.commerce.gd.api.enums;

public enum NstGoodTypeEnum {
	
	VEGETABLE_FRUIT(101, "蔬菜水果"),
	
	GRAIN_OIL(102, "干调粮油"),
	
	LIVE_FRESH(103, "活鲜水产"),
	
	FOOD_DRINKS(104, "食品饮料"),
	
	FROZEN_GOODS(105, "冷冻商品"),
	
	HUAFEI_ZHONGZI(106, "化肥种子"),
	
	NONGZI_NONGJU(107, "农资农具,"),
	
	RIYONG_GOODS(108, "日用品"),
	
	JIANCAI_GOODS(109, "建材设备"),
	
	OTHER_GOODS(110, "其他商品");

	NstGoodTypeEnum(int goodsType, String goodsTypeName) {
		this.goodsType = goodsType;
		this.goodsTypeName = goodsTypeName;
	}

	private final int goodsType;
	
	private final String goodsTypeName;
	
	public int getGoodsType() {
		return goodsType;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}
}
