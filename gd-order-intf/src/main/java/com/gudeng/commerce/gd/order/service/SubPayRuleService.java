package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.CategoryTreeDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;

public interface SubPayRuleService {
	
	/**
	 * 添加补贴发放规则
	 * @return
	 */
	public long addRule(SubPayRuleParamDTO param)  throws Exception;
	
	public SubPayRuleDTO getRuleInfo(int ruleId)  throws Exception;

	public int getRuleTotal(Map<String,Object> map)  throws Exception;
	
	public List<SubPayRuleDTO> getRuleList(Map<String,Object> map)  throws Exception;
	
	public int updateRuleStatus(Map<String,Object> map)  throws Exception;
	
	CategoryTreeDTO validateCate(Set<CategoryTreeDTO> list,Map<String,Object> map) throws Exception;
	
	public SubPayRuleDTO getRuleInfo(Map<String,Object> map)  throws Exception;
	
	public int startRule() throws Exception;
	
	/**
	 * @Title: querySubPayRuleByGoods
	 * @Description: TODO(根据产品ID查询补贴发放规则)
	 * @param subPayRule
	 * @return
	 * @throws ServiceException
	 */
	public SubPayRuleDTO querySubPayRuleByGoods(SubPayRuleDTO subPayRule) throws ServiceException;
	
	/**
	 * @Title: queryGoodsSubPayRuleByMap
	 * @Description: TODO(查询商品补贴规则map)
	 * @param subPayRule
	 * @return
	 * @throws ServiceException
	 */
	public List<SubPayRuleDTO> queryGoodsSubPayRuleByMap(Map<String, Object> map) throws ServiceException;

	long modifyRule(SubPayRuleParamDTO param) throws Exception;
}
