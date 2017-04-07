package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;

/**
 * @Description: TODO(补贴金额规则策略接口类)
 * @author mpan
 * @date 2015年12月5日 上午10:29:31
 */
public interface ISubRuleStrategy {
	
	public static final String ORDER_GOODS = "ORDER_GOODS"; // 订单商品信息集合
	
	public static final String SUB_PAY_RULE = "SUB_PAY_RULE"; // 补贴发放规则
	
	public static final String OUTMARK_STATUS = "OUTMARK_STATUS"; // 出厂状态
	
	/**
	 * @Title: calcSubAmt
	 * @Description: TODO(计算商品补贴金额)
	 * @param paramMap 商品信息，补贴发放规则
	 * @return 补贴金额，单位分
	 * @throws ServiceException
	 */
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException;

}
