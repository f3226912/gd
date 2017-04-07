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
import com.gudeng.commerce.gd.order.enm.EGoodsUnit;
import com.gudeng.commerce.gd.order.enm.EIgnoredDecimal;
import com.gudeng.commerce.gd.order.enm.ESubType;
import com.gudeng.commerce.gd.order.enm.ESubUnit;

/**
 * @Description: TODO(按采购重量区间进行补贴)
 * @author mpan
 * @date 2015年12月5日 上午11:11:39
 */
public class WeightRangeSubRuleStrategyImpl extends ASubRuleStrategy {
	
	static {
		subType = ESubType.WEIGHT_RANGE_SUB.getCode();
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WeightRangeSubRuleStrategyImpl.class);

	@Override
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException {
		OrderProductDetailDTO orderGoods = (OrderProductDetailDTO) paramMap.get(ISubRuleStrategy.ORDER_GOODS);
		SubPayRuleDTO subPayRule = (SubPayRuleDTO) paramMap.get(ISubRuleStrategy.SUB_PAY_RULE);
		List<SubRangePayRuleDTO> subRangePayRules = subPayRule.getRanges();
		if (CollectionUtils.isEmpty(subRangePayRules)) {
			throw new ServiceException("采购重量区间补贴规则不存在，规则ID=" + subPayRule.getRuleId());
		}
		long goodsWeight = calcWeightToG(orderGoods.getPurQuantity(), orderGoods.getUnit(), orderGoods.getGrossWeight());
		boolean ignoredDecimal = EIgnoredDecimal.YES.getCode().equals(subPayRule.getSubForTon()) ? true : false;
		if (subRangePayRules.size() == 1) {
			SubRangePayRuleDTO subRangePayRule = subRangePayRules.get(0);
			if ((StringUtils.isBlank(subRangePayRule.getLowerLimit()) || Double.parseDouble(subRangePayRule.getLowerLimit()) == 0) && (StringUtils.isBlank(subRangePayRule.getUpperLimit()) || Double.parseDouble(subRangePayRule.getUpperLimit()) == 0)) {
				return calcGoodsSubAmtByWeight(goodsWeight, subRangePayRule.getSubUnit(), subRangePayRule.getSubAmount(), ignoredDecimal);
			}
		}
		for (SubRangePayRuleDTO subRangePayRule : subRangePayRules) {
			if (!isExistsRange(subRangePayRule, goodsWeight)) {
				continue;
			}
			return calcGoodsSubAmtByWeight(goodsWeight, subRangePayRule.getSubUnit(), subRangePayRule.getSubAmount(), ignoredDecimal);
		}
		OrderSubInfoProcess.setOrderGoodsSubTipInfo(orderGoods, subPayRule.getMemberType().toString(), "采购重量区间补贴规则不存在");
		LOGGER.error("采购重量区间补贴规则不存在，规则ID=" + subPayRule.getRuleId());
		return 0;
	}
	
	/**
	 * @Title: calcGoodsSubAmtByWeight
	 * @Description: TODO(根据商品重量计算补贴金额)
	 * @return 补贴金额，单位分
	 * @throws ServiceException
	 */
	private long calcGoodsSubAmtByWeight(long goodsWeight, String subUnit, double subAmount, boolean ignoredDecimal) throws ServiceException {
		long subAmountFen = (long) (subAmount * 100);
		if (ESubUnit.YUAN.getCode().equals(subUnit)) {
			return subAmountFen;
		} else if (ESubUnit.YUAN_TON.getCode().equals(subUnit)) {
			if (ignoredDecimal) {
				return goodsWeight / 1000000 * subAmountFen;
			} else {
				return goodsWeight / 1000 * subAmountFen / 1000;
			}
		} else if (ESubUnit.YUAN_KG.getCode().equals(subUnit)) {
			if (ignoredDecimal) {
				return goodsWeight / 1000 * subAmountFen;
			} else {
				return goodsWeight * subAmountFen  / 1000;
			}
		} else {
			throw new ServiceException("商品重量区间补贴单位无法识别");
		}
	}
	
	/**
	 * @Title: isExistsRange
	 * @Description: TODO(商品重量是否存在此区间内)
	 * @param subRangePayRule
	 * @param weight
	 * @return
	 * @throws ServiceException
	 */
	private boolean isExistsRange(SubRangePayRuleDTO subRangePayRule, long weight) throws ServiceException {
		boolean lowerFlag = false;
		boolean upperFlag = false;
		String lowerLimitStr = subRangePayRule.getLowerLimit();
		String upperLimitStr = subRangePayRule.getUpperLimit();
		if (StringUtils.isNotBlank(lowerLimitStr)) {
			long lowerLimit = calcWeightToG(Double.parseDouble(lowerLimitStr), subRangePayRule.getUnit());
			if (weight > lowerLimit) {
				lowerFlag = true;
			}
		} else {
			lowerFlag = true;
		}
		if (StringUtils.isNotBlank(upperLimitStr)) {
			long upperLimit = calcWeightToG(Double.parseDouble(upperLimitStr), subRangePayRule.getUnit());
			if (weight <= upperLimit) {
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
	
	/**
	 * @Title: calcWeightToG
	 * @Description: TODO(计算商品重量)
	 * @param purQuantity 数量
	 * @param unit 单位
	 * @return 返回商品重量，单位克
	 * @throws ServiceException
	 */
	private long calcWeightToG(Double purQuantity, String unit) throws ServiceException {
		return calcWeightToG(purQuantity, unit, null);
	}
	
	/**
	 * @Description: TODO(计算商品重量，单位克)
	 * @param purQuantity 数量
	 * @param unit 单位
	 * @param grossWeight 商品毛重
	 * @return 返回商品重量，单位克
	 * @throws ServiceException
	 */
	private long calcWeightToG(Double purQuantity, String unit,Integer grossWeight) throws ServiceException {
		if (purQuantity == null || StringUtils.isBlank(unit)) {
			throw new ServiceException("商品数量或单位不能为空");
		}
		if (EGoodsUnit.TON.getCode().equals(unit)) {
			return (long) (purQuantity * 1000 * 1000);
		} else if (EGoodsUnit.KG.getCode().equals(unit) || EGoodsUnit.GJ.getCode().equals(unit)) {
			return (long) (purQuantity * 1000);
		} else if (EGoodsUnit.K.getCode().equals(unit)) {
			return purQuantity.longValue();
		} else if (grossWeight != null) {
			return (long) (purQuantity * grossWeight);
		} else {
			throw new ServiceException("商品重量无法计算");
		}
	}

}
