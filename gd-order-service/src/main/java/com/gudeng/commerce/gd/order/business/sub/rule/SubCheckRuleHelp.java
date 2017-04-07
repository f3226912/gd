package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.exception.WhiteListUserFoundException;
import com.gudeng.commerce.gd.order.dto.MemberSubResultDTO;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ESubStatus;

/**
 * @Description: TODO(补贴规则检查辅助)
 * @author mpan
 * @date 2015年12月20日 下午10:03:21
 */
public class SubCheckRuleHelp {

	@Autowired
	private ICheckRule orderCheckRule; // 订单维度检查链

	@Autowired
	private ICheckRule userCheckRule; // 用户维度检查链

	@Autowired
	private ICheckRule subTotalAmountCheckRule; // 市场补贴总额检查
	
	@Autowired
	private ICheckRule bankPayTransCheckRule; // 银行支付交易检查链

	private final static Logger LOGGER = LoggerFactory.getLogger(SubCheckRuleHelp.class);
	
	/**
	/**
	 * @Title: bankPayTransCheckRule
	 * @Description: TODO(银行支付交易检查链)
	 * @param subResultProcess 补贴结果处理
	 * @param paramMap 参数map
	 * @throws ServiceException
	 */
	public void bankPayTransCheckRule(Map<String, Object> paramMap) throws ServiceException {
		bankPayTransCheckRule.validation(paramMap);
	}

	/**
	 * @Title: userCheckRule
	 * @Description: TODO(用户检查链)
	 * @param subResultProcess 补贴结果处理
	 * @param paramMap 参数map
	 * @param memberType 会员类型
	 * @throws ServiceException
	 */
	public void userCheckRule(MemberSubResultProcess subResultProcess, Map<String, Object> paramMap, String memberType) throws ServiceException {
		try {
			paramMap.put(ICheckRule.MEMBER_TYPE, memberType);
			userCheckRule.validation(paramMap);
			subResultProcess.setMemberSubStatus(memberType, ESubStatus.CHECK_PASS.getCode());
		} catch (SubLimitRuleCheckException e) {
			LOGGER.info("补贴规则检查", e);
			subResultProcess.setMemberSubResult(memberType, ESubStatus.CHECK_NOT_PASS.getCode(), EMemberType.getDescByCode(memberType) + e.getMessage());
		} catch (WhiteListUserFoundException e) {
			LOGGER.info("白名单用户检索", e);
			subResultProcess.setMemberSubResult(memberType, ESubStatus.CHECK_WHITE_LIST_PASS.getCode(), EMemberType.getDescByCode(memberType) + e.getMessage());
		}
	}

	/**
	 * @Title: orderCheckRule
	 * @Description: TODO(订单维度检查链)
	 * @param subResultProcess 补贴结果处理
	 * @param paramMap 参数map
	 * @throws ServiceException
	 */
	public void orderCheckRule(MemberSubResultProcess subResultProcess, Map<String, Object> paramMap) throws ServiceException {
		Map<String, MemberSubResultDTO> memberSubResultPool = subResultProcess.getMemberSubResultPool();
		try {
			// 存在验证通过情况下，才进行这一步
			for (MemberSubResultDTO subResult : memberSubResultPool.values()) {
				if (ESubStatus.CHECK_PASS.getCode().equals(subResult.getSubStatus())) {
					orderCheckRule.validation(paramMap);
					break;
				}
			}
		} catch (SubLimitRuleCheckException e) {
			LOGGER.info("补贴规则检查异常", e);
			for (String memberType : memberSubResultPool.keySet()) {
				MemberSubResultDTO subResult = memberSubResultPool.get(memberType);
				if (ESubStatus.CHECK_PASS.getCode().equals(subResult.getSubStatus())) {
					subResultProcess.setMemberSubResult(memberType, ESubStatus.CHECK_NOT_PASS.getCode(), EMemberType.getDescByCode(memberType) + e.getMessage());
				}
			}
		}
	}

	/**
	 * @Title: subTotalAmountCheckRule
	 * @Description: TODO(市场补贴额检查)
	 * @param subResultProcess 补贴结果处理
	 * @param paramMap 参数map
	 * @throws ServiceException
	 */
	public void subTotalAmountCheckRule(MemberSubResultProcess subResultProcess, Map<String, Object> paramMap) throws ServiceException {
		Map<String, MemberSubResultDTO> memberSubResultPool = subResultProcess.getMemberSubResultPool();
		try {
			// 存在验证通过、白名单情况下，才进行这一步
			for (MemberSubResultDTO subResult : memberSubResultPool.values()) {
				String subStatus = subResult.getSubStatus();
				if (ESubStatus.CHECK_PASS.getCode().equals(subStatus) || ESubStatus.CHECK_WHITE_LIST_PASS.getCode().equals(subStatus)) {
					subTotalAmountCheckRule.validation(paramMap);
					break;
				}
			}
		} catch (SubLimitRuleCheckException e) {
			LOGGER.info("市场补贴额检查", e);
			for (String memberType : memberSubResultPool.keySet()) {
				MemberSubResultDTO subResult = memberSubResultPool.get(memberType);
				String subStatus = subResult.getSubStatus();
				if (ESubStatus.CHECK_PASS.getCode().equals(subStatus) || ESubStatus.CHECK_WHITE_LIST_PASS.getCode().equals(subStatus)) {
					subResultProcess.setMemberSubResult(memberType, ESubStatus.CHECK_NOT_PASS.getCode(), EMemberType.getDescByCode(memberType) + e.getMessage());
				}
			}
		}
	}

}
