package com.gudeng.commerce.gd.promotion.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.promotion.dao.BaseDao;
import com.gudeng.commerce.gd.promotion.dto.ActActivityIntegralDTO;
import com.gudeng.commerce.gd.promotion.dto.GdActActivityCommDTO;
import com.gudeng.commerce.gd.promotion.service.ActActivityCommService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;

/**
 * 活动佣金补贴规则操作
 * @author Ailen
 *
 */
public class ActActivityCommServiceImpl implements ActActivityCommService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<GdActActivityCommDTO> getList(Map<String, Object> params) {
		return baseDao.queryForList("GdActActivityComm.getList", params, GdActActivityCommDTO.class);
	}

	@Override
	public List<GdActActivityCommDTO> getListPage(Map<String, Object> params) {
		return baseDao.queryForList("GdActActivityComm.getListPage", params, GdActActivityCommDTO.class);
	}

	@Override
	public Integer getCount(Map<String, Object> params) {
		return baseDao.queryForObject("GdActActivityComm.getTotal", params, Integer.class);
	}

	@Override
	@Transactional
	public void addListRules(List<GdActActivityCommDTO> rules) {
		for (Iterator<GdActActivityCommDTO> iterator = rules.iterator(); iterator.hasNext();) {
			
			GdActActivityCommDTO gdActActivityCommDTO = iterator.next();
			
			/*
			 * 转换为MAP
			 */
			Map<String, Object> params = DalUtils.convertToMap(gdActActivityCommDTO);
			
			/*
			 * 添加一条数据
			 */
			baseDao.execute("GdActActivityComm.insert", params);
			
		}

	}

	@Override
	public ActActivityIntegralDTO getIntegralByUserId(Map<String, Object> paraMap) {
		ActActivityIntegralDTO actActivityIntegralDTO=baseDao.queryForObject("GdActActivityComm.getIntegralByActivityId", paraMap,ActActivityIntegralDTO.class);
		return actActivityIntegralDTO;
	}

}
