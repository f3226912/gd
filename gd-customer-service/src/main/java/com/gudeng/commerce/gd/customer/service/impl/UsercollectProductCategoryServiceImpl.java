package com.gudeng.commerce.gd.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.UsercollectProductCategoryEntity;
import com.gudeng.commerce.gd.customer.service.UsercollectProductCategoryService;

@Service
public class UsercollectProductCategoryServiceImpl implements
		UsercollectProductCategoryService {
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long focus(Long categoryId, Long userId) {
		UsercollectProductCategoryEntity usercollectProductCategoryEntity = new UsercollectProductCategoryEntity();

		usercollectProductCategoryEntity.setCreateTime(new Date());
		usercollectProductCategoryEntity.setProductCategoryId(categoryId);
		usercollectProductCategoryEntity.setUserId(userId);

		Long id = (Long) baseDao.persist(usercollectProductCategoryEntity,
				Long.class);
		return id;
	}

	@Override
	public void cancelFocus(Long categoryId,Long userId) {
		Map<String, Long> p = new HashMap<String, Long>();
		p.put("categoryId", categoryId);
		p.put("userId", userId);
		baseDao.execute("userProductCategory.deleUserProductCategoryId", p);

	}

	@Override
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("userId", userId);
		List<UsercollectProductCategoryDTO> list=baseDao.queryForList("userProductCategory.getFocusCategory", map, UsercollectProductCategoryDTO.class);
		return list;
	}

	@Override
	public List<UsercollectProductCategoryDTO> getFocusCategory(Long userId, Integer curLevel, Long marketId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("curLevel", curLevel);
		map.put("marketId", marketId);
		List<UsercollectProductCategoryDTO> list=baseDao.queryForList("userProductCategory.getFocusCategory", map, UsercollectProductCategoryDTO.class);
		return list;
	}

}
