package com.gudeng.commerce.gd.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.service.PromotionInfoService;

public class PromotionInfoServiceImpl implements PromotionInfoService {

	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public int addPromOrderEntity(Object entityObj) throws Exception {
		return baseDao.execute("PromInfo.addPromOrderEntity", entityObj);
	}

	@Override
	public int addPromOrderProductEntity(Object entityObj)
			throws Exception {
		return baseDao.execute("PromInfo.addPromOrderProductEntity", entityObj);
	}
}
