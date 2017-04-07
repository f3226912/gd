/**
 * @Title: SubLimitRuleServiceImpl.java
 * @Package com.gudeng.commerce.gd.admin.service.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年11月30日 下午3:54:57
 * @version V1.0
 */
package com.gudeng.commerce.gd.order.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.SubAmountTipConfDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleWhitelistDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.entity.SubAmountEntity;
import com.gudeng.commerce.gd.order.entity.SubLimitRuleWhitelistEntity;
import com.gudeng.commerce.gd.order.service.SubAmountService;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;
import com.gudeng.commerce.gd.order.util.DateUtil;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

/**
 * @ClassName: SubLimitRuleServiceImpl
 * @Description: TODO(补贴限制规则服务实现类)
 * @author mpan
 * @date 2015年11月30日 下午3:54:57
 */
public class SubLimitRuleServiceImpl implements SubLimitRuleService {

	@Autowired
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	private SubAmountService subAmountService;

	@Override
	public List<SubLimitRuleDTO> getSubLimitRuleDetail(Map<String, Object> map) throws ServiceException {
		List<SubLimitRuleDTO> subLimitRules = baseDao.queryForList("SubLimitRule.getSubLimitRuleList", map, SubLimitRuleDTO.class);
		if (CollectionUtils.isEmpty(subLimitRules)) {
			return null;
		}
		Map<String, Object> sfield = new HashMap<String, Object>();
		for (SubLimitRuleDTO subLimitRule : subLimitRules) {
			if (subLimitRule.getTimeStart() != null) {
				subLimitRule.setTimeStartStr(DateUtil.toString(subLimitRule.getTimeStart(), "yyyy-MM-dd HH:mm"));
			}
			if (subLimitRule.getTimeEnd() != null) {
				subLimitRule.setTimeEndStr(DateUtil.getDate(subLimitRule.getTimeEnd(), "yyyy-MM-dd HH:mm"));
			}
			
			Long ruleId = subLimitRule.getRuleId();
			sfield.put("ruleId", ruleId);
			List<SubRangeLimitRuleDTO> subRangeLimitRules = baseDao.queryForList("SubLimitRule.getSubRangeLimitRuleByRuleId", sfield, SubRangeLimitRuleDTO.class);
			subLimitRule.setSubRangeLimitRules(subRangeLimitRules);
			
			sfield.clear();
			sfield.put("limitruleId", ruleId);
			List<SubLimitRuleWhitelistDTO> wlist = getWhiteList(sfield);
			subLimitRule.setWlist(wlist);
			
			if (StringUtils.isNotBlank(subLimitRule.getContact())) {
				subLimitRule.setContacts(Arrays.asList(subLimitRule.getContact().split(",")));
			}
		}
		return subLimitRules;
	}
	
	@Override
	public List<SubLimitRuleDTO> getSubLimitRuleList(Map<String, Object> map) throws ServiceException {
		return baseDao.queryForList("SubLimitRule.getSubLimitRuleList", map, SubLimitRuleDTO.class);
	}
	
	@Override
	public Long saveSubLimitRule(SubLimitRuleDTO subLimitRule) throws ServiceException {
		baseDao.execute("SubLimitRule.saveSubLimitRule", subLimitRule);
		return (Long) baseDao.queryForObject("SubLimitRule.getSeq", null, Long.class);
	}

	@Override
	public void updateSubLimitRule(SubLimitRuleDTO subLimitRule) throws ServiceException {
		baseDao.execute("SubLimitRule.updateSubLimitRule", subLimitRule);
	}

	@Override
	public void delSubRangeLimitRule(Map<String, Object> map) throws ServiceException {
		baseDao.execute("SubLimitRule.delSubRangeLimitRule", map);
	}

	@Override
	public Long saveSubRangeLimitRule(SubRangeLimitRuleDTO subRangeLimitRule) throws ServiceException {
		baseDao.execute("SubLimitRule.saveSubRangeLimitRule", subRangeLimitRule);
		return (Long) baseDao.queryForObject("SubLimitRule.getSeq", null, Long.class);
	}

