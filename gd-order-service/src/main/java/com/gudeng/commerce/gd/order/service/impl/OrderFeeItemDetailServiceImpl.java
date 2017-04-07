package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderFeeItemDetailService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
import com.gudeng.framework.dba.util.DalUtils;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class OrderFeeItemDetailServiceImpl implements OrderFeeItemDetailService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public OrderFeeItemDetailDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("OrderFeeItemDetailEntity.getById", map, OrderFeeItemDetailDTO.class);
	}

	@Override
	public List<OrderFeeItemDetailDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderFeeItemDetailEntity.getList", map, OrderFeeItemDetailDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("OrderFeeItemDetailEntity.deleteById", map);
	}
	
	@Override
	public int deleteByOrderNo(String orderNo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		return baseDao.execute("OrderFeeItemDetailEntity.deleteByOrderNo", map);
	}
	
	@Override
	public int deleteByParam(Map<String,Object> param) throws Exception {
		return baseDao.execute("OrderFeeItemDetailEntity.deleteByParam", param);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("OrderFeeItemDetailEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(OrderFeeItemDetailDTO t) throws Exception {
		return baseDao.execute("OrderFeeItemDetailEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderFeeItemDetailEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(OrderFeeItemDetailEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<OrderFeeItemDetailDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderFeeItemDetailEntity.getListPage", map, OrderFeeItemDetailDTO.class);
	}

	@Override
	public int batchInsertEntity(List<OrderFeeItemDetailDTO> orderActFeeList)
			throws Exception {
		int len = orderActFeeList.size();
		@SuppressWarnings("unchecked")
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			OrderFeeItemDetailDTO entity = orderActFeeList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}

		return baseDao.batchUpdate("OrderFeeItemDetailEntity.batchInsertEntity", batchValues).length;
	}

	@Override
	@Transactional
	public int batchUpdate(List<OrderFeeItemDetailDTO> orderActFeeList,
			Long orderNo) throws Exception {
		int num = deleteByOrderNo(orderNo.toString());
		if(orderActFeeList == null || orderActFeeList.size() == 0){
			return num;
		}
		return batchInsertEntity(orderActFeeList);
	}

	@Override
	public int batchInsert(List<OrderFeeItemDetailEntity> feelList) throws Exception{
		int len = feelList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			OrderFeeItemDetailEntity entity = feelList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}
		return baseDao.batchUpdate("OrderFeeItemDetailEntity.batchInsertEntity", batchValues).length;
	}
}