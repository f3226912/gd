package com.gudeng.commerce.gd.promotion.service;

import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityQueryDTO;
import com.gudeng.commerce.gd.promotion.dto.GdPayBankCardInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;

public interface CountContextService extends CountBaseService {

	public Double getCommission(GdActActivityCommDTO com, 
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo);

	public Double getSubsidy(GdActActivityCommDTO com,
			GdOrderActivityQueryDTO queryDTO, GdProductActInfoDTO productInfo,
			GdPayBankCardInfoDTO payCardInfo, boolean isBuyer);
}
