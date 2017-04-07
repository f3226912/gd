package com.gudeng.commerce.gd.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dao.BaseDao;
import com.gudeng.commerce.gd.report.dto.GoodsTradeResult;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.GoodsService;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:47:14
 */
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public List<GoodsTradeResult> getGoodsTradeResult(Map<String, Object> params) throws ServiceException {
		List<GoodsTradeResult> list = (List<GoodsTradeResult>)baseDao.queryForList("GoodsTrade.getGoodsTradeResult", params, GoodsTradeResult.class);
		
		return list;
	}

}
