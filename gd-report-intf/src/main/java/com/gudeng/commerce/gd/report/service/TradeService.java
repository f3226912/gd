package com.gudeng.commerce.gd.report.service;

import java.util.List;

import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.dto.TradeResult;
import com.gudeng.commerce.gd.report.dto.UserAllTradeDataDTO;
import com.gudeng.commerce.gd.report.dto.UserTradeDataDTO;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:48:30
 */
public interface TradeService {
	
	/**
	 * 查询成交件数
	 */
	public Long queryGoodsNum(DataDBQuery dataQuery) throws ServiceException;
	
	/**
	 * 查询交易信息
	 */
	public UserTradeDataDTO queryTradeInfo(DataDBQuery dataQuery) throws ServiceException;
	
	/**
	 * 查询交易额、订单量结果集合
	 */
	public List<TradeResult> queryTradeResultList(DataDBQuery dataQuery) throws ServiceException;
	
	/**
	 * 查询全部交易信息
	 */
	public List<UserAllTradeDataDTO> queryAllTradeInfo(DataDBQuery dataQuery) throws ServiceException;
	
	/**
	 * 查询交易买家
	 */
	public List<Long> queryBuyerNum(DataDBQuery dataQuery) throws ServiceException;

}
