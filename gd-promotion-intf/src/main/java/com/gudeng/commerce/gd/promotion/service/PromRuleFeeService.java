package com.gudeng.commerce.gd.promotion.service;

import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;

public interface PromRuleFeeService {

	/**
	 * 保存活动规则和手续费
	 * @param dto
	 */
	void savePormRuleFee(PromBaseinfoDTO dto);
	
	/**
	 * 查询规则和手续费
	 * @param map
	 */
	PromBaseinfoDTO queryPromRuleFee(Map<String,Object> map);
}
