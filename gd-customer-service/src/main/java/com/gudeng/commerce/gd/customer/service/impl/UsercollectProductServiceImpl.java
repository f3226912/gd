package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.entity.UsercollectProductEntity;
import com.gudeng.commerce.gd.customer.service.UsercollectProductService;

@Service
public class UsercollectProductServiceImpl implements UsercollectProductService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public List<PushProductDTO> getList(Long userId,Long businessId,Long marketId,Long cateId, int startRow,int endRow) {
		Map<String, Number>  p = new HashMap<String, Number>();
		p.put("userId", userId);
		p.put("businessId", businessId);
		p.put("cateId", cateId);
		p.put("marketId", marketId);
		p.put("startRow", startRow);
		p.put("endRow", endRow);
		List<PushProductDTO> list = baseDao.queryForList("userProduct.getList", p, PushProductDTO.class);
		return list;

	}

	@Override
	public int getTotal(Long userId, Long businessId, Long marketId, Long cateId) {
		Map<String, Object>  paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("businessId", businessId);
		paramMap.put("cateId", cateId);
		paramMap.put("marketId", marketId);
		return (int) baseDao.queryForObject("userProduct.getTotal", paramMap, Integer.class);
	}

	@Override
	public List<UsercollectProductDTO> getProductList(Long memberId,Long marketId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", memberId);
		map.put("marketId", marketId);
		return baseDao.queryForList("userProduct.getProductList", map, UsercollectProductDTO.class);
	}

	@Override
	public Long addFocus(Long userId, Long productId,Long categoryId) {
		UsercollectProductEntity usercollectProductEntity = new UsercollectProductEntity();
		usercollectProductEntity.setCreateTime(new Date());
		usercollectProductEntity.setCateId(categoryId);
		usercollectProductEntity.setUserId(userId);
		usercollectProductEntity.setProductId(productId);
		baseDao.persist(usercollectProductEntity, Long.class);
		return productId;
	}

	@Override
	public void cancelFocus(Long userId, Long productId) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("productId", productId);
	    baseDao.execute("userProduct.cancelFocus", paramMap);
	}

	@Override
	public UsercollectProductDTO getCollect(Long userId, Long productId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("productId", productId);

		return (UsercollectProductDTO)baseDao.queryForObject("userProduct.getCollect", param, UsercollectProductDTO.class);
	}

	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		return (Integer)baseDao.queryForObject("userProduct.getCount", paramMap, Integer.class);
	}

	@Override
	public List<PushProductDTO> getCollectList(Long userId, Long businessId,
			Long marketId, Long productId, int startRow, int endRow) {
		Map<String,Object>  p = new HashMap<>();
		p.put("userId", userId);
		p.put("businessId", businessId);
		p.put("productId", productId);
		p.put("marketId", marketId);
		p.put("startRow", startRow);
		p.put("endRow", endRow);
		List<PushProductDTO> list = baseDao.queryForList("userProduct.getCollectList", p,PushProductDTO.class);
		return list;
	}

	@Override
	public Integer cancelMoreFocus(Long userId, String productIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String[] productIdsArray = productIds.split(",");

		paramMap.put("productIds", productIdsArray);
		return (Integer)baseDao.execute("userProduct.cancelMoreFocus", paramMap);
	}

	@Override
	public List<UsercollectProductDTO> getProductsOfConcerned(Map<String, Object> params) throws Exception{
		params.put("pictureType", 4);
		return baseDao.queryForList("userProduct.getProductsOfConcerned", params, UsercollectProductDTO.class);
	}

	@Override
	public int getProductsCountOfConcerned(Map<String, Object> params) throws Exception {
		params.put("pictureType", 4);
		return (Integer)baseDao.queryForObject("userProduct.getProductsCountOfConcerned", params, Integer.class);
	}
}
