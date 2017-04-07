package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.SubLimitRuleCheckException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.SubAmountDTO;
import com.gudeng.commerce.gd.order.dto.SubAmountTipConfDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.TaskDTO;
import com.gudeng.commerce.gd.order.enm.EHasSubBalance;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.service.SubAmountService;
import com.gudeng.commerce.gd.order.service.SubHelpService;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;
import com.gudeng.commerce.gd.order.service.TaskService;
import com.gudeng.commerce.gd.order.util.FreeMarkerUtil;

/**
 * @Description: TODO(补贴总额限制)
 * @author mpan
 * @date 2015年12月5日 下午5:17:28
 */
public class SubTotalAmountCheckRuleImpl implements ICheckRule, ICommonCheckRule {
	
	/**
	 * 持有后继的校验对象
	 */
	private ICheckRule checkRule;
	
	@Autowired
	private SubHelpService subHelpService;
	
	@Autowired
	private SubAmountService subAmountService;
	
	@Autowired
	private SubLimitRuleService subLimitRuleService;
	
	@Autowired
	private TaskService taskService;
	
	private static final String SEND_STATUS_YES = "1"; // 发送状态 1已发送
	
	private static final String SEND_STATUS_NO = "0"; // 发送状态 0未发送
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SubTotalAmountCheckRuleImpl.class);

	@Override
	public void checkRule(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		SubLimitRuleDTO subLimitRule = (SubLimitRuleDTO) paramMap.get(ICheckRule.SUB_LIMIT_RULE);
		Double allAmount = subHelpService.getAllSubAmountByAll(orderBase.getMarketId().longValue(), subLimitRule.getTimeStart(), subLimitRule.getTimeEnd());
		long allAmountFen = (long) (allAmount * 100);
		long subAmountFen = allAmountFen;
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getBuySubStatus()) && orderBase.getSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSubAmount() * 100);
		}
		// 用户维度检查链不通过时，跳过
		if (!ESubStatus.CHECK_NOT_PASS.getCode().equals(orderBase.getSellSubStatus()) && orderBase.getSellSubAmount() != null) {
			subAmountFen += (long) (orderBase.getSellSubAmount() * 100);
		}
		long limitAmountFen = (long) (subLimitRule.getSubAmount() * 100);
		boolean flag = subAmountFen > limitAmountFen;
		triggerSubAmount(orderBase, subLimitRule.getContacts(), subAmountFen, limitAmountFen);
		if (flag) {
			// 需求确认，市场总额超限，补贴审核驳回
			throw new SubLimitRuleCheckException("市场总补贴额度超过限制>>" + (subAmountFen / 100.0) + ">" + subLimitRule.getSubAmount());
		}
	}

	@Override
	public void validation(Map<String, Object> paramMap) throws ServiceException {
		checkRule(paramMap);
		if (checkRule != null) {
			checkRule.validation(paramMap);
		}
	}
	
	private void triggerSubAmount(OrderBaseinfoDTO orderBase, List<String> contacts, long subAmountFen, long limitAmountFen) throws ServiceException {
		if (subAmountFen >= limitAmountFen) {
			try {
				SubAmountDTO subAmount = new SubAmountDTO();
				subAmount.setMarketId(orderBase.getMarketId());
				subAmount.setHasSubBalance(EHasSubBalance.NO.getCode());
				subAmount.setUpdateUserId("SYS");
				subAmountService.updateSubAmount(subAmount);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("ruleId", orderBase.getSubRuleId());
				map.put("userId","SystemTask");			
				map.put("marketId",orderBase.getMarketId());	
				subLimitRuleService.updateSubLimitRuleStatus(map);
			} catch (Exception e) {
				LOGGER.error("修改是否有补贴额标志失败", e);
			}

			
		}
		if (CollectionUtils.isEmpty(contacts)) {
			LOGGER.info("紧急联系人为空");
			return;
		}
		SubAmountTipConfDTO qryTipConf = new SubAmountTipConfDTO();
		qryTipConf.setMarketId(orderBase.getMarketId());
		qryTipConf.setSendStatus(SEND_STATUS_NO);
		List<SubAmountTipConfDTO> tipConfs = subLimitRuleService.getSubAmountTipConfList(qryTipConf);
		if (CollectionUtils.isEmpty(tipConfs)) {
			LOGGER.info("没有设置市场补贴额使用量配置");
			return;
		}
		for (SubAmountTipConfDTO tipConf : tipConfs) {
			if (SEND_STATUS_YES.equals(tipConf.getSendStatus())) {
				continue;
			}
			// 超过限定阀值，发送邮件通知紧急联系人
			if (subAmountFen * 1000 / limitAmountFen  > tipConf.getTipVal()) {
				SubAmountTipConfDTO upTipConf = new SubAmountTipConfDTO();
				upTipConf.setId(tipConf.getId());
				upTipConf.setSendStatus(SEND_STATUS_YES);
				upTipConf.setUpdateUserId("SYS");
				subLimitRuleService.updateSubAmountTipConf(upTipConf);
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("tipConf", tipConf);
				String sendContent = FreeMarkerUtil.genContent(param, "subAmountUseTipMail.ftl");
				TaskDTO taskInfo = TaskDTO.getSendEmailTask("市场补贴额使用量提醒", contacts, sendContent);
				taskService.addTask(taskInfo);
				break;
			}
		}
	}

	public void setCheckRule(ICheckRule checkRule) {
		this.checkRule = checkRule;
	}

}
