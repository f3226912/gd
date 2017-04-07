package com.gudeng.commerce.gd.home.dto.district;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 省市区县选择相关的配置
 * @Project gd-home-intf
 * @ClassName DistrctConfig.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年11月18日 下午5:18:07
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory 目前该类为了实现将省市区县显示在首页物流服务的查询框中的一些效果
 * 
 */
public class DistrctConfig {

	/**
	 * @Fields hotProvince 热门省份配置，热门省份显示在省份列表的排列靠前的位置
	 * @since Ver 1.0
	 */
	public static final Map<String, Integer> hotProvince = new HashMap<>();

	/**
	 * @Fields hotCity 热门城市配置,热门城市显示在热门城市列表
	 * @since Ver 1.0
	 */
	public static final Map<String, Integer> hotCity = new HashMap<>();

	static {

		/**
		 * 添加热门省份
		 */
		hotProvince.put("420000", 0);// 湖北省
		hotProvince.put("450000", 1);// 广西壮族自治区

		/**
		 * 添加热门城市
		 */
		hotCity.put("420100", 0);// 武汉市
		hotCity.put("450900", 1);// 玉林市

	}

}
