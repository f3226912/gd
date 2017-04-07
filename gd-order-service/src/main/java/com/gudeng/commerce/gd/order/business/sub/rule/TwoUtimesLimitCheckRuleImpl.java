package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;

/**
 * @Description: TODO(用户间交易频率限制检查类)
 * @author mpan
 * @date 2015年12月2日 下午8:02:49
 */
public class TwoUtimesLimitCheckRuleImpl extends ACheckRule {
	
	@Override
	protected void dayRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Long buyMemberId = getMemberId(orderBase, EMemberType.BUYER.getCode());
		Long sellMemberId = getMemberId(orderBase, EMemberType.WHOLESALER.getCode());
		Integer dayCount = subHelpService.getTwoUserTradingFrequencyByDay(orderBase.getMarketId().longValue(), buyMemberId, sellMemberId);
		int sumDayCount = dayCount + 1;
		boolean flag = sumDayCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "用户间交易频率超过限制>>" + sumDayCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void weekRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Long buyMemberId = getMemberId(orderBase, EMemberType.BUYER.getCode());
		Long sellMemberId = getMemberId(orderBase, EMemberType.WHOLESALER.getCode());
		Integer weekCount = subHelpService.getTwoUserTradingFrequencyByWeek(orderBase.getMarketId().longValue(), buyMemberId, sellMemberId);
		int sumWeekCount = weekCount + 1;
		boolean flag = sumWeekCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "用户间交易频率超过限制>>" + sumWeekCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void monthRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Long buyMemberId = getMemberId(orderBase, EMemberType.BUYER.getCode());
		Long sellMemberId = getMemberId(orderBase, EMemberType.WHOLESALER.getCode());
		Integer monthCount = subHelpService.getTwoUserTradingFrequencyByMonth(orderBase.getMarketId().longValue(), buyMemberId, sellMemberId);
		int sumMonthCount = monthCount + 1;
		boolean flag = sumMonthCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "用户间交易频率超过限制>>" + sumMonthCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void allRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		Long buyMemberId = getMemberId(orderBase, EMemberType.BUYER.getCode());
		Long sellMemberId = getMemberId(orderBase, EMemberType.WHOLESALER.getCode());
		Integer allCount = subHelpService.getTwoUserTradingFrequencyByAll(orderBase.getMarketId().longValue(), buyMemberId, sellMemberId, subLimitRule.getTimeStart(), subLimitRule.getTimeEnd());
		int sumAllCount = allCount + 1;
		boolean flag = sumAllCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "用户间交易频率超过限制>>" + sumAllCount + ">" + subRangeLimitRule.getCount());
		}
	}

}
