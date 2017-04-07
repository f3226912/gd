package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;

/**
 * @Description: TODO(补贴总额检查规则接口类)
 * @author mpan
 * @date 2015年12月2日 下午8:43:57
 */
public interface ICommonCheckRule {
	
	/**
	 * @Title: valid
	 * @Description: TODO(补贴总额校验)
	 * @param orderBase
	 * @throws ServiceException
	 * @throws
	 */
	public void checkRule(Map<String, Object> paramMap) throws ServiceException;

}
