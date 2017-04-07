package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.WhiteListUserFoundException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleWhitelistDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.EVehLimit;

/**
 * @Description: TODO(白名单检查规则实现类)
 * @author mpan
 * @date 2015年12月20日 下午6:31:07
 */
public class WhiteListCheckRuleImpl implements ICheckRule, ICommonCheckRule {
	
	/**
	 * 持有后继的校验对象
	 */
	protected ICheckRule checkRule;

	@Override
	public void checkRule(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		String memberType = (String) paramMap.get(ICheckRule.MEMBER_TYPE);
		List<SubLimitRuleWhitelistDTO> wlist = subLimitRule.getWlist();
		if (CollectionUtils.isEmpty(wlist)) {
			return;
		}
		for (SubLimitRuleWhitelistDTO whitelist : wlist) {
			if (EMemberType.BUYER.getCode().equals(memberType)) {
				if (orderBase.getMemberId().longValue() == whitelist.getMemberId()) {
					throw new WhiteListUserFoundException("检索到白名单用户");
				}
			} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
				if (orderBase.getSellMemberId().longValue() == whitelist.getMemberId()) {
					throw new WhiteListUserFoundException("检索到白名单用户");
				}
			}
		}
	}

	@Override
	public void validation(Map<String, Object> paramMap) throws ServiceException {
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		if (EVehLimit.OPEN.getCode().equals(subLimitRule.getWhiteLimit())) {
			checkRule(paramMap);
		}
		if (checkRule != null) {
			checkRule.validation(paramMap);
		}
	}

	public void setCheckRule(ICheckRule checkRule) {
		this.checkRule = checkRule;
	}

}
