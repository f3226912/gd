package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.GateSentryLookSubRuleException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.service.SubPayRuleService;
import com.gudeng.commerce.gd.order.util.MathUtil;

/**
 * @Description: TODO(计算订单补贴金额)
 * @author mpan
 * @date 2015年12月5日 上午10:01:32
 */
public class OrderSubAmtInvoker {
	
	private Map<String, ISubRuleStrategy> subRuleStrategyMap;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderSubAmtInvoker.class);
	
	@Autowired
	private SubPayRuleService subPayRuleService;
	
	public void invoke(OrderBaseinfoDTO orderBase) throws ServiceException {
		if (EOrderStatus.CANNEL.getCode().equals(orderBase.getOrderStatus()) || EOrderStatus.INVALID.getCode().equals(orderBase.getOrderStatus())) {
			throw new ServiceException("订单已取消");
		}
		List<String> memberTypes = new ArrayList<String>();
		if (StringUtils.isBlank(orderBase.getBuySubStatus()) || ESubStatus.WAIT_OUT_CAR.getCode().equals(orderBase.getBuySubStatus())) {
			if (orderBase.getMemberId() != null) {
				memberTypes.add(EMemberType.BUYER.getCode());
			}
		}
		if (StringUtils.isBlank(orderBase.getSellSubStatus()) || ESubStatus.WAIT_OUT_CAR.getCode().equals(orderBase.getSellSubStatus())) {
			if (orderBase.getSellMemberId() != null) {
				memberTypes.add(EMemberType.WHOLESALER.getCode());
			}
		}
		if (CollectionUtils.isEmpty(memberTypes)) {
			throw new ServiceException("订单补贴状态或memberId异常，无法计算补贴");
		} else {
			for (String memberType : memberTypes) {
				try {
					calcOrderSubAmtByMemberType(orderBase, memberType);
				} catch (GateSentryLookSubRuleException e) {
					OrderSubInfoProcess.setOrderSubInfo(orderBase, memberType, ESubStatus.WAIT_OUT_CAR.getCode());
					LOGGER.info("商品门岗目测审查补贴", e);
				} catch (ServiceException e) {
					OrderSubInfoProcess.setOrderSubInfo(orderBase, memberType, ESubStatus.NOT_SUB.getCode());
					LOGGER.info("计算补贴额", e);
				}
			}
		}
	}
	
	/**
	 * @Title: calcOrderSubAmtByMemberType
	 * @Description: TODO(根据用户类型计算补贴)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型 1产地供应商 2农批商 3采购商
	 * @param buySellType 买卖类型 1买 2卖
	 * @return
	 * @throws ServiceException
	 */
	private void calcOrderSubAmtByMemberType(OrderBaseinfoDTO orderBase, String memberType) throws ServiceException {
		long orderSubAmtSum = 0;
		for (OrderProductDetailDTO orderGoods : orderBase.getDetailList()) {
			try {
				// 重置临时商品补贴额
				orderGoods.setGoodsSubAmtTemp(0D);
				// 查询商品对应的补贴发放规则
				SubPayRuleDTO subPayRule = null;
				try {
					Integer ruleId = null;
					if (EMemberType.BUYER.getCode().equals(memberType)) {
						ruleId = orderGoods.getRuleId();
					} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
						ruleId = orderGoods.getSellRuleId();
					}
					if (ruleId == null) {
						LOGGER.info(EMemberType.getDescByCode(memberType) + "没有参与商品补贴活动，产品ID=" + orderGoods.getProductId());
						continue;
					}
					subPayRule = subPayRuleService.getRuleInfo(ruleId);
					if (subPayRule == null) {
						LOGGER.info("商品补贴发放规则不存在，ruleId=" + ruleId);
						continue;
					}
				} catch (Exception e) {
					LOGGER.error("查询商品对应的补贴发放规则失败", e);
					continue;
				}
				// 获取商品补贴额计算策略
				ISubRuleStrategy subRuleStrategy = subRuleStrategyMap.get(subPayRule.getSubType());
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put(ISubRuleStrategy.ORDER_GOODS, orderGoods);
				paramMap.put(ISubRuleStrategy.SUB_PAY_RULE, subPayRule);
				paramMap.put(ISubRuleStrategy.OUTMARK_STATUS, orderBase.getOutmarkStatus());
				// 计算商品补贴额
				long goodsSubAmtFen = subRuleStrategy.calcSubAmt(paramMap);
				// 根据商品补贴额，支付系数，得到最终的补贴额
				long goodsSubAmt = calcGoodsSubAmtByPayCoeff(goodsSubAmtFen, orderBase.getPayments(), orderBase.getOrderAmount(), subPayRule);
				// 设置临时商品补贴额，以便补贴计算异常时回滚
				orderGoods.setGoodsSubAmtTemp(goodsSubAmt / 100.0);
				// 统计订单补贴额
				orderSubAmtSum += goodsSubAmt;
			} catch (ServiceException e) {
				// 计算商品补贴额异常处理
				OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, memberType, e.getMessage());
				throw e;
			}
		}
		// 确保所有商品计算补贴后，再设置商品补贴额
		for (OrderProductDetailDTO orderGoods : orderBase.getDetailList()) {
			OrderSubInfoProcess.setOrderGoodsSubInfo(orderGoods, memberType, orderGoods.getGoodsSubAmtTemp());
		}
		// 设置订单补贴额
		if (orderSubAmtSum > 0) {
			OrderSubInfoProcess.setOrderSubInfo(orderBase, memberType, ESubStatus.SUBED.getCode(), orderSubAmtSum / 100.0);
		} else {
			OrderSubInfoProcess.setOrderSubInfo(orderBase, memberType, ESubStatus.NOT_SUB.getCode(), 0D);
		}
	}
	
	/**
	 * @Title: getPayCoeffMap
	 * @Description: TODO(获取支付方式系数map)
	 * @param subPayRule 补贴发放规则
	 * @return
	 */
	private Map<String, Integer> getPayCoeffMap(SubPayRuleDTO subPayRule) {
		Map<String, Integer> payPerMap = new HashMap<String, Integer>();
		payPerMap.put(EPayType.ACC_BALANCE.getCode(), subPayRule.getWalletCoefficient());
		payPerMap.put(EPayType.OFFLINE_CARD.getCode(), subPayRule.getPosCoefficient());
		payPerMap.put(EPayType.CASH.getCode(), subPayRule.getCashCoefficient());
		return payPerMap;
	}
			
	/**
	 * @Title: calcGoodsSubAmtByPayCoeff
	 * @Description: TODO(按支付方式计算商品补贴金额)
	 * @param goodsSubAmt 商品基本补贴金额
	 * @param orderPayments 订单支付方式集合
	 * @param orderAmount 订单金额
	 * @param subPayRule 补贴发放规则
	 * @return 最终的商品补贴金额（单位分）
	 * @throws ServiceException
	 */
	private long calcGoodsSubAmtByPayCoeff(long goodsSubAmtFen, List<PaySerialnumberDTO> orderPayments, double orderAmount, SubPayRuleDTO subPayRule) throws ServiceException {
		Map<String, Integer> payPerMap = getPayCoeffMap(subPayRule);
		long orderAmountFen = (long) (orderAmount * 100);
		
		long subAmtSum = 0; 
		for (PaySerialnumberDTO payment : orderPayments) {
			Integer subCoeff = payPerMap.get(payment.getPayType());
			long payAmountFen = (long) (payment.getTradeAmount() * 100);
			// 防止溢出问题
			long subAmtbyPayCoeff = (long) (MathUtil.div(MathUtil.mul(MathUtil.mul(goodsSubAmtFen, payAmountFen), subCoeff), orderAmountFen) / 1000);
//			long subAmtbyPayCoeff = goodsSubAmtFen * payAmountFen * subCoeff / orderAmountFen  / 1000;
			subAmtSum += subAmtbyPayCoeff;
		}
		return subAmtSum;
	}
	
	public void setSubRuleStrategyMap(Map<String, ISubRuleStrategy> subRuleStrategyMap) {
		this.subRuleStrategyMap = subRuleStrategyMap;
	}

}
