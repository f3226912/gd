package com.gudeng.commerce.gd.order.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;
import com.gudeng.commerce.gd.order.service.SubHelpService;
import com.gudeng.commerce.gd.order.util.DateUtil;

@Service
public class SubHelpServiceImpl implements SubHelpService {

	@Autowired
	private BaseDao<?> baseDao;
	
	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}
	
	/**
	 * @Title: getAllSubAmount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param marketId
	 * @param type 查询类型: 1每日, 2每周, 3每月
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 * @throws Exception
	 */
	private Double getAllSubAmount(Integer type, Long marketId, Date timeStart, Date timeEnd) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("marketId", marketId);
		paramMap.put("type", type);
		if (timeStart != null) {
			paramMap.put("timeStartStr", DateUtil.toString(timeStart));
		}
		if (timeEnd != null) {
			paramMap.put("timeEndStr", DateUtil.toString(timeEnd));
		}
		Double allAmount = baseDao.queryForObject("subHelp.getAllSubAmount", paramMap , Double.class);
		return allAmount;
	}
	
	@Override
	public Double getAllSubAmountByDay(Long marketId) throws ServiceException {
		return getAllSubAmount(ERuleTimeRange.DAY.getCode(), marketId, null, null);
	}
	
	@Override
	public Double getAllSubAmountByWeek(Long marketId) throws ServiceException {
		return getAllSubAmount(ERuleTimeRange.WEEK.getCode(), marketId, null, null);
	}
	
	@Override
	public Double getAllSubAmountByMonth(Long marketId) throws ServiceException {
		return getAllSubAmount(ERuleTimeRange.MONTH.getCode(), marketId, null, null);
	}
	
	@Override
	public Double getAllSubAmountByAll(Long marketId, Date timeStart, Date timeEnd) throws ServiceException {
		return getAllSubAmount(ERuleTimeRange.ALL.getCode(), marketId, timeStart, timeEnd);
	}
	
	/**
	 * @Title: getMemberSubAmount
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param type
	 * @param marketId
	 * @param memberId
	 * @param memberType
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 * @throws Exception
	 */
	private Double getMemberSubAmount(Integer type, Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查询类型: 1每日, 2每周, 3每月
		paramMap.put("marketId", marketId);
		paramMap.put("memberId", memberId);
		paramMap.put("memberType", memberType);
		if (timeStart != null) {
			paramMap.put("timeStartStr", DateUtil.toString(timeStart));
		}
		if (timeEnd != null) {
			paramMap.put("timeEndStr", DateUtil.toString(timeEnd));
		}
		paramMap.put("type", type);
		return baseDao.queryForObject("subHelp.getSubAmountByMemberId", paramMap , Double.class);
	}
	
	public Double getMemberSubAmountByDay(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getMemberSubAmount(ERuleTimeRange.DAY.getCode(), marketId, memberId, memberType, null, null);
	}
	
	public Double getMemberSubAmountByWeek(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getMemberSubAmount(ERuleTimeRange.WEEK.getCode(), marketId, memberId, memberType, null, null);
	}
	
	public Double getMemberSubAmountByMonth(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getMemberSubAmount(ERuleTimeRange.MONTH.getCode(), marketId, memberId, memberType, null, null);
	}
	
	public Double getMemberSubAmountByAll(Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException {
		return getMemberSubAmount(ERuleTimeRange.ALL.getCode(), marketId, memberId, memberType, timeStart, timeEnd);
	}
	
	private Integer getSingleUserTradingFrequency(Integer type, Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查询类型: 1每日, 2每周, 3每月
		paramMap.put("marketId", marketId);
		paramMap.put("memberId", memberId);
		paramMap.put("memberType", memberType);
		if (timeStart != null) {
			paramMap.put("timeStartStr", DateUtil.toString(timeStart));
		}
		if (timeEnd != null) {
			paramMap.put("timeEndStr", DateUtil.toString(timeEnd));
		}
		paramMap.put("type", type);
		return baseDao.queryForObject("subHelp.getTradingFrequency", paramMap , Integer.class);
	}
	
	public Integer getSingleUserTradingFrequencyByDay(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getSingleUserTradingFrequency(ERuleTimeRange.DAY.getCode(), marketId, memberId, memberType, null, null);
	}
	
	
	public Integer getSingleUserTradingFrequencyByWeek(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getSingleUserTradingFrequency(ERuleTimeRange.WEEK.getCode(), marketId, memberId, memberType, null, null);
	}
	
	
	public Integer getSingleUserTradingFrequencyByMonth(Long marketId, Long memberId,String memberType) throws ServiceException {
		return getSingleUserTradingFrequency(ERuleTimeRange.MONTH.getCode(), marketId, memberId, memberType, null, null);
	}
	
	public Integer getSingleUserTradingFrequencyByAll(Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException {
		return getSingleUserTradingFrequency(ERuleTimeRange.ALL.getCode(), marketId, memberId, memberType, timeStart, timeEnd);
	}
	
	/**
	 * @Title: getTwoUserTradingFrequency
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param type
	 * @param marketId
	 * @param memberId
	 * @param sellMemberId
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 * @throws ServiceException
	 */
	private Integer getTwoUserTradingFrequency(Integer type, Long marketId, Long memberId, Long sellMemberId, Date timeStart, Date timeEnd) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查询类型: 1每日, 2每周, 3每月
		paramMap.put("marketId", marketId);
		paramMap.put("memberId", memberId);
		paramMap.put("sellMemberId", sellMemberId);
		if (timeStart != null) {
			paramMap.put("timeStartStr", DateUtil.toString(timeStart));
		}
		if (timeEnd != null) {
			paramMap.put("timeEndStr", DateUtil.toString(timeEnd));
		}
		paramMap.put("type", type);
		return baseDao.queryForObject("subHelp.getTwoTradingFrequency", paramMap , Integer.class);
	}
	
	public Integer getTwoUserTradingFrequencyByDay(Long marketId, Long memberId, Long sellMemberId) throws ServiceException {
		return getTwoUserTradingFrequency(ERuleTimeRange.DAY.getCode(), marketId, memberId, sellMemberId, null, null);
	}
	
	public Integer getTwoUserTradingFrequencyByWeek(Long marketId, Long memberId, Long sellMemberId) throws ServiceException {
		return getTwoUserTradingFrequency(ERuleTimeRange.WEEK.getCode(), marketId, memberId, sellMemberId, null, null);
	}
	
	public Integer getTwoUserTradingFrequencyByMonth(Long marketId, Long memberId, Long sellMemberId) throws ServiceException {
		return getTwoUserTradingFrequency(ERuleTimeRange.MONTH.getCode(), marketId, memberId, sellMemberId, null, null);
	}
	
	public Integer getTwoUserTradingFrequencyByAll(Long marketId, Long memberId, Long sellMemberId, Date timeStart, Date timeEnd) throws ServiceException {
		return getTwoUserTradingFrequency(ERuleTimeRange.ALL.getCode(), marketId, memberId, sellMemberId, timeStart, timeEnd);
	}
	
	private Integer getSubOutMarket(Integer type, String carNumber, Long marketId, Date timeStart, Date timeEnd) throws ServiceException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查询类型: 1每日, 2每周, 3每月
		paramMap.put("carNumber", carNumber);
		paramMap.put("marketId", marketId);
		paramMap.put("type", type);
		if (timeStart != null) {
			paramMap.put("timeStartStr", DateUtil.toString(timeStart));
		}
		if (timeEnd != null) {
			paramMap.put("timeEndStr", DateUtil.toString(timeEnd));
		}
		return baseDao.queryForObject("subHelp.getSubOutMarketBuyer", paramMap , Integer.class);
	}
	
	public Integer getSubOutMarketByDay(String carNumber, Long marketId) throws ServiceException {
		return getSubOutMarket(ERuleTimeRange.DAY.getCode(), carNumber, marketId, null, null);
	}
	
	public Integer getSubOutMarketByWeek(String carNumber, Long marketId) throws ServiceException {
		return getSubOutMarket(ERuleTimeRange.WEEK.getCode(), carNumber, marketId, null, null);
	}
	
	public Integer getSubOutMarketByMonth(String carNumber, Long marketId) throws ServiceException {
		return getSubOutMarket(ERuleTimeRange.MONTH.getCode(), carNumber, marketId, null, null);
	}
	
	public Integer getSubOutMarketByAll(String carNumber, Long marketId, Date timeStart, Date timeEnd) throws ServiceException {
		return getSubOutMarket(ERuleTimeRange.ALL.getCode(), carNumber, marketId, timeStart, timeEnd);
	}
	
	// 此处是王大神专人使用
	@Override
	public Map<String, Integer> getSubOutMarket(String carNumber, String status, Long marketId, Date date) throws Exception {
		Map<String, Integer> map = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//查询类型: 1每日, 2每周, 3每月
		paramMap.put("carNumber", carNumber);
		paramMap.put("marketId", marketId);
		paramMap.put("status", status);
		paramMap.put("type", "1");
		
		/*
		 * 判断时间，如果未填入时间，则用当天来判断
		 */
		if(date == null){
			date = DateUtil.getNow();
		}
		paramMap.put("countDate", DateUtil.getDate(date, DateUtil.DATE_FORMAT_DATEONLY));
		
		Integer dailySubOutmarket = baseDao.queryForObject("subHelp.getSubOutMarket", paramMap , Integer.class);
		map.put("dailySubOutmarket", dailySubOutmarket);
		
		paramMap.put("type", "2");
		Integer weeklySubOutmarket = baseDao.queryForObject("subHelp.getSubOutMarket", paramMap , Integer.class);
		map.put("weeklySubOutmarket", weeklySubOutmarket);
		
		paramMap.put("type", "3");
		Integer monthlySubOutmarket = baseDao.queryForObject("subHelp.getSubOutMarket", paramMap , Integer.class);
		map.put("monthlySubOutmarket", monthlySubOutmarket);
		return map;
	}

}