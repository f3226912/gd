package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;

/**
 * @Description: TODO(车辆限制检查类)
 * @author mpan
 * @date 2015年12月2日 下午8:01:20
 */
public class VehLimitCheckRuleImpl extends ACheckRule {
	
	@Override
	protected void dayRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		OrderOutmarketInfoDTO outmarketInfo = orderBase.getOutmarketInfo();
		Integer dayCount = (Integer) subHelpService.getSubOutMarketByDay(outmarketInfo.getCarNumber(), orderBase.getMarketId().longValue());
		int sumDayCount = dayCount + 1;
		boolean flag = sumDayCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "车辆进出市场频率超过限制>>" + sumDayCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void weekRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		OrderOutmarketInfoDTO outmarketInfo = orderBase.getOutmarketInfo();
		Integer weekCount = (Integer) subHelpService.getSubOutMarketByWeek(outmarketInfo.getCarNumber(), orderBase.getMarketId().longValue());
		int sumWeekCount = weekCount + 1;
		boolean flag = sumWeekCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "车辆进出市场频率超过限制>>" + sumWeekCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void monthRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		OrderOutmarketInfoDTO outmarketInfo = orderBase.getOutmarketInfo();
		Integer monthCount = subHelpService.getSubOutMarketByMonth(outmarketInfo.getCarNumber(), orderBase.getMarketId().longValue());
		int sumMonthCount = monthCount + 1;
		boolean flag = sumMonthCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "车辆进出市场频率超过限制>>" + sumMonthCount + ">" + subRangeLimitRule.getCount());
		}
	}

	@Override
	protected void allRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		OrderOutmarketInfoDTO outmarketInfo = orderBase.getOutmarketInfo();
		Integer allCount = subHelpService.getSubOutMarketByAll(outmarketInfo.getCarNumber(), orderBase.getMarketId().longValue(), subLimitRule.getTimeStart(), subLimitRule.getTimeEnd());
		int sumAllCount = allCount + 1;
		boolean flag = sumAllCount > subRangeLimitRule.getCount();
		if (flag) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "车辆进出市场频率超过限制>>" + sumAllCount + ">" + subRangeLimitRule.getCount());
		}
	}

}
