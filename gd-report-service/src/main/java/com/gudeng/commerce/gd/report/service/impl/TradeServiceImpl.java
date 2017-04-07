package com.gudeng.commerce.gd.report.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.report.dao.BaseDao;
import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.TradeDBQuery;
import com.gudeng.commerce.gd.report.dto.TradeResult;
import com.gudeng.commerce.gd.report.dto.UserAllTradeDataDTO;
import com.gudeng.commerce.gd.report.dto.UserTradeDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;
import com.gudeng.commerce.gd.report.service.TradeService;
import com.gudeng.framework.dba.util.DalUtils;

/**
 * @Description: TODO(所有订单交易服务)
 * @author mpan
 * @date 2016年6月13日 下午4:50:36
 */
public class TradeServiceImpl implements TradeService {
	
	@Autowired
	private BaseDao<?> baseDao;

	@Override
	public Long queryGoodsNum(DataDBQuery dataQuery) throws ServiceException {
		return baseDao.queryForObject("OrderBaseinfo.queryGoodsNum", dataQuery, Long.class);
	}

	@Override
	public UserTradeDataDTO queryTradeInfo(DataDBQuery dataQuery) throws ServiceException {
		TradeDBQuery tradeDBQuery = (TradeDBQuery) dataQuery;
		Map<String, Object> map = DalUtils.convertToMap(dataQuery);
		if (CollectionUtils.isNotEmpty(tradeDBQuery.getOrderStatusIn())) {
			map.put("orderStatusIn", tradeDBQuery.getOrderStatusIn());
		}
		return baseDao.queryForObject("OrderBaseinfo.queryTradeInfo", map, UserTradeDataDTO.class);
	}

	@Override
	public List<TradeResult> queryTradeResultList(DataDBQuery dataQuery) throws ServiceException {
		return baseDao.queryForList("OrderBaseinfo.queryTradeResultList", dataQuery, TradeResult.class);
	}

	@Override
	public List<UserAllTradeDataDTO> queryAllTradeInfo(DataDBQuery dataQuery) throws ServiceException {
		return baseDao.queryForList("OrderBaseinfo.queryAllTradeInfo", dataQuery, UserAllTradeDataDTO.class);
	}

	@Override
	public List<Long> queryBuyerNum(DataDBQuery dataQuery) throws ServiceException {
		return baseDao.queryForList("OrderBaseinfo.queryBuyerNum", dataQuery, Long.class);
	}

}
