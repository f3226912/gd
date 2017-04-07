package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.customer.dto.NpsOfferPriceDTO;
import com.gudeng.commerce.gd.customer.entity.NpsOfferPriceEntity;

public interface NpsOfferPriceToolService extends BaseToolService<NpsOfferPriceDTO> {
	public Long insert(NpsOfferPriceEntity entity) throws Exception;
}