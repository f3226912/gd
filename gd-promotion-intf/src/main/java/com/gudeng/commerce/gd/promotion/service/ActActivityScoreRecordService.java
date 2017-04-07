package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityScoreRecordDTO;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;

public interface ActActivityScoreRecordService {

	Long add(ActActivityScoreRecordEntity entity);
	
	Integer getTotal(Map<String, Object> map);
	
	List<ActActivityScoreRecordDTO> queryPageByCondition(Map<String, Object> map);
	
	Integer update(ActActivityScoreRecordDTO dto);
	
	ActActivityScoreRecordDTO getById(Integer id);
	
	List<ActActivityScoreRecordDTO> queryListByCondition(Map<String, Object> map);

	Long addIntegralRecord(ActActivityScoreRecordDTO dto);

	/**
	 * 修改积分记录
	 * @param dto
	 * @return
	 */
	int updateIntegralRecord(ActActivityScoreRecordDTO dto);

	Integer getTotal2(Map<String, Object> map);
}
