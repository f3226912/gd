package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectShopService;

/**
 *功能描述：usercollectshop增删改查实现类
 *
 */
@Service
public class UsercollectShopServiceImpl implements UsercollectShopService {

	@Autowired
	private BaseDao<UsercollectShopDTO> baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public int focusShop(Long userId, Long businessId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("businessId", businessId);
		return baseDao.execute("usercollectShop.insertCollectShop", paramMap);
	}

	@Override
	public int blurShop(Long userId, Long businessId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("businessId", businessId);
		return baseDao.execute("usercollectShop.deleteCollectShopByUserIdAndBusinessId", paramMap);
	}

	@Override
	public int blurShop(Long id) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return baseDao.execute("usercollectShop.deleteCollectShopById", paramMap);
	}

	@Override
	public UsercollectShopDTO getCollectShop(Long userId, Long businessId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		paramMap.put("businessId", businessId);
		
		return (UsercollectShopDTO)baseDao.queryForObject("usercollectShop.getCollectShopByUserIdAndBusinessId", paramMap, UsercollectShopDTO.class);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UsercollectShopDTO> getAll(Long userId ) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		return  baseDao.queryForList("usercollectShop.getAll", paramMap, UsercollectShopDTO.class);
	}

	@Override
	public List<UsercollectShopDTO> getCollectList(Map<String, Object> paramMap) {
		return  baseDao.queryForList("usercollectShop.getCollectList", paramMap, UsercollectShopDTO.class);
	}

	@Override
	public Integer getCount(Map<String, Object> paramMap) {
		return (Integer)baseDao.queryForObject("usercollectShop.getCount", paramMap, Integer.class);
		
	}

	@Override
	public Integer getFocusMeCount(Map<String, Object> map) {
		return (Integer)baseDao.queryForObject("usercollectShop.getFocusMeCount", map, Integer.class);
	}

	@Override
	public List<UsercollectShopDTO> getFocusMeCollectList(Map<String, Object> map) {
		return  baseDao.queryForList("usercollectShop.getFocusMeCollectList", map, UsercollectShopDTO.class);
	}

	@Override
	public Integer blurMoreShop(Long userId, String businessIds) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		String[] businessIdsArray = businessIds.split(",");
		
		paramMap.put("businessIds", businessIdsArray);
		return  (Integer)baseDao.execute("usercollectShop.deleteCollectShopByUserIdAndBusinessIds", paramMap);
	}
}
