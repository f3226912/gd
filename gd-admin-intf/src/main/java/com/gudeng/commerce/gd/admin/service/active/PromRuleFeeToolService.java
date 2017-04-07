package com.gudeng.commerce.gd.admin.service.active;

import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;

public interface PromRuleFeeToolService {

	/**
	 * 保存活动规则和手续费
	 * @param dto
	 */
	void savePromRuleFee(PromBaseinfoDTO dto) throws Exception;
	
	PromBaseinfoDTO queryPromRuleFeeByActId(Integer actId) throws Exception;
}
