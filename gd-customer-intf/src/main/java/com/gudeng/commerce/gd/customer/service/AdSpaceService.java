package com.gudeng.commerce.gd.customer.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.customer.dto.AdSpaceDTO;
import com.gudeng.commerce.gd.customer.entity.AdSpaceEntity;

public interface AdSpaceService {

	Long persist(AdSpaceEntity entity);
	
	List<AdSpaceDTO> queryByCondition(Map<String, Object> map);

	Long countByCondition(Map<String, Object> map);

	int deleteById(Long id);

	AdSpaceDTO getById(Long id);

	int update(AdSpaceEntity entity);

	boolean isExist(Long menuId, String spaceSign);

	boolean canDelete(Long adSpaceId);

	int deleteAdSpace(Long id, String updateUserId, String updateUserName);
}
