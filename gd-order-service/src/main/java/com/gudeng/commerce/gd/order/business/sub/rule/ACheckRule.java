package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.NotFoundSubLimitRuleException;
import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ERuleTimeRange;
import com.gudeng.commerce.gd.order.enm.ESubLimitType;
import com.gudeng.commerce.gd.order.enm.EVehLimit;
import com.gudeng.commerce.gd.order.service.SubHelpService;

/**
 * @Description: TODO(补贴规则检查异常抽象类)
 * @author mpan
 * @date 2015年12月2日 下午7:38:27
 */
public abstract class ACheckRule implements ICheckRule {
	
//	private final static Logger LOGGER = LoggerFactory.getLogger(ACheckRule.class);

	/**
	 * 持有后继的校验对象
	 */
	protected ICheckRule checkRule;
	
	
	@Autowired
	protected SubHelpService subHelpService;
	
	/**
	 * @Title: dayRangeValid
	 * @Description: TODO(每天维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract void dayRangeValid(Map<String, Object> paramMap) throws ServiceException;

	/**
	 * @Title: weekRangeValid
	 * @Description: TODO(每周维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract void weekRangeValid(Map<String, Object> paramMap) throws ServiceException;

	/**
	 * @Title: monthRangeValid
	 * @Description: TODO(每月维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract void monthRangeValid(Map<String, Object> paramMap) throws ServiceException;
	
	/**
	 * @Title: allRangeValid
	 * @Description: TODO(整个活动维度校验)
	 * @throws ServiceException
	 * @throws
	 */
	protected abstract void allRangeValid(Map<String, Object> paramMap) throws ServiceException;

	/**
	 * 补贴规则检查
	 */
	@SuppressWarnings("unchecked")
	public void validation(Map<String, Object> paramMap) throws ServiceException {
		boolean isOpen = false;
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		Map<String, List<SubRangeLimitRuleDTO>> subLimitMap = (Map<String, List<SubRangeLimitRuleDTO>>) paramMap.get(ICheckRule.SUB_RANGE_LIMIT_RULE_MAP);
		if (this instanceof VehLimitCheckRuleImpl && EVehLimit.OPEN.getCode().equals(subLimitRule.getVehLimit()) && orderBase.getOutmarketInfo() != null && StringUtils.isNotBlank(orderBase.getOutmarketInfo().getCarNumber())) {
			// 车辆限制检查类
			isOpen = true;
		} else if (this instanceof UamountLimitCheckRuleImpl && EVehLimit.OPEN.getCode().equals(subLimitRule.getUamountLimit())) {
			// 补贴用户额度限制检查类
			isOpen = true;
		} else if (this instanceof UtimesLimitCheckRuleImpl && EVehLimit.OPEN.getCode().equals(subLimitRule.getUtimesLimit())) {
			// 补贴用户次数限制检查类
			isOpen = true;
		} else if (this instanceof TwoUtimesLimitCheckRuleImpl && EVehLimit.OPEN.getCode().equals(subLimitRule.getTwoUtimesLimit())) {
			// 用户间交易频率限制检查类
			isOpen = true;
		} else if (this instanceof TamountLimitCheckRuleImpl && EVehLimit.OPEN.getCode().equals(subLimitRule.getTamountLimit())) {
			// 补贴额度限制
			isOpen = true;
		}
		if (isOpen) {
			List<SubRangeLimitRuleDTO> subRangeLimitRules = subLimitMap.get(ESubLimitType.getCodeByClassName(this.getClass().getSimpleName()));
			if (CollectionUtils.isEmpty(subRangeLimitRules)) {
				throw new NotFoundSubLimitRuleException("未找到补贴限制规则");
			}
			for (SubRangeLimitRuleDTO subRangeLimitRule : subRangeLimitRules) {
				if (subRangeLimitRule.getCount() == null && subRangeLimitRule.getAmount() == null) {
					continue;
				}
				paramMap.put(ICheckRule.SUB_RANGE_LIMIT_RULE, subRangeLimitRule);
				if (ERuleTimeRange.DAY.getCode() == subRangeLimitRule.getTimeRange()) {
					dayRangeValid(paramMap);
				} else if (ERuleTimeRange.WEEK.getCode() == subRangeLimitRule.getTimeRange()) {
					weekRangeValid(paramMap);
				} else if (ERuleTimeRange.MONTH.getCode() == subRangeLimitRule.getTimeRange()) {
					monthRangeValid(paramMap);
				} else if (ERuleTimeRange.ALL.getCode() == subRangeLimitRule.getTimeRange()) {
					allRangeValid(paramMap);
				}
			}
		}
		if (checkRule != null) {
			checkRule.validation(paramMap);
		}
	}
	
	/**
	 * @Title: getMemberId
	 * @Description: TODO(根据会员类型获取会员ID)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型
	 * @return
	 * @throws ServiceException
	 */
	protected Long getMemberId(OrderBaseinfoDTO orderBase, String memberType) throws ServiceException {
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			return Long.valueOf(orderBase.getMemberId());
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			return Long.valueOf(orderBase.getSellMemberId());
		} else {
			throw new ServiceException("未知的会员类型" + memberType);
		}
	}

	public void setCheckRule(ICheckRule checkRule) {
		this.checkRule = checkRule;
	}

}
