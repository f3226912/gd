package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActGiftBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.entity.ActGiftBaseinfoEntity;

public interface ActGiftBaseinfoService {

	Long add(ActGiftBaseinfoEntity entity);

	List<ActGiftBaseinfoDTO> queryPageByCondition(Map<String, Object> map);
	
	Integer getTotalCountByCondition(Map<String, Object> map);

	Integer update(ActGiftBaseinfoEntity entity);

	ActGiftBaseinfoDTO getById(Integer id);
	
	boolean exist(String name);

	List<ActGiftBaseinfoDTO> queryListByCondition(Map<String, Object> map);
	
	int delete(ActGiftBaseinfoDTO dto);

	Integer sumActivityGiftCost(Integer giftId);

	List<ActGiftBaseinfoDTO> getListByCondition(Map<String, Object> map);
	/**
	 * 更新礼品信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateGiftBaseInfo(Map<String, Object> params) throws Exception;
}
