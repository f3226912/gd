package com.gudeng.commerce.gd.admin.service;

import java.util.Date;
import java.util.Map;

public interface SubHelpToolService {

	/**
	 * 根据车牌号 carNumber
	 * 查询车辆通过频率
	 * @param carNumber
	 * @param marketId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getSubOutMarket(String carNumber, String status, Long marketId, Date date) throws Exception;

}
