package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AppshareDTO;
public interface AppshareToolService {

	List<AppshareDTO> queryPageByCondition(Map<String, Object> map) throws Exception;
	
	int getTotalCountByCondtion(Map<String, Object> map) throws Exception;
	
	List<AppshareDTO> queryListByCondition(Map<String, Object> map) throws Exception;

	int updateStatus(AppshareDTO dto) throws Exception;
	
	List<AppshareDTO> queryDetailPageByCondition(Map<String, Object> map) throws Exception;
	
	int getDetailTotalCountByCondtion(Map<String, Object> map) throws Exception;

}
