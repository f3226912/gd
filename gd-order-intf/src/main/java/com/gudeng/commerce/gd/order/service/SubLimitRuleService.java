/**
 * @Title: SubLimitRuleService.java
 * @Package com.gudeng.commerce.gd.admin.service
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午3:54:16
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.SubAmountTipConfDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleWhitelistDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;

/**
 * @ClassName: SubLimitRuleService
 * @Description: TODO(补贴限制规则服务接口)
 * @author mpan
 * @date 2015年11月30日 下午3:54:16
 */
public interface SubLimitRuleService {

	/**
	 * 根据条件查询补贴限制规则详情
	 */
	public List<SubLimitRuleDTO> getSubLimitRuleDetail(Map<String, Object> map) throws ServiceException;
	
	/**
	 * 根据条件查询补贴限制规则列表
	 */
	public List<SubLimitRuleDTO> getSubLimitRuleList(Map<String, Object> map) throws ServiceException;
	
	/**
	 * 新增补贴限制规则
	 */
	public Long saveSubLimitRule(SubLimitRuleDTO subLimitRule) throws ServiceException;
	
	/**
	 * 更新补贴限制规则
	 */
	public void updateSubLimitRule(SubLimitRuleDTO subLimitRule) throws ServiceException;
	
	/**
	 * 删除补贴限制区间规则
	 */
	public void delSubRangeLimitRule(Map<String, Object> map) throws ServiceException;
	
	/**
	 * 新增补贴限制区间规则
	 */
	public Long saveSubRangeLimitRule(SubRangeLimitRuleDTO subRangeLimitRule) throws ServiceException;
	
	/**
	 * 保存或更新补贴限制规则
	 */
	public void saveOrUpdateSubLimitRule(SubLimitRuleDTO subLimitRule) throws Exception;
	
	/**
	 * 删除白名单
	 * @param map
	 */
	public void delWhiteList(Map<String,Object> map) throws ServiceException;
	
	/**
	 * 获取白名单列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SubLimitRuleWhitelistDTO> getWhiteList(Map<String,Object> map) throws ServiceException;
	
	
	public void updateSubLimitRuleStatus(Map<String,Object> map)  throws Exception;
	
	/**
	 * @Title: getSubAmountTipConfList
	 * @Description: TODO(查询补贴额使用提示配置)
	 * @param tipConf
	 * @return
	 * @throws ServiceException
	 */
	public List<SubAmountTipConfDTO> getSubAmountTipConfList(SubAmountTipConfDTO tipConf) throws ServiceException;
	
	/**
	 * @Title: updateSubAmountTipConf
	 * @Description: TODO(更新补贴额使用提示配置)
	 * @param tipConf
	 * @return
	 * @throws ServiceException
	 */
	public int updateSubAmountTipConf(SubAmountTipConfDTO tipConf) throws ServiceException;
	
	/**
	 * 过期限制规则
	 * @return
	 * @throws Exception
	 */
	public int ExpireLimitRule() throws Exception;
	
	
	/**
	 * 获取限制规则列表
	 * @return
	 * @throws ServiceException
	 */
	public List<SubLimitRuleDTO> SearchLimitRuleList(Map<String,Object> map) throws ServiceException;
	
	/**
	 * 获取限制规则列表数量
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	public int searchLimitRuleListCount(Map<String,Object> map) throws ServiceException;
	
}
