package com.gudeng.commerce.gd.api.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.NstSpecialLineDTO;

public interface NstSpecialLineToolService {

	List<NstSpecialLineDTO> getSpecialLineByCondition(Map<String, Object> paramMap) throws Exception;

}
