package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.service.OrderRefundRecordService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class OrderRefundRecordServiceImpl implements OrderRefundRecordService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public OrderRefundRecordDTO getById(String createUserId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUserId", createUserId);
		return baseDao.queryForObject("OrderRefundRecord.getById", map, OrderRefundRecordDTO.class);
	}

	@Override
	public List<OrderRefundRecordDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderRefundRecord.getList", map, OrderRefundRecordDTO.class);
	}

	@Override
	public int deleteById(String createUserId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createUserId", createUserId);
		return baseDao.execute("OrderRefundRecord.deleteById", map);
	}
	
	@Override
	public int deleteBatch(List<String> list) throws Exception {
		int len = list.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createUserId", StringUtils.trim(list.get(i)));
			batchValues[i] = map;
		}
		return baseDao.batchUpdate("OrderRefundRecord.deleteById", batchValues).length;
	}

	@Override
	public int update(OrderRefundRecordDTO t) throws Exception {
		return baseDao.execute("OrderRefundRecord.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("OrderRefundRecord.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(OrderRefundRecordEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<OrderRefundRecordDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("OrderRefundRecord.getListPage", map, OrderRefundRecordDTO.class);
	}
}