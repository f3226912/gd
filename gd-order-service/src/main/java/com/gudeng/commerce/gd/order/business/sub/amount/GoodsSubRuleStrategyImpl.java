package com.gudeng.commerce.gd.order.business.sub.amount;

import java.util.Map;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.enm.ESubType;

/**
 * @Description: TODO(按商品补贴)
 * @author mpan
 * @date 2015年12月5日 上午11:09:59
 */
public class GoodsSubRuleStrategyImpl extends ASubRuleStrategy {
	
	static {
		subType = ESubType.GOODS_SUB.getCode();
	}

	@Override
	public long calcSubAmt(Map<String, Object> paramMap) throws ServiceException {
		SubPayRuleDTO subPayRule = (SubPayRuleDTO) paramMap.get(ISubRuleStrategy.SUB_PAY_RULE);
		return (long) (subPayRule.getSubAmount() * 100);
	}

}
