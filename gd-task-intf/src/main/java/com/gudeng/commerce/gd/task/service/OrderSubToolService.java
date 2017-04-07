package com.gudeng.commerce.gd.task.service;

import com.gudeng.commerce.gd.exception.ServiceException;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2015年12月8日 下午5:35:11
 */
public interface OrderSubToolService {
	
	/**
	 * @Title: handleOrderSubAmt
	 * @Description: TODO(处理订单补贴金额，并更新到库)
	 * @param orderNo
	 * @throws ServiceException
	 */
	public void handleOrderSubAmtToDB(Long orderNo) throws ServiceException;
	
	/**
	 * @Title: checkOrderSubRule
	 * @Description: TODO(检查订单补贴规则)
	 * @throws ServiceException
	 * @throws
	 */
	public void checkOrderSubRuleToDB(Long orderNo) throws ServiceException;

}
