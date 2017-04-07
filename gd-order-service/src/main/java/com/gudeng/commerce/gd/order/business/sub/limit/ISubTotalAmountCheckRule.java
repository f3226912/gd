package com.gudeng.commerce.gd.order.business.sub.limit;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;

/**
 * @Description: TODO(补贴总额检查规则接口类)
 * @author mpan
 * @date 2015年12月2日 下午8:43:57
 */
public interface ISubTotalAmountCheckRule {
	
	/**
	 * @Title: actRangeValid
	 * @Description: TODO(补贴总额校验)
	 * @param orderBase
	 * @throws ServiceException
	 * @throws
	 */
	public void actRangeValid(OrderBaseinfoDTO orderInfo) throws ServiceException;
 
     
}
   
