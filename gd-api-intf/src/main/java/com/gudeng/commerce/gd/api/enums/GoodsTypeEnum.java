package com.gudeng.commerce.gd.api.enums;

public enum GoodsTypeEnum {

	
	DONGPIN_GOODS(105,"冻品"), 
	FUSHI_GOODS(104,"副食"),
	GANTIAO_GOODS(102,"干调"),
	GUOSHU_GOODS(101,"果蔬"),
	SHUICHAN_GOODS(103,"水产"),
	SHUIGUO_GOODS(101,"水果"),
	HAICHAN_GOODS(103,"海产"),
	QXRDL_GOODS(103,"禽畜肉蛋类"),
	QINDAN_GOODS(103,"禽蛋"),
	LIANGYOU_GOODS(102,"粮油"),
	CHA_GOODS(102,"茶"),
	CHAYE_GOODS(102,"茶叶"),
	SHUCAI_GOODS(101,"蔬菜"),
	JIUSHUI_GOODS(104,"酒水"),
	JIUCAI_GOODS(101,"韭菜"),
	QITA_GOODS(110,"其他");
	
	
	/**
	 * 说明：goodTypeCode 是货物类型编码 在 NstGoodTypeEnum中
	 * productCategoryName 是当前主营分类的名
	 */
	private final Integer goodTypeCode;
	private final String productCategoryName;
	
	GoodsTypeEnum(Integer goodTypeCode, String productCategoryName){
		this.goodTypeCode = goodTypeCode;
		this.productCategoryName = productCategoryName;
	}
	
	public Integer getGoodTypeCode() {
		return goodTypeCode;
	}



	public String getProductCategoryName() {
		return productCategoryName;
	}



	public static Integer getKeyByValue(String productCategoryName){
		for (GoodsTypeEnum item :GoodsTypeEnum.values()){
			if (item.getProductCategoryName() == productCategoryName){
				return item.getGoodTypeCode();
			}
		}
		return 110; //全部都匹配不上的话，就返回  110 其他商品
	}
	
	public static GoodsTypeEnum getByKey(Integer goodTypeCode){
		for (GoodsTypeEnum item :GoodsTypeEnum.values()){
			if (item.getGoodTypeCode() == goodTypeCode){
				return item;
			}
		}
		return null;
	}
}
