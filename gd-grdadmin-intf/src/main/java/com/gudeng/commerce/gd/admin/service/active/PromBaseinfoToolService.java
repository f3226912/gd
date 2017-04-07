package com.gudeng.commerce.gd.admin.service.active;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;

public interface PromBaseinfoToolService {

	/**
	 * 查询活动列表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	List<PromBaseinfoDTO> queryPageByCondition(Map<String,Object> params) throws Exception;
	
	Integer getTotalCountByCondition(Map<String, Object> map) throws Exception;
	
	Integer savePromBaseinfo(PromBaseinfoDTO dto) throws Exception;
	
	/**
	 * 根据活动ID查询活动
	 * @param actId
	 * @return
	 * @throws Exception
	 */
	PromBaseinfoDTO queryPromBaseinfoById(Integer actId) throws Exception;
	
	/**
	 * 校验活动是否开始
	 * @param actId
	 * @return  true 已经开始了 false示开始
	 * @throws Exception
	 */
	boolean validatePromStart(Integer actId) throws Exception;
	
}
