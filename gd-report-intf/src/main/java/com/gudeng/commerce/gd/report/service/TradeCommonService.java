package com.gudeng.commerce.gd.report.service;

import com.gudeng.commerce.gd.report.dto.DataDBQuery;
import com.gudeng.commerce.gd.report.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:48:30
 */
public interface TradeCommonService {
	
	/**
	 * 查询订单量
	 */
	public Long queryOrderNum(DataDBQuery dataQuery) throws ServiceException;
	
	/**
	 * 查询交易额
	 */
	public Double queryTradeAmt(DataDBQuery dataQuery) throws ServiceException;

}
