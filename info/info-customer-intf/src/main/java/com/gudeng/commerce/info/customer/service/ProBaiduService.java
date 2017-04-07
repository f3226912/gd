package com.gudeng.commerce.info.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.info.customer.dto.ProBaiduEntityDTO;

public interface ProBaiduService {

	List<ProBaiduEntityDTO> sumReport(Map<String, Object> map);
}
