package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;

/**
 * @Description: TODO(补贴规则校验接口)
 * @author mpan
 * @date 2015年12月5日 下午4:26:02
 */
public interface ICheckRule {
	
	public static final String ORDER_BASE = "ORDER_BASE"; // 订单信息
	
	public static final String SUB_LIMIT_RULE = "SUB_LIMIT_RULE"; // 补贴限制规则
	
	public static final String SUB_RANGE_LIMIT_RULE = "SUB_RANGE_LIMIT_RULE"; // 补贴区间限制规则
	
	public static final String SUB_RANGE_LIMIT_RULE_MAP = "SUB_RANGE_LIMIT_RULE_MAP"; // 补贴区间限制规则MAP
	
	public static final String MEMBER_TYPE = "MEMBER_TYPE"; // 会员类型
	
	/**
	 * @Title: validation
	 * @Description: TODO(规则校验)
	 * @param paramMap 入参
	 * @throws ServiceException
	 */
	public void validation(Map<String, Object> paramMap) throws ServiceException;

}
