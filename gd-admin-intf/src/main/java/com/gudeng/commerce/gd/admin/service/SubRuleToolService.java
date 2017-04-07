package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gudeng.commerce.gd.order.dto.CategoryTreeDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleParamDTO;

/**
 * 
 * @author Semon
 *
 */
public interface SubRuleToolService {
	
	long addSubRule(SubPayRuleParamDTO subruleDTO) throws Exception;
	
	SubPayRuleDTO getRuleInfo(int ruleId) throws Exception;
	
	int getRuleTotal(Map<String,Object> map) throws Exception;
	
	List<SubPayRuleDTO> getRuleList(Map<String,Object> map) throws Exception;
	
	int updateRuleStatus(Map<String,Object> map) throws Exception;
	
	CategoryTreeDTO validateCate(Set<CategoryTreeDTO> list,Map<String,Object> map) throws Exception;
	
	SubPayRuleDTO getRuleInfo(Map<String,Object> map)throws Exception;
	
	long modifyRule(SubPayRuleParamDTO subruleDTO)throws Exception;

}
