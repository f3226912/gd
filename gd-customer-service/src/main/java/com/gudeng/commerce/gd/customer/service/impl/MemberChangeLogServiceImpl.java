package com.gudeng.commerce.gd.customer.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberChangeLogDTO;
import com.gudeng.commerce.gd.customer.entity.MemberChangeLogEntity;
import com.gudeng.commerce.gd.customer.service.MemberChangeLogService;


/**
 *功能描述：MemberChangeLog增删改查实现类
 *
 */
@Service
public class MemberChangeLogServiceImpl implements MemberChangeLogService{

	@Autowired
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void addMemberChangeLog(Map map) throws Exception {
		baseDao.execute("MemberChangeLog.addMemberChangeLog", map);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("MemberChangeLog.getTotal", map, Integer.class);
	}

	@Override
	public List<MemberChangeLogDTO> getBySearch(Map map) throws Exception {
		//return  baseDao.queryForList("MemberBaseinfo.getBySearch", map, MemberBaseinfoDTO.class);
		return  baseDao.queryForList("MemberChangeLog.getBySearch", map, MemberChangeLogDTO.class);
	}
}
