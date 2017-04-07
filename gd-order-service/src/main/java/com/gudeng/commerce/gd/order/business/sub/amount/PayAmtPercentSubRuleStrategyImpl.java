package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.enm.ESubType;

/**
 * @Description: TODO(成交金额比例补贴)
 * @author mpan
 * @date 2015年12月5日 上午10:33:44
 */
public class PayAmtPercentSubRuleStrategyImpl extends ASubRuleStrategy {
	
	static {
		subType = ESubType.PAY_AMT_PERCENT_SUB.getCode();
	}

	@Override
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException {
		OrderProductDetailDTO orderGoods = (OrderProductDetailDTO) paramMap.get(ISubRuleStrategy.ORDER_GOODS);
		SubPayRuleDTO subPayRule = (SubPayRuleDTO) paramMap.get(ISubRuleStrategy.SUB_PAY_RULE);
		long goodsPayAmtFen = (long) (orderGoods.getTradingPrice() * 100);
		return goodsPayAmtFen * subPayRule.getSubPercent() / 100000;
	}

}
