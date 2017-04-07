package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;
import com.gudeng.commerce.gd.order.enm.ESubStatus;

/**
 * @Description: TODO(补贴额度限制)
 * @author mpan
 * @date 2015年12月2日 下午8:03:15
 */
public class TamountLimitCheckRuleImpl extends ACheckRule {
	
	@Override
	protected void dayRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Double dayAmount = subHelpService.getAllSubAmountByDay(orderBase.getMarketId().longValue());
		long dayAmountFen = (long) (dayAmount * 100);
		long subAmountFen = dayAmountFen;
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getBuySubStatus()) && orderBase.getSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSubAmount() * 100);
		}
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getSellSubStatus()) && orderBase.getSellSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSellSubAmount() * 100);
		}
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		boolean flag = subAmountFen > limitAmountFen;
		if (flag) {
			// 需求确认，补贴额边界问题，按订单实际补贴额发放
			long maxSubAmountFen = limitAmountFen - dayAmountFen;
			if (maxSubAmountFen <= 0) {
				throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "市场补贴额度超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
			}
		}
	}

	@Override
	protected void weekRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Double weekAmount = subHelpService.getAllSubAmountByWeek(orderBase.getMarketId().longValue());
		long weekAmountFen = (long) (weekAmount * 100);
		long subAmountFen = weekAmountFen;
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getBuySubStatus()) && orderBase.getSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSubAmount() * 100);
		}
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getSellSubStatus()) && orderBase.getSellSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSellSubAmount() * 100);
		}
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		boolean flag = subAmountFen > limitAmountFen;
		if (flag) {
			// 需求确认，补贴额边界问题，按订单实际补贴额发放
			double maxSubAmountFen = limitAmountFen - weekAmountFen;
			if (maxSubAmountFen <= 0) {
				throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "市场补贴额度超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
			}
		}
	}

	@Override
	protected void monthRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Double monthAmount = subHelpService.getAllSubAmountByMonth(orderBase.getMarketId().longValue());
		long monthAmountFen = (long) (monthAmount * 100);
		long subAmountFen = monthAmountFen;
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getBuySubStatus()) && orderBase.getSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSubAmount() * 100);
		}
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getSellSubStatus()) && orderBase.getSellSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSellSubAmount() * 100);
		}
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		boolean flag = subAmountFen > limitAmountFen;
		if (flag) {
			// 需求确认，补贴额边界问题，按订单实际补贴额发放
			double maxSubAmountFen = limitAmountFen - monthAmountFen;
			if (maxSubAmountFen <= 0) {
				throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "市场补贴额度超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
			}
		}
	}

	@Override
	protected void allRangeValid(Map<String, Object> paramMap) throws ServiceException {
		
	}
	
}
