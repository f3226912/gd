package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.DeliveryAddressDTO;
import com.gudeng.commerce.gd.customer.entity.DeliveryAddress;
import com.gudeng.commerce.gd.customer.service.DeliveryAddressService;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public DeliveryAddressDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("DeliveryAddress.getById", map, DeliveryAddressDTO.class);
	}

	@Override
	public List<DeliveryAddressDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("DeliveryAddress.getList", map, DeliveryAddressDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("DeliveryAddress.deleteById", map);
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
		return baseDao.batchUpdate("DeliveryAddress.deleteById", batchValues).length;
	}

	@Override
	public int update(DeliveryAddressDTO t) throws Exception {
		return baseDao.execute("DeliveryAddress.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("DeliveryAddress.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(DeliveryAddress entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<DeliveryAddressDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("DeliveryAddress.getListPage", map, DeliveryAddressDTO.class);
	}

	@Override
	public int updateArriveTime(String orderNo) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("memberAddressId", orderNo);
		return baseDao.execute("DeliveryAddress.updateArriveTime", params);
	}
}