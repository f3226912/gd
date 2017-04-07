package com.gudeng.commerce.gd.admin.util;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.customer.dto.AreaDTO;

public class AreaUtil {

	public static boolean isCity(AreaDTO city) {
		if (null == city || StringUtils.isEmpty(city.getArea())
				|| "市辖区".equals(city.getArea()) || "县".equals(city.getArea())
				|| "市".equals(city.getArea()) || "省直辖行政单位" .equals(city.getArea())) {
			return false;
		}
		return true;
	}

	public static boolean isAllCity(String place) {
		if (StringUtils.isNotEmpty(place) && place.indexOf("全国") != -1) {
			return true;
		}
		return false;
	}

}
