package com.gudeng.commerce.gd.supplier.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.supplier.dao.BaseDao;
import com.gudeng.commerce.gd.supplier.entity.SystemLogEntity;
import com.gudeng.commerce.gd.supplier.service.SystemLogService;

@SuppressWarnings(value={"rawtypes","unchecked"})
public class SystemLogServiceImpl implements SystemLogService {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Long insertLog(SystemLogEntity entity) throws Exception {
		return (Long) baseDao.persist(entity, Long.class);
	}
	
	
	@Override
	public int[] batchInsertLog(List<SystemLogEntity> entityList) throws Exception {
		Map[] batchValues = new Map[entityList.size()];
		
		for(int i=0; i<entityList.size(); i++){
			SystemLogEntity en = entityList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("type", en.getType());
			map.put("content", en.getContent());
			map.put("createTime", en.getCreateTime());
			map.put("chennel", en.getChennel());
			map.put("createUserId", en.getCreateUserId());
			map.put("operationId", en.getOperationId());
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("SystemLog.addLog", batchValues);
	}
}
