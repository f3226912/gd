package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;

public interface AdSpaceToolService {

	Long insert(AdSpaceEntity entity) throws Exception;
	
	List<AdSpaceDTO> queryByCondition(Map<String, Object> map) throws Exception;
	
	Long countByCondition(Map<String, Object> map) throws Exception;
	
	int deleteById(Long id) throws Exception;
	
	AdSpaceDTO getById(Long id) throws Exception;
	
	int update(AdSpaceEntity entity) throws Exception;
	
	boolean isExist(Long menuId, String spaceSign) throws Exception;
	
	boolean canDelete(Long adSpaceId) throws Exception;
	
	int deleteAdSpace(Long id, String updateUserId, String updateUserName) throws Exception;
}
