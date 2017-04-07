package com.gudeng.commerce.gd.order.business.sub.rule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.exception.ServiceTaskLockTimeException;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.enm.EBillUseStatus;
import com.gudeng.commerce.gd.order.enm.EPayType;
import com.gudeng.commerce.gd.order.enm.EUpPayFlag;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;
import com.gudeng.commerce.gd.order.service.AuditLogService;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderBillService;

/**
 * @Description: TODO(银行支付交易检查规则实现类)
 * @author mpan
 * @date 2015年12月22日 下午2:31:07
 */
public class BankPayTransCheckRuleImpl implements ICheckRule, ICommonCheckRule {
	
	/**
	 * 持有后继的校验对象
	 */
	private ICheckRule checkRule;
	
	@Autowired
	private OrderBaseinfoService orderBaseinfoService;
	
	@Autowired
	private OrderBillService orderBillService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BankPayTransCheckRuleImpl.class);

	@Override
	public void checkRule(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		List<PaySerialnumberDTO> payments = orderBase.getPayments();
		if (CollectionUtils.isEmpty(payments)) {
			throw new ServiceTaskLockTimeException("缺少订单支付信息");
		}
		String transNos = null;
		Double tradeAmount = null;
		for (PaySerialnumberDTO payment : payments) {
			if (EPayType.OFFLINE_CARD.getCode().equals(payment.getPayType())) {
				transNos = payment.getStatementId();
				tradeAmount = payment.getTradeAmount();
				break;
			}
		}
		if (StringUtils.isBlank(transNos)) {
			throw new ServiceTaskLockTimeException("银行支付流水为空");
		}
		String[] transNoArray = transNos.split(",");
		List<String> sysRefeNos = Arrays.asList(transNoArray);
		// 验证银行支付流水
		boolean flag = false;
		String message = null;
		OrderBillDTO queryOrderBill = new OrderBillDTO();
		queryOrderBill.setSysRefeNos(sysRefeNos);
//		queryOrderBill.setUseStatus(EBillUseStatus.UNUSE.getCode());
		List<OrderBillDTO> orderBills = orderBillService.queryOrderBill(queryOrderBill);
		OrderBaseinfoDTO upOrderBase = new OrderBaseinfoDTO();
		upOrderBase.setOrderNo(orderBase.getOrderNo());
		if (CollectionUtils.isEmpty(orderBills) || orderBills.size() < transNoArray.length) {
			// 找不到对应支付流水时，修改更新支付流水标志 1能改
			message = "银行交易流水检索不存在";
			upOrderBase.setUpPayFlag(EUpPayFlag.YES.getCode());
			flag = true;
		} else {
			boolean useFlag = false;
			double tradeMoney = 0;
			for (OrderBillDTO orderBill : orderBills) {
				if (EBillUseStatus.USEED.getCode().equals(orderBill.getUseStatus())) {
					useFlag = true;
					message = orderBill.getSysRefeNo() + "银行交易流水已使用，不能重复使用";
					break;
				}
				tradeMoney += orderBill.getTradeMoney();
			}
			if (useFlag) {
				upOrderBase.setUpPayFlag(EUpPayFlag.YES.getCode());
				flag = true;
			} else if (tradeMoney < tradeAmount) {
				message = "银行交易金额小于系统交易金额";
				upOrderBase.setUpPayFlag(EUpPayFlag.YES.getCode());
				flag = true;
			} else {
				// 找到对应支付流水时，修改更新支付流水标志为 0不能改
				upOrderBase.setUpPayFlag(EUpPayFlag.NO.getCode());
				OrderBillDTO upOrderBill = new OrderBillDTO();
				upOrderBill.setUpdateUserId("SYS");
				upOrderBill.setUseStatus(EBillUseStatus.USEED.getCode());
				upOrderBill.setSysRefeNos(sysRefeNos);
				orderBillService.updateOrderBill(upOrderBill);
			}
		}
		try {
			orderBaseinfoService.updateByOrderNo(upOrderBase);
		} catch (Exception e) {
			LOGGER.error("修改订单更新支付流水标志失败", e);
		}
		if (flag) {
			// 订单审核日志
			try {
				AuditLogEntity auditLog = new AuditLogEntity();
				auditLog.setType("3");
				auditLog.setOrderNo(orderBase.getOrderNo());
				auditLog.setAuditUserId("SYS");
				auditLog.setAuditUserName("系统");
				auditLog.setDescription(message);
				auditLog.setCreateUserId("SYS");
				auditLog.setUpdateUserId("SYS");
				auditLogService.addAuditLog(auditLog);
			} catch (Exception e) {
				throw new ServiceException("订单审核日志新增失败", e);
			}
			throw new ServiceTaskLockTimeException(message);
		}
	}

	@Override
	public void validation(Map<String, Object> paramMap) throws ServiceException {
		OrderBaseinfoDTO orderBase = (OrderBaseinfoDTO) paramMap.get(ICheckRule.ORDER_BASE);
		if (EPayType.OFFLINE_CARD.getCode().equals(orderBase.getPayType()) || EPayType.ACC_BALANCE_AND_OFFLINE_CARD.getCode().equals(orderBase.getPayType())) {
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
