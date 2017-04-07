package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityScoreRecordDTO;
import com.gudeng.commerce.gd.promotion.entity.ActActivityScoreRecordEntity;

/**
 * 功能描述：MemberBaseinfo增删改查实现类
 *
 */

public interface IntegralScoreToolService {
	/**
	 * 通过IntegralScore对象插入数据库
	 * @param ActActivityScoreRecordEntity
	 * @return Long
	 * 
	 */
	Long addIntegralEntity(ActActivityScoreRecordEntity entity) throws Exception;
	
	Integer updatentegralDto(ActActivityScoreRecordDTO dto) throws Exception;
	
	Integer getTotal(Map<String, Object> map) throws Exception;
	
	List<ActActivityScoreRecordDTO> queryPageByCondition(Map<String, Object> map) throws Exception;
	
	Long addIntegralRecord(ActActivityScoreRecordDTO dto) throws Exception;
	
	List<ActActivityScoreRecordDTO> queryListByCondition(Map<String, Object> map) throws Exception;

	ActActivityScoreRecordDTO getById(Integer id) throws Exception;

	int updateIntegralRecord(ActActivityScoreRecordDTO dto) throws Exception; 
}