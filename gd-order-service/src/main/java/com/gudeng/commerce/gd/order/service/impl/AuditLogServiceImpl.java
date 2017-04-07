package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.AuditLogDTO;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;
import com.gudeng.commerce.gd.order.service.AuditLogService;

@Service
@SuppressWarnings(value={"rawtypes","unchecked"})
public class AuditLogServiceImpl implements AuditLogService {
	
	@Autowired
    private BaseDao  baseDao ;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public int addAuditLog(AuditLogEntity al) throws Exception {
		return (int) baseDao.execute("AuditLog.addAuditLog", al);
	}
	
	@Override
	public List<AuditLogDTO> getListByOrderNo(Long orderNo, String type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("orderNo", orderNo);
		return	(List<AuditLogDTO>)baseDao.queryForList("AuditLog.getListByOrderNo", map, AuditLogDTO.class);
	}

	@Override
	public List<AuditLogDTO> getListByOrderNo(Long orderNo) throws Exception {
		Map map = new HashMap();
		map.put("orderNo", orderNo);
		List<AuditLogDTO> list= baseDao.queryForList("AuditLog.getListByOrderNo", map, AuditLogDTO.class);
		return list;
	}


	@Override
	public Long insertEntity(AuditLogEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}
	
	
	@Override
	public List<AuditLogEntity> getSYSAuditLogByOrderNo(Long orderNo) throws Exception {
		Map map = new HashMap();
		map.put("orderNo", orderNo);
		return baseDao.queryForList("AuditLog.getSYSAuditLogByOrderNo", map, AuditLogEntity.class);
	}
}
