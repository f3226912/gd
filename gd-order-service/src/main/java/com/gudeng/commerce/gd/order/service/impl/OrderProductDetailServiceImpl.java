package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.framework.dba.util.DalUtils;

@Service
public class OrderProductDetailServiceImpl implements OrderProductDetailService {

	@Autowired
	private BaseDao<?> baseDao;

	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long insertEntity(OrderProductDetailEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("preSalProductDetailId", id);
		return (int) baseDao.execute("OrderProductDetail.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count = 0;
		for (Long id : idList) {
			count = deleteById(id);
			if (count <= 0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(OrderProductDetailDTO obj) throws Exception {
		int count = baseDao.execute("OrderProductDetail.updateDTO", obj);
		if (count <= 0) {
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<OrderProductDetailDTO> objList) throws Exception {
		int count = 0;
		for (OrderProductDetailDTO dto : objList) {
			count = baseDao.execute("OrderProductDetail.deleteById", dto);
			if (count <= 0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("OrderProductDetail.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public OrderProductDetailDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("preSalProductDetailId", id);
		return (OrderProductDetailDTO) this.baseDao.queryForObject("OrderProductDetail.getById", map,
				OrderProductDetailDTO.class);
	}

	@Override
	public List<OrderProductDetailDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<OrderProductDetailDTO> list = baseDao.queryForList("OrderProductDetail.getListByConditionPage", map,
				OrderProductDetailDTO.class);
		return list;
	}

	@Override
	public List<OrderProductDetailDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<OrderProductDetailDTO> list = baseDao.queryForList("OrderProductDetail.getListByCondition", map,
				OrderProductDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchInsertEntity(List<OrderProductDetailEntity> entityList) throws Exception {
		int len = entityList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for (int i = 0; i < len; i++) {
			OrderProductDetailEntity entity = entityList.get(i);
			batchValues[i] = DalUtils.convertToMap(entity);
		}

		return baseDao.batchUpdate("OrderProductDetail.batchInsertEntity", batchValues).length;
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNo(Map<String, Object> map) throws Exception {
		List<OrderProductDetailDTO> list = baseDao.queryForList("OrderProductDetail.getListByOrderNo", map,
				OrderProductDetailDTO.class);
		return list;
	}

	@Override
	public List<OrderProductDetailDTO> getListByOrderNoList(List<Long> list) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNoList", list);
		return baseDao.queryForList("OrderProductDetail.getListByOrderNoList", map, OrderProductDetailDTO.class);
	}

	@Override
	public int batchUpdate(List<OrderProductDetailDTO> orderDetails) throws ServiceException {
		// List<Map<String, Object>> batchValues= new ArrayList<Map<String,
		// Object>>();
		for (OrderProductDetailDTO orderDetail : orderDetails) {
			baseDao.execute("OrderProductDetail.batchUpdate", DalUtils.convertToMap(orderDetail));
		}
		return orderDetails.size();
		// return baseDao.batchUpdate("OrderProductDetail.batchUpdate",
		// batchValues.toArray(new Map[0])).length;
	}

	@Override
	public List<OrderProductDetailDTO> searchByProductName(Map<String, Object> map) throws ServiceException {
		return baseDao.queryForList("OrderProductDetail.searchByProductName", map, OrderProductDetailDTO.class);
	}

	@Override
	public OrderProductDetailDTO getFirstProductByOrderNo(Map<String, Object> map) throws ServiceException {
		// TODO Auto-generated method stub
		return baseDao.queryForObject("OrderProductDetail.getFirstProductByOrderNo", map, OrderProductDetailDTO.class);
	}

}
