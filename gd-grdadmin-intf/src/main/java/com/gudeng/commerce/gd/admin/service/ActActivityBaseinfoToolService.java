package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.entity.ActActivityBaseinfoEntity;

public interface ActActivityBaseinfoToolService {

	Long add(ActActivityBaseinfoEntity entity) throws Exception;
	
	List<ActActivityBaseinfoDTO> queryPageByCondition(Map<String, Object> map) throws Exception ;
	
	Integer getTotalCountByCondition(Map<String, Object> map) throws Exception;
	
	ActActivityBaseinfoDTO getById(Integer id) throws Exception;

	Long addActivity(ActActivityBaseinfoDTO dto) throws Exception;
	
	int delete(ActActivityBaseinfoDTO dto) throws Exception;

	int updateActivity(ActActivityBaseinfoDTO dto) throws Exception;

	int updateLaunch(ActActivityBaseinfoDTO dto) throws Exception;

	List<ActActivityBaseinfoDTO> queryListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 活动统计分页
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	List<ActActivityBaseinfoDTO> queryActivityStatisticPage(Map<String, Object> map) throws Exception;
	
	/**
	 * 活动统计总记录数
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	Integer getActivityStatisticTotalCount(Map<String, Object> map) throws Exception;

	List<ActActivityBaseinfoDTO> queryActStatisticListByCondition(Map<String, Object> map) throws Exception;

	/**
	 * 获取某个活动的一个参与用户
	 * @param activityId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	ActReUserActivityDto getActivityUser(String activityId, String userId) throws Exception;
}
