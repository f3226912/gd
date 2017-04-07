package com.gudeng.commerce.gd.order.service;

import java.util.Date;
import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;

/**
 * 补贴计算辅助service
 * @author TerryZhang
 *
 */
public interface SubHelpService {
	
	/**
	 * @Title: getAllSubAmountByDay
	 * @Description: TODO(获取整个市场补贴额)
	 * @param marketId
	 * @return
	 * @throws ServiceException
	 */
	public Double getAllSubAmountByDay(Long marketId) throws ServiceException;
	
	public Double getAllSubAmountByWeek(Long marketId) throws ServiceException;
	
	public Double getAllSubAmountByMonth(Long marketId) throws ServiceException;
	
	public Double getAllSubAmountByAll(Long marketId, Date timeStart, Date timeEnd) throws ServiceException;
	
	/**
	 * @Title: getMemberSubAmountByDay
	 * @Description: TODO(获取用户补贴额)
	 * @param marketId
	 * @param memberId
	 * @param memberType
	 * @return
	 * @throws ServiceException
	 */
	public Double getMemberSubAmountByDay(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Double getMemberSubAmountByWeek(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Double getMemberSubAmountByMonth(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Double getMemberSubAmountByAll(Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException;

	
	/**
	 * @Title: getSingleUserTradingFrequencyByDay
	 * @Description: TODO(获取单一用户的交易频率)
	 * @param marketId
	 * @param memberId
	 * @param memberType
	 * @return
	 * @throws ServiceException
	 */
	public Integer getSingleUserTradingFrequencyByDay(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Integer getSingleUserTradingFrequencyByWeek(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Integer getSingleUserTradingFrequencyByMonth(Long marketId, Long memberId,String memberType) throws ServiceException;
	
	public Integer getSingleUserTradingFrequencyByAll(Long marketId, Long memberId,String memberType, Date timeStart, Date timeEnd) throws ServiceException;
	
	/**
	 * @Title: getTwoUserTradingFrequencyByDay
	 * @Description: TODO( 获取与同一交易目标间交易频率)
	 * @param marketId
	 * @param memberId
	 * @param sellMemberId
	 * @return
	 * @throws ServiceException
	 */
	public Integer getTwoUserTradingFrequencyByDay(Long marketId, Long memberId, Long sellMemberId) throws ServiceException;
	
	public Integer getTwoUserTradingFrequencyByWeek(Long marketId, Long memberId, Long sellMemberId) throws ServiceException;
	
	public Integer getTwoUserTradingFrequencyByMonth(Long marketId, Long memberId, Long sellMemberId) throws ServiceException;
	
	public Integer getTwoUserTradingFrequencyByAll(Long marketId, Long memberId, Long sellMemberId, Date timeStart, Date timeEnd) throws ServiceException;
	
	/**
	 * @Title: getSubOutMarketByDay
	 * @Description: TODO(查询车辆补贴次数)
	 * @param carNumber
	 * @param marketId
	 * @return
	 * @throws ServiceException
	 */
	public Integer getSubOutMarketByDay(String carNumber, Long marketId) throws ServiceException;
	
	public Integer getSubOutMarketByWeek(String carNumber, Long marketId) throws ServiceException;
	
	public Integer getSubOutMarketByMonth(String carNumber, Long marketId) throws ServiceException;
	
	public Integer getSubOutMarketByAll(String carNumber, Long marketId, Date timeStart, Date timeEnd) throws ServiceException;
	
	/**
	 * 根据车牌号 carNumber
	 * 查询车辆通过频率
	 * @param carNumber
	 * @param status
	 * @param marketId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getSubOutMarket(String carNumber, String status, Long marketId, Date date) throws Exception;
}
