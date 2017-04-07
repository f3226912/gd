package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;

public interface CountBaseService {
	
	public Double countCommission(String ruleJsonStr, GdOrderActivityQueryDTO queryDTO, 
			GdProductActInfoDTO productInfo, Double minAmt);
}
