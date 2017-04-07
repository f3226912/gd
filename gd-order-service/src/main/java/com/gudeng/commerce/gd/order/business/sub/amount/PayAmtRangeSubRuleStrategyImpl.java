package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangePayRuleDTO;
import com.gudeng.commerce.gd.order.enm.ESubType;
import com.gudeng.commerce.gd.order.enm.ESubUnit;
import com.gudeng.commerce.gd.order.util.MathUtil;

/**
 * @Description: TODO(按采购金额区间进行补贴)
 * @author mpan
 * @date 2015年12月5日 上午11:12:53
 */
public class PayAmtRangeSubRuleStrategyImpl extends ASubRuleStrategy {
	
	static {
		subType = ESubType.PAY_AMT_RANGE_SUB.getCode();
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PayAmtRangeSubRuleStrategyImpl.class);
	
	@Override
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException {
		OrderProductDetailDTO orderGoods = (OrderProductDetailDTO) paramMap.get(ISubRuleStrategy.ORDER_GOODS);
		SubPayRuleDTO subPayRule = (SubPayRuleDTO) paramMap.get(ISubRuleStrategy.SUB_PAY_RULE);
		List<SubRangePayRuleDTO> subRangePayRules = subPayRule.getRanges();
		if (CollectionUtils.isEmpty(subRangePayRules)) {
			throw new ServiceException("采购金额区间补贴规则不存在");
		}
		long goodsPayAmtFen = (long) (orderGoods.getTradingPrice() * 100);
		if (subRangePayRules.size() == 1) {
			SubRangePayRuleDTO subRangePayRule = subRangePayRules.get(0);
			if ((StringUtils.isBlank(subRangePayRule.getLowerLimit()) || Double.parseDouble(subRangePayRule.getLowerLimit()) == 0) && (StringUtils.isBlank(subRangePayRule.getUpperLimit()) || Double.parseDouble(subRangePayRule.getUpperLimit()) == 0)) {
				return calcGoodsSubAmtByPayAmt(goodsPayAmtFen, subRangePayRule.getSubUnit(), subRangePayRule.getSubAmount());
			}
		}
		for (SubRangePayRuleDTO subRangePayRule : subRangePayRules) {
			if (!isExistsRange(subRangePayRule, goodsPayAmtFen)) {
				continue;
			}
			return calcGoodsSubAmtByPayAmt(goodsPayAmtFen, subRangePayRule.getSubUnit(), subRangePayRule.getSubAmount());
		}
		OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, subPayRule.getMemberType().toString(), "采购金额区间补贴规则不存在");
		LOGGER.error("采购金额区间补贴规则不存在，规则ID=" + subPayRule.getRuleId());
		return 0;
	}
	
	/**
	 * @Title: calcGoodsSubAmtByWeight
	 * @Description: TODO(根据采购金额区间计算补贴金额)
	 * @return 补贴金额，单位分
	 * @throws ServiceException
	 */
	private long calcGoodsSubAmtByPayAmt(long goodsPayAmtFen, String subUnit, double subAmount) throws ServiceException {
		long subAmountFen = (long) (subAmount * 100);
		if (ESubUnit.YUAN.getCode().equals(subUnit)) {
			return subAmountFen;
		} else if (ESubUnit.YUAN_QIAN.getCode().equals(subUnit)) {
			return (long) (MathUtil.mul(goodsPayAmtFen, subAmountFen) / 100000);
		} else if (ESubUnit.YUAN_WAN.getCode().equals(subUnit)) {
			return (long) (MathUtil.mul(goodsPayAmtFen, subAmountFen) / 1000000);
		} else if (ESubUnit.YUAN_SHI_WAN.getCode().equals(subUnit)) {
			return (long) (MathUtil.mul(goodsPayAmtFen, subAmountFen) / 10000000);
		} else if (ESubUnit.YUAN_BAI_WAN.getCode().equals(subUnit)) {
			return (long) (MathUtil.mul(goodsPayAmtFen, subAmountFen) / 100000000);
		} else if (ESubUnit.YUAN_QIAN_WAN.getCode().equals(subUnit)) {
			return (long) (MathUtil.mul(goodsPayAmtFen, subAmountFen) / 1000000000);
		} else {
			throw new ServiceException("商品金额区间补贴单位无法识别");
		}
	}
	
	/**
	 * @Title: isExistsRange
	 * @Description: TODO(商品金额是否存在此区间内)
	 * @param subRangePayRule
	 * @param goodsPayAmt
	 * @return
	 * @throws ServiceException
	 */
	private boolean isExistsRange(SubRangePayRuleDTO subRangePayRule, long goodsPayAmt) throws ServiceException {
		boolean lowerFlag = false;
		boolean upperFlag = false;
		String lowerLimitStr = subRangePayRule.getLowerLimit();
		String upperLimitStr = subRangePayRule.getUpperLimit();
		if (StringUtils.isNotBlank(lowerLimitStr)) {
			long lowerLimit = (long) (Double.parseDouble(lowerLimitStr) * 100);
			if (goodsPayAmt > lowerLimit) {
				lowerFlag = true;
			}
		} else {
			lowerFlag = true;
		}
		if (StringUtils.isNotBlank(upperLimitStr)) {
			long upperLimit = (long) (Double.parseDouble(upperLimitStr) * 100);
			if (goodsPayAmt <= upperLimit) {
				upperFlag = true;
			}
		} else {
			upperFlag = true;
		}
		if (lowerFlag && upperFlag) {
			return true;
		} else {
			return false;
		}
	}
	
}
