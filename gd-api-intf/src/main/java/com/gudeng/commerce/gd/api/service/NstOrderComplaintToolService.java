package com.gudeng.commerce.gd.api.service;

import com.gudeng.commerce.gd.customer.dto.NstOrderComplaintDTO;
import com.gudeng.commerce.gd.customer.entity.NstOrderComplaintEntity;

public interface NstOrderComplaintToolService {

	Long save(NstOrderComplaintEntity nstOrderComplaintEntity) throws Exception;

	NstOrderComplaintDTO getById(Long id) throws Exception;
}
