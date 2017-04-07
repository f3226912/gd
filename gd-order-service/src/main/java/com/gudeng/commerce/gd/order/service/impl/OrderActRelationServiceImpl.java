package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderActRelationDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.service.OrderActRelationService;
import com.gudeng.framework.dba.util.DalUtils;

public class OrderActRelationServiceImpl implements OrderActRelationService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int add(OrderActRelationEntity entity) throws Exception {
		return baseDao.persist(entity, Integer.class);
	}

	@Override
	public List<OrderActRelationDTO> getByOrderNo(Long orderNo) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		return baseDao.queryForList("OrderActRelation.getListByOrderNo", map,
				OrderActRelationDTO.class);
	}

	@Override
	public List<OrderActRelationDTO> getByActId(Integer actId) throws Exception {
		return null;
	}
	
	@Override
	public int batchInsertEntity(List<OrderActRelationEntity> orderActList) throws Exception {
		int len = orderActList.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			OrderActRelationEntity entity = orderActList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}

		return baseDao.batchUpdate("OrderActRelation.batchInsertEntity", batchValues).length;
	}
}

	
