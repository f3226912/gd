package com.gudeng.commerce.info.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;

public interface ProBaiduToolService {
	
	List<ProBaiduEntityDTO> sumReport(Map<String, Object> map) throws Exception;
}