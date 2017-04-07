package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;
import com.gudeng.commerce.gd.order.util.MathUtil;

/**
 * @Description: TODO(补贴用户额度限制检查类)
 * @author mpan
 * @date 2015年12月2日 下午8:01:52
 */
public class UamountLimitCheckRuleImpl extends ACheckRule {
	
	@Override
	protected void dayRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		String memberType = (String) paramMap.get(ICheckRule.MEMBER_TYPE);
		Long memberId = getMemberId(orderBase, memberType);
		Double dayAmount = subHelpService.getMemberSubAmountByDay(orderBase.getMarketId().longValue(), memberId, memberType);
		long dayAmountFen = (long) (dayAmount * 100); 
		boolean flag = true;
		long subAmountFen = 0;
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			subAmountFen = dayAmountFen + (long) (orderBase.getSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			subAmountFen = dayAmountFen + (long) (orderBase.getSellSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		}
		if (flag && !calcMaxOrderSubAmt(orderBase, memberType, limitAmountFen, dayAmountFen)) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "单一用户补贴额超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
		}
	}

	@Override
	protected void weekRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		String memberType = (String) paramMap.get(ICheckRule.MEMBER_TYPE);
		Long memberId = getMemberId(orderBase, memberType);
		Double weekAmount = subHelpService.getMemberSubAmountByWeek(orderBase.getMarketId().longValue(), memberId, memberType);
		long weekAmountFen = (long) (weekAmount * 100);
		boolean flag = true;
		long subAmountFen = 0;
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			subAmountFen = weekAmountFen + (long) (orderBase.getSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			subAmountFen = weekAmountFen + (long) (orderBase.getSellSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		}
		if (flag && !calcMaxOrderSubAmt(orderBase, memberType, limitAmountFen, weekAmountFen)) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "单一用户补贴额超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
		}
	}

	@Override
	protected void monthRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		String memberType = (String) paramMap.get(ICheckRule.MEMBER_TYPE);
		Long memberId = getMemberId(orderBase, memberType);
		Double monthAmount = subHelpService.getMemberSubAmountByMonth(orderBase.getMarketId().longValue(), memberId, memberType);
		long monthAmountFen = (long) (monthAmount * 100);
		boolean flag = true;
		double subAmountFen = 0;
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			subAmountFen = monthAmountFen + (long) (orderBase.getSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			subAmountFen = monthAmountFen + (long) (orderBase.getSellSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		}
		if (flag && !calcMaxOrderSubAmt(orderBase, memberType, limitAmountFen, monthAmountFen)) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "单一用户补贴额超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
		}
	}
	
	@Override
	protected void allRangeValid(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		SubRangeLimitRuleDTO subRangeLimitRule = (SubRangeLimitRuleDTO) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE);
		String memberType = (String) paramMap.get(ICheckRule.MEMBER_TYPE);
		Long memberId = getMemberId(orderBase, memberType);
		Double allAmount = subHelpService.getMemberSubAmountByAll(orderBase.getMarketId().longValue(), memberId, memberType, subLimitRule.getTimeStart(), subLimitRule.getTimeEnd());
		long allAmountFen = (long) (allAmount * 100);
		boolean flag = true;
		double subAmountFen = 0;
		long limitAmountFen = (long) (subRangeLimitRule.getAmount() * 100);
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			subAmountFen = allAmountFen + (long) (orderBase.getSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			subAmountFen = allAmountFen + (long) (orderBase.getSellSubAmount() * 100);
			flag = subAmountFen > limitAmountFen;
		}
		if (flag && !calcMaxOrderSubAmt(orderBase, memberType, limitAmountFen, allAmountFen)) {
			throw new SubLimitRuleCheckException(ERuleTimeRange.getDescByCode(subRangeLimitRule.getTimeRange()) + "单一用户补贴额超过限制>>" + (subAmountFen / 100.0) + ">" + subRangeLimitRule.getAmount());
		}
	}
	
	/**
	 * @Title: calcMaxOrderSubAmt
	 * @Description: TODO(计算有效的订单补贴金额)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型
	 * @param limitSubAmount 限制补贴额
	 * @param subAmountSum 用户补贴额
	 * @return 是否有补贴 true有 false无
	 * @throws ServiceException 
	 */
	private boolean calcMaxOrderSubAmt(OrderBaseinfoDTO orderBase, String memberType, long limitSubAmountFen, long subAmountSumFen) throws ServiceException {
		long maxSubAmountFen = limitSubAmountFen - subAmountSumFen;
		if (maxSubAmountFen > 0) {
			if (EMemberType.BUYER.getCode().equals(memberType)) {
				calcMaxGoodsSubAmt(orderBase.getDetailList(), memberType , orderBase.getOrderAmount(), maxSubAmountFen);
				orderBase.setSubAmount(maxSubAmountFen / 100.0);
			} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
				calcMaxGoodsSubAmt(orderBase.getDetailList(), memberType, orderBase.getOrderAmount(), maxSubAmountFen);
				orderBase.setSellSubAmount(maxSubAmountFen / 100.0);
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @Title: calcMaxGoodsSubAmt
	 * @Description: TODO(计算有效的商品补贴金额)
	 * @param orderGoodsList 订单商品明细集合
	 * @param memberType 会员类型
	 * @param orderSubAmtFen 订单补贴额
	 * @param maxSubAmount 最大的有效订单补贴额
	 * @throws ServiceException
	 */
	private void calcMaxGoodsSubAmt(List<OrderProductDetailDTO> orderGoodsList, String memberType, double orderAmount, long maxSubAmountFen) throws ServiceException {
		if (CollectionUtils.isEmpty(orderGoodsList)) {
			return;
		}
		long orderAmountFen = (long) (orderAmount * 100);
		for (OrderProductDetailDTO orderGoods : orderGoodsList) {
			long tradingPriceFen = (long) (orderGoods.getTradingPrice() * 100);
			// 防止溢出问题
			long maxGoodsSubAmountFen = (long) MathUtil.div(MathUtil.mul(maxSubAmountFen, tradingPriceFen), orderAmountFen);
//			long maxGoodsSubAmountFen = maxSubAmountFen * tradingPriceFen / orderAmountFen;
			if (EMemberType.BUYER.getCode().equals(memberType)) {
				orderGoods.setSubAmount(maxGoodsSubAmountFen / 100.0);
			} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
				orderGoods.setSellSubAmount(maxGoodsSubAmountFen / 100.0);
			}
		}
	}

}
