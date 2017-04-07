package com.gudeng.commerce.gd.promotion.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.promotion.dto.ActActivityIntegralDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;

/**
 * 活动佣金补贴规则操作
 * @author Ailen
 *
 */
public interface ActActivityCommService {
	
	/**
	 * 获得规则
	 * @param params
	 * @return
	 */
	public List<GdActActivityCommDTO> getList(Map<String, Object> params);
	
	/**
	 * 获得规则 分页
	 * @param params
	 * @return
	 */
	public List<GdActActivityCommDTO> getListPage(Map<String, Object> params);
	
	/**
	 * 获得数量
	 * @param params
	 * @return
	 */
	public Integer getCount(Map<String, Object> params);
	
	/**
	 * 添加批量规则
	 * @param rules
	 */
	public void addListRules(List<GdActActivityCommDTO> rules);
	
	
   public ActActivityIntegralDTO getIntegralByUserId(Map<String, Object> paraMap);
}
