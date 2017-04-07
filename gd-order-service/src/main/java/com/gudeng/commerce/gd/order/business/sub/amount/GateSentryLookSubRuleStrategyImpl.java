package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.GateSentryLookSubRuleException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderOutmarketInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangePayRuleDTO;
import com.gudeng.commerce.gd.order.enm.EOutmarkStatus;
import com.gudeng.commerce.gd.order.enm.ESubType;
import com.gudeng.commerce.gd.order.enm.ESubUnit;
import com.gudeng.commerce.gd.order.service.OrderOutmarketinfoService;

/**
 * @Description: TODO(门岗目测审查补贴)
 * @author mpan
 * @date 2015年12月5日 上午11:13:44
 */
public class GateSentryLookSubRuleStrategyImpl extends ASubRuleStrategy {
	
	static {
		subType = ESubType.GATE_SENTRY_LOOK_SUB.getCode();
	}
	
	@Autowired
	private OrderOutmarketinfoService orderOutmarketinfoService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GateSentryLookSubRuleStrategyImpl.class);
	
	@Override
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException {
		OrderProductDetailDTO orderGoods = (OrderProductDetailDTO) paramMap.get(ISubRuleStrategy.ORDER_GOODS);
		SubPayRuleDTO subPayRule = (SubPayRuleDTO) paramMap.get(ISubRuleStrategy.SUB_PAY_RULE);
		String outmarkStatus = (String) paramMap.get(ISubRuleStrategy.OUTMARK_STATUS);
		List<SubRangePayRuleDTO> subRangePayRules = subPayRule.getRanges();
		if (CollectionUtils.isEmpty(subRangePayRules)) {
			throw new ServiceException("门岗目测审查规则不存在，规则ID=" + subPayRule.getRuleId());
		}
		OrderOutmarketInfoDTO queryOutmarketInfo = new OrderOutmarketInfoDTO();
		queryOutmarketInfo.setOrderNo(orderGoods.getOrderNo());
		OrderOutmarketInfoDTO outmarketInfo = orderOutmarketinfoService.queryOrderOutmarkCarInfoByOrderNo(queryOutmarketInfo);
		if (outmarketInfo == null) {
			if (EOutmarkStatus.YES_OUTMARK.getCode().equals(outmarkStatus)) {
				OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, subPayRule.getMemberType().toString(), "订单已出场，未查询到出场车辆信息");
				return 0;
			} else {
				OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, subPayRule.getMemberType().toString(), "车辆未出场，门岗目测审查不能完成");
				throw new GateSentryLookSubRuleException("车辆未出场，门岗目测审查不能完成");
			}
		}
		if (outmarketInfo.getCwpid() == null || outmarketInfo.getWeighType() == null) {
			OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, subPayRule.getMemberType().toString(), "门岗目测审查车辆类型和满载度未确定，无法计算补贴");
			LOGGER.info("门岗目测审查车辆类型和满载度未确定，无法计算补贴");
			return 0;
		} else {
			for (SubRangePayRuleDTO subRangePayRule : subRangePayRules) {
				if (outmarketInfo.getCwpid().longValue() == subRangePayRule.getCarType() && outmarketInfo.getWeighType().equals(subRangePayRule.getTruck())) {
					// 设置商品补贴单位
					OrderSubInfoProcess.setOrderGoodsSubUnit(orderGoods, subPayRule.getMemberType().toString(), subRangePayRule.getSubUnit());
					if (ESubUnit.YUAN.getCode().equals(subRangePayRule.getSubUnit())) {
						return (long) (subRangePayRule.getSubAmount() * 100);
					} else {
						return calcDayCarSubAmt(orderGoods, subPayRule.getMemberType().toString(), outmarketInfo.getCarNumber(), subRangePayRule);
					}
				}
			}
		}
		return 0;
		
	}
	
	/**
	 * @Title: calcDayCarSubAmt
	 * @Description: TODO(计算补贴额[限每台车一天一次])
	 * @param carNumber 车牌号
	 * @param subRangePayRule 补贴规则
	 * @return
	 * @throws ServiceException 
	 */
	private long calcDayCarSubAmt(OrderProductDetailDTO orderGoods, String memberType, String carNumber, SubRangePayRuleDTO subRangePayRule) throws ServiceException {
		OrderOutmarketInfoDTO queryOutmarketInfo = new OrderOutmarketInfoDTO();
		queryOutmarketInfo.setCarNumber(carNumber);
		List<OrderOutmarketInfoDTO> outmarketInfos = orderOutmarketinfoService.queryOrderOutmarkCarInfoByCarNumber(queryOutmarketInfo);
		if (CollectionUtils.isNotEmpty(outmarketInfos)) {
			for (OrderOutmarketInfoDTO outmarketInfo : outmarketInfos) {
				if (orderGoods.getOrderNo().longValue() != outmarketInfo.getOrderNo()) {
					OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, memberType, "该车辆当天已补贴（限每天一次）");
					LOGGER.info("该车辆当天已补贴（限每天一次），车牌号：" + carNumber);
					return 0;
				}
			}
		}
		return (long) (subRangePayRule.getSubAmount() * 100);
	}

}
