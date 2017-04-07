package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.customer.dto.NpsPurchaseDTO;
import com.gudeng.commerce.gd.customer.entity.NpsPurchaseEntity;

public interface NpsPurchaseToolService extends BaseToolService<NpsPurchaseDTO> {
	public Long insert(NpsPurchaseEntity entity) throws Exception;
}