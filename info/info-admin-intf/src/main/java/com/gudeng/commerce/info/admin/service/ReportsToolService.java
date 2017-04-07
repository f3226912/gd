package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ReportsDTO;

public interface ReportsToolService {

	List<ReportsDTO> getPageByCondition(Map<String, Object> map) throws Exception;
	
	Integer getTotalByCondition(Map<String, Object> map) throws Exception;

	Integer update(ReportsDTO reportsDTO) throws Exception;
	
	Integer updateState(Map<String, Object> map) throws Exception;

	ReportsDTO getById(Long reportsID) throws Exception;
}
