/**
 * @Title: SubLimitRuleService.java
 * @Package com.gudeng.commerce.gd.admin.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午3:54:16
 * @version V1.0
 */
package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;

/**
 * @ClassName: SubLimitRuleService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年11月30日 下午3:54:16
 */
public interface SubLimitRuleToolService {

	/**
	 * 根据条件查询补贴限制规则
	 */
	public List<SubLimitRuleDTO> getSubLimitRuleDetail(Map<String, Object> map) throws Exception;
	
	/**
	 * 更新补贴限制规则
	 */
	public void saveOrUpdateSubLimitRule(SubLimitRuleDTO subLimitRule) throws Exception;
	
	/**
	 * 删除白名单
	 * @param map
	 */
	public void delWhiteList(Map<String,Object> map)  throws Exception ;
	
	public void updateSubLimitRuleStatus(Map<String,Object> map)  throws Exception;
	
	public List<SubLimitRuleDTO> searchLimitRuleList(Map<String,Object> map)  throws Exception;

	
	public int getSearchLimitRuleListCount(Map<String,Object> map)  throws Exception;

}
