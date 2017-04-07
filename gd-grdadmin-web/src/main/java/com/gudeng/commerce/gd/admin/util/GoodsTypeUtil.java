package com.gudeng.commerce.gd.admin.util;

import org.apache.commons.lang3.StringUtils;
/**
 * 页面获取货物类型工具类
 * @author xiaojun
 */
public class GoodsTypeUtil {
	/**
	 * 实际获取货物类型的方法
	 * @param code
	 * @return
	 */
	public static String getGoodsTypeByCode(String code){
		if (StringUtils.isNotBlank(code)) {
			return EGoodsType.getValueByCode(Integer.valueOf(code));
		}
		return "其他";
	}
}