	@Transactional
	@Override
	public void saveOrUpdateSubLimitRule(SubLimitRuleDTO subLimitRule) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isExits = false;
		if (subLimitRule.getRuleId() != null && subLimitRule.getMarketId() != null) {
			map.put("ruleId", subLimitRule.getRuleId());
			map.put("marketId", subLimitRule.getMarketId());
			List<SubLimitRuleDTO> subLimitRules = getSubLimitRuleDetail(map);
			if (CollectionUtils.isNotEmpty(subLimitRules)) {
				isExits = true;
			}
		}
		Long ruleId = subLimitRule.getRuleId();
		if (isExits) {
			updateSubLimitRule(subLimitRule);
			delSubRangeLimitRule(map);
		} else {
			ruleId = saveSubLimitRule(subLimitRule);
			//添加
			SubAmountEntity subAmount = new SubAmountEntity();
			subAmount.setSubAmountBal(subLimitRule.getSubAmount());
			subAmount.setSubAmountTotal(subLimitRule.getSubAmount());
			subAmount.setMarketId(subLimitRule.getMarketId().intValue());
			subAmount.setHasSubBalance("1");
			subAmount.setMarketName("");
			subAmount.setIsAvailable("1");
			subAmount.setCreateTime(new Date());
			subAmount.setCreateUserId(subLimitRule.getCreateUserId());
			subAmount.setSubRuleId(ruleId);
			subAmountService.add(subAmount);
		}
		List<SubRangeLimitRuleDTO> subRangeLimitRules = subLimitRule.getSubRangeLimitRules();
		if (CollectionUtils.isEmpty(subRangeLimitRules)) {
			return;
		}
		for (SubRangeLimitRuleDTO subRangeLimitRule : subRangeLimitRules) {
			subRangeLimitRule.setRuleId(ruleId);
			if (subRangeLimitRule.getAmount() == null && subRangeLimitRule.getCount() == null) {
				continue;
			}
			saveSubRangeLimitRule(subRangeLimitRule);
		}
		//添加白名单
		if(CollectionUtils.isEmpty(subLimitRule.getWlist()))return;
		Map<String,Object>tempMap = new HashMap<String,Object>();
		for(SubLimitRuleWhitelistEntity swl:subLimitRule.getWlist()){
			tempMap.clear();
			tempMap.put("limitruleId", ruleId);
			tempMap.put("memberId", swl.getMemberId());
			SubLimitRuleWhitelistEntity temWhite = (SubLimitRuleWhitelistEntity) baseDao.queryForObject("SubLimitRule.queryWhiteInfo", tempMap,SubLimitRuleWhitelistEntity.class);
			if(temWhite==null){
				swl.setLimitruleId(Integer.parseInt(ruleId.toString()));
				baseDao.execute("SubLimitRule.saveWhiteList", swl);
			}
			
		}
		
	}

	@Override
	public void delWhiteList(Map<String, Object> map)throws ServiceException {
		baseDao.execute("SubLimitRule.delWhiteList", map);
	}

	@Override
	public List<SubLimitRuleWhitelistDTO> getWhiteList(Map<String,Object> map)
			throws ServiceException {
		List<SubLimitRuleWhitelistDTO> list = baseDao.queryForList("SubLimitRule.queryWhiteInfo", map, SubLimitRuleWhitelistDTO.class);
		return list;
	}

	@Override
	@Transactional
	public void updateSubLimitRuleStatus(Map<String, Object> map)
			throws Exception {
		String userId = map.get("userId")==null?"":map.get("userId").toString();
		SubLimitRuleDTO limitRule = (SubLimitRuleDTO)baseDao.queryForObject("SubLimitRule.getSubLimitRuleInfo",map,SubLimitRuleDTO.class);
		baseDao.execute("SubLimitRule.updateSubLimitRuleStatus",map);
		map.clear();
		//禁用系统规则后，把所以在排队和启用的活动规则都更新为系统规则过期状态
		map.put("marketId", limitRule.getMarketId());
		map.put("userId", userId);
		baseDao.execute("subPayRule.updateRuleByMarketId", map);
		subAmountService.setUnavailableByMarketId(limitRule.getMarketId().intValue(), userId);
		
		
	}

	@Override
	public List<SubAmountTipConfDTO> getSubAmountTipConfList(SubAmountTipConfDTO tipConf) throws ServiceException {
		return baseDao.queryForList("SubLimitRule.getSubAmountTipConfList", tipConf, SubAmountTipConfDTO.class);
	}

	@Override
	public int updateSubAmountTipConf(SubAmountTipConfDTO tipConf) throws ServiceException {
		return baseDao.execute("SubLimitRule.updateSubAmountTipConf", tipConf);
	}

	@Override
	public int ExpireLimitRule() throws Exception {
		Map<String, Object> map = new HashMap<String,Object>();
		//获取过期的限制规则列表
		List<SubLimitRuleDTO> limitRuleList = baseDao.queryForList("SubLimitRule.getExpireSubLimitRuleList", null,SubLimitRuleDTO.class);
		if(CollectionUtils.isEmpty(limitRuleList))return 0;
		for(SubLimitRuleDTO sub:limitRuleList){
			map.clear();
			//过期限制规则
			map.put("ruleId", sub.getRuleId());
			baseDao.execute("SubLimitRule.updateSubLimitRuleStatusByRuleId", map);
			//禁用系统规则后，把所以在排队和启用的活动规则都更新为系统规则过期状态
			
			map.put("marketId", sub.getMarketId());
			map.put("userId", "System");
			baseDao.execute("subPayRule.updateRuleByMarketId", map);
		}
		return limitRuleList.size();
	}

	@Override
	public List<SubLimitRuleDTO> SearchLimitRuleList(Map<String, Object> map)
			throws ServiceException {
		List<SubLimitRuleDTO> list =  baseDao.queryForList("SubLimitRule.searchLimitRuleList", map, SubLimitRuleDTO.class);
		return list;
	}

	@Override
	public int searchLimitRuleListCount(Map<String, Object> map)
			throws ServiceException {
		
		return (Integer)baseDao.queryForObject("SubLimitRule.searchLimitRuleListCount", map, Integer.class);
	}

}
