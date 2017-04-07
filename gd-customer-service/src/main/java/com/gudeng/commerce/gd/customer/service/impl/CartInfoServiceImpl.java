package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CartInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.customer.entity.CartInfoEntity;
import com.gudeng.commerce.gd.customer.service.CartInfoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

public class CartInfoServiceImpl implements CartInfoService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public CartInfoDTO getById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.queryForObject("CartInfoEntity.getById", map, CartInfoDTO.class);
	}

	@Override
	public List<CartInfoDTO> getList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CartInfoEntity.getList", map, CartInfoDTO.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return baseDao.execute("CartInfoEntity.deleteById", map);
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
		return baseDao.batchUpdate("CartInfoEntity.deleteById", batchValues).length;
	}

	@Override
	public int update(CartInfoDTO t) throws Exception {
		return baseDao.execute("CartInfoEntity.update", t);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return baseDao.queryForObject("CartInfoEntity.getTotal", map, Integer.class);
	}

	@Override
	public Long insert(CartInfoEntity entity) throws Exception {
		return baseDao.persist(entity, Long.class);
	}

	@Override
	public List<CartInfoDTO> getListPage(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("CartInfoEntity.getListPage", map, CartInfoDTO.class);
	}

	@Override
	public CartInfoDTO getCartItemByParam(Long memberId, Long productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("productId", productId);
		return baseDao.queryForObject("CartInfoEntity.getCartItemByParam", map, CartInfoDTO.class);
	}

	@Override
	public int deleteCartItemByParam(Long memberId, Long productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("productId", productId);
		return baseDao.execute("CartInfoEntity.deleteCartItemByParam", map);
	}

	@Override
	@Transactional
	public PageQueryResultDTO<CartInfoDTO> getCartItemByParam(Map<String, Object> paramMap) throws Exception {
		List<CartInfoDTO> cartList = baseDao.queryForList("CartInfoEntity.getCartItems", paramMap, CartInfoDTO.class);
		Integer totalCount = baseDao.queryForObject("CartInfoEntity.getTotalSearch", paramMap, Integer.class);
		PageQueryResultDTO<CartInfoDTO> queryResult = new PageQueryResultDTO<CartInfoDTO>();
		queryResult.setDataList(cartList);
		queryResult.setTotalCount(totalCount == null ? 0 : totalCount);
		return queryResult;
	}
}