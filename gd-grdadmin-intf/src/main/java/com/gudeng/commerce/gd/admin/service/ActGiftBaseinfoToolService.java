package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;

public interface ActGiftBaseinfoToolService {

	Long add(ActGiftBaseinfoEntity entity) throws Exception;
	
	Integer update(ActGiftBaseinfoEntity entity) throws Exception;
	
	List<ActGiftBaseinfoDTO> queryPageByCondition(Map<String, Object> map) throws Exception;
	
	Integer getTotalCountByCondition(Map<String, Object> map) throws Exception;
	
	ActGiftBaseinfoDTO getById(Integer id) throws Exception;
	
	boolean exist(String name) throws Exception;
	
	List<ActGiftBaseinfoDTO> queryListByCondition(Map<String, Object> map) throws Exception;

	int delete(ActGiftBaseinfoDTO dto) throws Exception;
	
	/**
	 * 所有活动礼品预算总和
	 * @param activityId
	 * @return
	 */
	Integer sumActivityGiftCost(Integer giftId) throws Exception;

	List<ActGiftBaseinfoDTO> getListByCondition(Map<String, Object> map) throws Exception; 
	
	/**
	 * 还在使用某个礼品的活动数量：礼品兑换结束时间大于当前时间
	 * @param giftId
	 * @return
	 * @throws Exception 
	 */
	public int getActivityUseGiftCount(int giftId) throws Exception;
}
