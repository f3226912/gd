package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.dto.MemberSubResultDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubRangeLimitRuleDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;

/**
 * @Description: TODO(订单补贴限制规则检查类)
 * @author mpan
 * @date 2015年12月16日 下午5:01:32
 */
public class OrderSubLimitRuleInvoker {

	@Autowired
	private SubLimitRuleService subLimitRuleService;
	
	@Autowired
	private SubCheckRuleHelp subCheckRuleHelp;
	
//	private final static Logger LOGGER = LoggerFactory.getLogger(OrderSubLimitRuleInvoker.class);

	public void checkSubLimitRule(OrderBaseinfoDTO orderBase) throws ServiceException {
		if (EOrderStatus.CANNEL.getCode().equals(orderBase.getOrderStatus()) || EOrderStatus.INVALID.getCode().equals(orderBase.getOrderStatus())) {
			throw new ServiceException("订单已取消");
		}
		List<String> memberTypes = new ArrayList<String>();
		if (ESubStatus.SUBED.getCode().equals(orderBase.getBuySubStatus())) {
			memberTypes.add(EMemberType.BUYER.getCode());
		}
		if (ESubStatus.SUBED.getCode().equals(orderBase.getSellSubStatus())) {
			memberTypes.add(EMemberType.WHOLESALER.getCode());
		}
		if (CollectionUtils.isEmpty(memberTypes)) {
			throw new ServiceException("订单没有补贴，跳过补贴限制规则检查");
		} else {
			if (orderBase.getSubRuleId() == null || orderBase.getSubRuleId() == 0) {
				throw new ServiceException("补贴限制规则ID不能为空");
			}
			// 根据商品所属市场，查询补贴限制规则
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ruleId", orderBase.getSubRuleId());
			List<SubLimitRuleDTO> subLimitRules = subLimitRuleService.getSubLimitRuleDetail(map);
			if (CollectionUtils.isEmpty(subLimitRules)) {
				throw new ServiceException("补贴限制规则不存在，ruleId=" + orderBase.getSubRuleId());
			}

			// 会员补贴结果
			MemberSubResultProcess subResultProcess = new MemberSubResultProcess();
			Map<String, MemberSubResultDTO> memberSubResultPool = subResultProcess.getMemberSubResultPool();
			orderBase.setMemberSubResultPool(memberSubResultPool);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ICheckRule.ORDER_BASE, orderBase);
			paramMap.put(ICheckRule.SUB_LIMIT_RULE, subLimitRules.get(0));
			Map<String, List<SubRangeLimitRuleDTO>> subLimitMap = getSubRangeLimitRuleMap(subLimitRules.get(0).getSubRangeLimitRules());;
			paramMap.put(ICheckRule.SUB_RANGE_LIMIT_RULE_MAP, subLimitMap);
			// 银行支付交易检查链
			subCheckRuleHelp.bankPayTransCheckRule(paramMap);
			
			// 用户维度检查链
			for (String memberType : memberTypes) {
				subCheckRuleHelp.userCheckRule(subResultProcess, paramMap, memberType);
			}
			// 订单维度检查链
			subCheckRuleHelp.orderCheckRule(subResultProcess, paramMap);
			
			// 市场补贴总额检查
			subCheckRuleHelp.subTotalAmountCheckRule(subResultProcess, paramMap);
			
			// 检查结果，最终处理
			for (String memberType : memberSubResultPool.keySet()) {
				MemberSubResultDTO subResult = memberSubResultPool.get(memberType);
				String subStatus = subResult.getSubStatus();
				// 白名单用户，只需要市场总额检查，设置为验证通过
				if (ESubStatus.CHECK_WHITE_LIST_PASS.getCode().equals(subStatus)) {
					subStatus = ESubStatus.CHECK_PASS.getCode();
				}
				setOrderSubInfo(orderBase, memberType, subStatus);
				// 记录审核日志
				if (StringUtils.isBlank(subResult.getAuditDesc())) {
					subResult.setAuditDesc(EMemberType.getDescByCode(memberType) + "系统审核通过");
				}
			}
		}

	}
	
	/**
	 * @Title: setOrderSubInfo
	 * @Description: TODO(设置补贴状态)
	 * @param orderBase 订单信息
	 * @param memberType 会员类型
	 * @param subStatus 补贴状态
	 * @throws ServiceException
	 */
	private void setOrderSubInfo(OrderBaseinfoDTO orderBase, String memberType, String subStatus) throws ServiceException {
		if (EMemberType.BUYER.getCode().equals(memberType)) {
			orderBase.setBuySubStatus(subStatus);
		} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
			orderBase.setSellSubStatus(subStatus);
		}
	}
	
	/**
	 * @Title: getSubRangeLimitRuleMap
	 * @Description: TODO(根据限制类型获取集合map)
	 * @param subRangeLimitRules
	 * @return
	 */
	private Map<String, List<SubRangeLimitRuleDTO>> getSubRangeLimitRuleMap(List<SubRangeLimitRuleDTO> subRangeLimitRules) {
		if (CollectionUtils.isEmpty(subRangeLimitRules)) {
			return null;
		}
		String limitType = null;
		List<SubRangeLimitRuleDTO> subRangeLimitRuleList = null;
		Map<String, List<SubRangeLimitRuleDTO>> subLimitMap = new HashMap<String, List<SubRangeLimitRuleDTO>>();
		for (SubRangeLimitRuleDTO subRangeLimitRule : subRangeLimitRules) {
			limitType = subRangeLimitRule.getLimitType();
			if (subLimitMap.containsKey(limitType)) {
				subRangeLimitRuleList = subLimitMap.get(limitType);
			} else {
				subRangeLimitRuleList = new ArrayList<SubRangeLimitRuleDTO>();
				subLimitMap.put(limitType, subRangeLimitRuleList);
			}
			subRangeLimitRuleList.add(subRangeLimitRule);
		}
		return subLimitMap;
	}

}
