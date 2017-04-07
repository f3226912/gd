package com.gudeng.commerce.gd.supplier.bo;

public class RedisConstant {

	/**
	 * redis key
	 */
	public static enum RedisKey{
		//单个产品分类
		PRODUCT_CATEGORY_ID("PRODUCT_CATEGORY_"),
		//市场id所有产品分类
		PRODUCT_CATEGORY_ALL_MARKETID("PRODUCT_CATEGORY_ALL_MARKETID_"),
		//根据id产品分类子集
		PRODUCT_CATEGORY_CHILD("PRODUCT_CATEGORY_CHILD_"),
		//根据市场id产品分类子集
		PRODUCT_CATEGORY_CHILD_MARKETID("PRODUCT_CATEGORY_CHILD_MARKETID_"),
		//根据id查询一级产品分类
		PRODUCT_CATEGORY_TOP("PRODUCT_CATEGORY_TOP"),
		//根据id及市场id查询一级产品分类
		PRODUCT_CATEGORY_TOP_MARKETID("PRODUCT_CATEGORY_TOP_MARKETID_"),
		
		//商品id查商品，返回DTO
		PRODUCT_PRODUCTID("PRODUCT_"),
		
		//对应农批商关联的供应商关联表数据
		PRODUCT_CATEGORY_NPS_REF_SUPP("PRODUCT_CATEGORY_NPS_REF_SUPP_");
		
		public String value;

		RedisKey(String value) {
			this.value = value;
		}
	
	}
	
}
