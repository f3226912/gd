package com.gudeng.commerce.gd.order.business.sub.limit;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;

/**
 * @Description: TODO(补贴规则校验接口)
 * @author mpan
 * @date 2015年12月5日 下午4:26:02
 */
public interface ICheckRule {
	
	/**
	 * @Title: validation
	 * @Description: TODO(规则校验)
	 * @param orderInfo
	 * @throws ServiceException
	 * @throws
	 */

   // 总开关，首先进入开关判断，开关开启，启动审核判断	
	public String Switch(OrderBaseinfoDTO orderBase) throws ServiceException;
	
  }
