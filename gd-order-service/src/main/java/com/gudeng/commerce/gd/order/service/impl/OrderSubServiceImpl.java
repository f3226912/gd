package com.gudeng.commerce.gd.order.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.exception.ServiceException;
import com.gudeng.commerce.gd.order.business.sub.amount.OrderSubAmtInvoker;
import com.gudeng.commerce.gd.order.business.sub.amount.OrderSubInfoProcess;
import com.gudeng.commerce.gd.order.business.sub.rule.ICheckRule;
import com.gudeng.commerce.gd.order.business.sub.rule.OrderSubLimitRuleInvoker;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.dto.SubAuditDTO;
import com.gudeng.commerce.gd.order.dto.SubLimitRuleDTO;
import com.gudeng.commerce.gd.order.dto.SubPayRuleDTO;
import com.gudeng.commerce.gd.order.enm.EAccStatus;
import com.gudeng.commerce.gd.order.enm.EMemberType;
import com.gudeng.commerce.gd.order.enm.ESubAuditStatus;
import com.gudeng.commerce.gd.order.enm.ESubLimitStatus;
import com.gudeng.commerce.gd.order.enm.ESubStatus;
import com.gudeng.commerce.gd.order.enm.ESubType;
import com.gudeng.commerce.gd.order.entity.AuditLogEntity;
import com.gudeng.commerce.gd.order.service.AccInfoService;
import com.gudeng.commerce.gd.order.service.AuditLogService;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.OrderSubService;
import com.gudeng.commerce.gd.order.service.SubAuditService;
import com.gudeng.commerce.gd.order.service.SubLimitRuleService;
import com.gudeng.commerce.gd.order.service.SubPayRuleService;
import com.gudeng.commerce.gd.order.service.TaskService;

/**
 * @Description: TODO(订单补贴服务实现类)
 * @author mpan
 * @date 2015年12月4日 下午6:46:30
 */
public class OrderSubServiceImpl implements OrderSubService {
	
	@Autowired
	private OrderSubLimitRuleInvoker orderSubLimitRuleInvoker;
	
	@Autowired
	private OrderSubAmtInvoker orderSubAmtInvoker;
	
	@Autowired
	private OrderBaseinfoService orderBaseinfoService;
	
	@Autowired
	private SubPayRuleService subPayRuleService;
	
	@Autowired
	private OrderProductDetailService orderProductDetailService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private SubAuditService subAuditService;
	
	@Autowired
	private AccInfoService accInfoService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@Autowired
	private ICheckRule subTotalAmountCheckRule; // 市场补贴总额检查
	
	@Autowired
	private SubLimitRuleService subLimitRuleService;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderSubServiceImpl.class);
	
	private final static String HAS_SUB_YES = "1"; // 有补贴
	
	private final static String HAS_SUB_NO = "0"; // 无补贴
	
	private OrderBaseinfoDTO calcOrderSubAmt(Long orderNo) throws ServiceException {
		try {
			OrderBaseinfoDTO orderBase = orderBaseinfoService.queryOrderDetail(orderNo);
			if (orderBase == null) {
				throw new ServiceException("订单不存在");
			}
			updateSubActivtyInfo(orderBase);
			// 重新加载
			orderBase = orderBaseinfoService.queryOrderDetail(orderNo);
			orderSubAmtInvoker.invoke(orderBase);
			return orderBase;
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("计算补贴金额失败", e);
		}
	}
	
	/**
	 * @Title: updateActivtyInfo
	 * @Description: TODO(更新补贴活动信息)
	 * @param orderBase
	 * @throws ServiceException
	 */
	private void updateSubActivtyInfo(OrderBaseinfoDTO orderBase) throws ServiceException {
		if (orderBase.getSubRuleId() != null) {
			return;
		}
		List<String> memberTypes = new ArrayList<String>();
		if (orderBase.getMemberId() != null) {
			memberTypes.add(EMemberType.BUYER.getCode());
		}
		if (orderBase.getSellMemberId() != null) {
			memberTypes.add(EMemberType.WHOLESALER.getCode());
		}
		if (CollectionUtils.isEmpty(memberTypes)) {
			LOGGER.info("会员ID为空，不能更新活动规则信息");
			return;
		}
		Map<Integer, OrderProductDetailDTO> map = new HashMap<Integer, OrderProductDetailDTO>();
		// 查询商品对应的补贴发放规则
		for (String memberType : memberTypes) {
			for (OrderProductDetailDTO orderGoods : orderBase.getDetailList()) {
				SubPayRuleDTO querySubPayRule = new SubPayRuleDTO();
				querySubPayRule.setProductId(orderGoods.getProductId().longValue());
				querySubPayRule.setMarketId(orderBase.getMarketId());
				querySubPayRule.setMemberType(Integer.valueOf(memberType));
				SubPayRuleDTO subPayRule = subPayRuleService.querySubPayRuleByGoods(querySubPayRule);
				OrderProductDetailDTO updateOrderDetail = getOrderDetailByDetailId(map, orderGoods.getPreSalProductDetailId());
				if (subPayRule == null) {
					OrderSubInfoProcess.setOrderGoodsSubTipInfo(updateOrderDetail, memberType, EMemberType.getDescByCode(memberType) + "没有参与商品补贴活动");
					LOGGER.info(EMemberType.getDescByCode(memberType) + "没有参与商品补贴活动，产品ID=" + orderGoods.getProductId());
				} else {
					if (EMemberType.BUYER.getCode().equals(memberType)) {
						updateOrderDetail.setRuleId(subPayRule.getRuleId());
						if (StringUtils.isNotBlank(subPayRule.getSubType())) {
							updateOrderDetail.setSubType(subPayRule.getSubType());
							updateOrderDetail.setSubRule(ESubType.getDescByCode(subPayRule.getSubType()));
						}
					} else if (EMemberType.WHOLESALER.getCode().equals(memberType)) {
						updateOrderDetail.setSellRuleId(subPayRule.getRuleId());
						if (StringUtils.isNotBlank(subPayRule.getSubType())) {
							updateOrderDetail.setSellSubType(subPayRule.getSubType());
							updateOrderDetail.setSellSubRule(ESubType.getDescByCode(subPayRule.getSubType()));
						}
					}
				}
			}
		}
		if (!map.isEmpty()) {
			List<OrderProductDetailDTO> updateOrderDetails = Arrays.asList((OrderProductDetailDTO[]) map.values().toArray(new OrderProductDetailDTO[0]));
			orderProductDetailService.batchUpdate(updateOrderDetails);
		}
		// 根据商品所属市场，查询补贴限制规则
		Long ruleId = 0L;
		try {
			Map<String, Object> ruleMap = new HashMap<String, Object>();
			ruleMap.put("marketId", orderBase.getMarketId());
			ruleMap.put("timeRange", "Y");
			ruleMap.put("status", ESubLimitStatus.OPEN.getCode());
			List<SubLimitRuleDTO> subLimitRules = subLimitRuleService.getSubLimitRuleDetail(ruleMap);
			if (CollectionUtils.isEmpty(subLimitRules)) {
				LOGGER.info("没有满足条件的补贴限制规则，市场ID=" + orderBase.getMarketId());
				return;
			}
			ruleId = subLimitRules.get(0).getRuleId();
		} finally {
			OrderBaseinfoDTO updateOrderBase = new OrderBaseinfoDTO();
			updateOrderBase.setOrderNo(orderBase.getOrderNo());
			updateOrderBase.setSubRuleId(ruleId);
			try {
				orderBaseinfoService.updateByOrderNo(updateOrderBase);
			} catch (Exception e) {
				throw new ServiceException("更新补贴活动信息失败", e);
			}
		}
	}
	
	private OrderProductDetailDTO getOrderDetailByDetailId(Map<Integer, OrderProductDetailDTO> map, Integer detailId) {
		if (map.containsKey(detailId)) {
			return map.get(detailId);
		} else {
			OrderProductDetailDTO orderDetail = new OrderProductDetailDTO();
			orderDetail.setPreSalProductDetailId(detailId);
			map.put(detailId, orderDetail);
			return orderDetail;
		}
	}

	private void updateOrderSubInfo(OrderBaseinfoDTO orderBase) throws ServiceException {
		try {
			OrderBaseinfoDTO updateOrderBase = new OrderBaseinfoDTO();
			updateOrderBase.setOrderNo(orderBase.getOrderNo());
			updateOrderBase.setSubAmount(orderBase.getSubAmount());
			updateOrderBase.setSellSubAmount(orderBase.getSellSubAmount());
			updateOrderBase.setSuppSubAmount(orderBase.getSuppSubAmount());
			updateOrderBase.setBuySubStatus(orderBase.getBuySubStatus());
			updateOrderBase.setSellSubStatus(orderBase.getSellSubStatus());
			updateOrderBase.setSuppSubStatus(orderBase.getSuppSubStatus());
			orderBaseinfoService.updateByOrderNo(updateOrderBase);
			List<OrderProductDetailDTO> orderDetails = orderBase.getDetailList();
			if (CollectionUtils.isNotEmpty(orderDetails)) {
				OrderProductDetailDTO updateOrderDetail = null;
				List<OrderProductDetailDTO> updateOrderDetails = new ArrayList<OrderProductDetailDTO>();
				for (OrderProductDetailDTO orderDetail : orderDetails) {
					updateOrderDetail = new OrderProductDetailDTO();
					updateOrderDetail.setPreSalProductDetailId(orderDetail.getPreSalProductDetailId());
					updateOrderDetail.setSubAmount(orderDetail.getSubAmount());
					updateOrderDetail.setSellSubAmount(orderDetail.getSellSubAmount());
					updateOrderDetail.setSuppSubAmount(orderDetail.getSuppSubAmount());
					updateOrderDetail.setRuleId(orderDetail.getRuleId());
					updateOrderDetail.setSubType(orderDetail.getSubType());
					updateOrderDetail.setSubUnit(orderDetail.getSubUnit());
					updateOrderDetail.setSubRule(orderDetail.getSubRule());
					updateOrderDetail.setSubTipInfo(orderDetail.getSubTipInfo());
					updateOrderDetail.setSellRuleId(orderDetail.getSellRuleId());
					updateOrderDetail.setSellSubType(orderDetail.getSellSubType());
					updateOrderDetail.setSellSubUnit(orderDetail.getSellSubUnit());
					updateOrderDetail.setSellSubRule(orderDetail.getSellSubRule());
					updateOrderDetail.setSellSubTipInfo(orderDetail.getSellSubTipInfo());
					updateOrderDetail.setUpdateUserId("SYS");
					updateOrderDetails.add(updateOrderDetail);
				}
				orderProductDetailService.batchUpdate(updateOrderDetails);
			}
		} catch (Exception e) {
			throw new ServiceException("更新补贴信息更新", e);
		}
	}

	@Override
	public void handleOrderSubAmtToDB(Long orderNo) throws ServiceException {
		OrderBaseinfoDTO orderBase = calcOrderSubAmt(orderNo);
		updateOrderSubInfo(orderBase);
	}

	@Override
	public List<OrderProductDetailDTO> queryProductSubList(List<OrderProductDetailDTO> qryProductDetails) throws Exception {
		if (CollectionUtils.isEmpty(qryProductDetails)) {
			throw new Exception("产品ID不能为空");
		}
		List<Integer> productIdIn = new ArrayList<Integer>();
		for (OrderProductDetailDTO orderProductDetail : qryProductDetails) {
			if (orderProductDetail.getProductId() == null) {
				throw new Exception("产品ID不能为空");
			}
			productIdIn.add(orderProductDetail.getProductId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productIdIn", productIdIn);
		List<SubPayRuleDTO> subPayRules = subPayRuleService.queryGoodsSubPayRuleByMap(map);
		List<OrderProductDetailDTO> orderDetails = new ArrayList<OrderProductDetailDTO>();
		if (CollectionUtils.isNotEmpty(subPayRules)) {
			long productId = 0L;
			OrderProductDetailDTO orderDetail = null;
			for (SubPayRuleDTO subPayRule : subPayRules) {
				if (productId != subPayRule.getProductId()) {
					orderDetail = new OrderProductDetailDTO();
					orderDetails.add(orderDetail);
					orderDetail.setProductId(subPayRule.getProductId().intValue());
					orderDetail.setHasBuySub(HAS_SUB_NO);
					orderDetail.setHasSellSub(HAS_SUB_NO);
					orderDetail.setHasSuppSub(HAS_SUB_NO);
				}
				if (subPayRule.getRuleId() != null) {
					if (EMemberType.BUYER.getCode().equals(subPayRule.getMemberType().toString())) {
						orderDetail.setHasBuySub(HAS_SUB_YES);
					} else if (EMemberType.WHOLESALER.getCode().equals(subPayRule.getMemberType().toString())) {
						orderDetail.setHasSellSub(HAS_SUB_YES);
					} else if (EMemberType.SUPPLIER.getCode().equals(subPayRule.getMemberType().toString())) {
						orderDetail.setHasSuppSub(HAS_SUB_YES);
					}
				}
				productId = subPayRule.getProductId();
			}
		}
		boolean isExists = false;
		for (int proId : productIdIn) {
			isExists = false;
			for (OrderProductDetailDTO orderDetail : orderDetails) {
				if (orderDetail.getProductId() == proId) {
					isExists = true;
					break;
				}
			}
			if (!isExists) {
				OrderProductDetailDTO orderDetail = new OrderProductDetailDTO();
				orderDetail.setProductId(proId);
				orderDetail.setHasBuySub(HAS_SUB_NO);
				orderDetail.setHasSellSub(HAS_SUB_NO);
				orderDetail.setHasSuppSub(HAS_SUB_NO);
				orderDetails.add(orderDetail);
			}
		}
		return orderDetails;
	}
	
	@Override
	public void checkOrderSubRuleToDB(Long orderNo) throws ServiceException {
		OrderBaseinfoDTO orderBase = checkOrderSubRule(orderNo);
		updateOrderSubRuleCheckInfo(orderBase);
	}
	
	private OrderBaseinfoDTO checkOrderSubRule(Long orderNo) throws ServiceException {
		try {
			OrderBaseinfoDTO orderBase = orderBaseinfoService.queryOrderDetail(orderNo);
			if (orderBase == null) {
				throw new ServiceException("订单不存在");
			}
			orderSubLimitRuleInvoker.checkSubLimitRule(orderBase);
			return orderBase;
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("检查订单补贴规则失败", e);
		}
	}

	private void updateOrderSubRuleCheckInfo(OrderBaseinfoDTO orderBase) throws ServiceException {
		// 更新订单补贴信息
		try {
			OrderBaseinfoDTO updateOrderBase = new OrderBaseinfoDTO();
			updateOrderBase.setOrderNo(orderBase.getOrderNo());
			updateOrderBase.setSubAmount(orderBase.getSubAmount());
			updateOrderBase.setSellSubAmount(orderBase.getSellSubAmount());
			updateOrderBase.setSuppSubAmount(orderBase.getSuppSubAmount());
			updateOrderBase.setBuySubStatus(orderBase.getBuySubStatus());
			updateOrderBase.setSellSubStatus(orderBase.getSellSubStatus());
			updateOrderBase.setSuppSubStatus(orderBase.getSuppSubStatus());
			orderBaseinfoService.updateByOrderNo(updateOrderBase);
			List<OrderProductDetailDTO> orderDetails = orderBase.getDetailList();
			if (CollectionUtils.isNotEmpty(orderDetails)) {
				OrderProductDetailDTO updateOrderDetail = null;
				List<OrderProductDetailDTO> updateOrderDetails = new ArrayList<OrderProductDetailDTO>();
				for (OrderProductDetailDTO orderDetail : orderDetails) {
					updateOrderDetail = new OrderProductDetailDTO();
					updateOrderDetail.setPreSalProductDetailId(orderDetail.getPreSalProductDetailId());
					updateOrderDetail.setSubAmount(orderDetail.getSubAmount());
					updateOrderDetail.setSellSubAmount(orderDetail.getSellSubAmount());
					updateOrderDetail.setSuppSubAmount(orderDetail.getSuppSubAmount());
					updateOrderDetail.setUpdateUserId("SYS");
					updateOrderDetails.add(updateOrderDetail);
				}
				orderProductDetailService.batchUpdate(updateOrderDetails);
			}
		} catch (Exception e) {
			throw new ServiceException("更新订单补贴规则检查信息", e);
		}
		// 新增订单补贴审核记录
		SubAuditDTO subAudit = new SubAuditDTO();
		subAudit.setOrderNo(orderBase.getOrderNo());
		double subAmountSum = 0;
		if (orderBase.getSubAmount() != null) {
			subAmountSum += orderBase.getSubAmount(); 
		}
		if (orderBase.getSellSubAmount() != null) {
			subAmountSum += orderBase.getSellSubAmount(); 
		}
		subAudit.setSubAmount(subAmountSum);
		subAudit.setCreateUserId("SYS");
		boolean hasBuySub = ESubStatus.CHECK_PASS.getCode().equals(orderBase.getBuySubStatus());
		boolean hasSellSub = ESubStatus.CHECK_PASS.getCode().equals(orderBase.getSellSubStatus());
		// 待补贴
		if (hasBuySub || hasSellSub) {
			subAudit.setSubStatus(ESubAuditStatus.SYSTEM_PASS_SUB.getCode());
			subAudit.setSubComment("系统审核通过");
			if (hasBuySub) {
				updateAccAmount(orderBase.getMemberId(), orderBase.getSubAmount());
			}
			if (hasSellSub) {
				updateAccAmount(orderBase.getSellMemberId(), orderBase.getSellSubAmount());
			}
		} else { // 系统驳回
			subAudit.setSubStatus(ESubAuditStatus.SYSTEM_NOT_SUB.getCode());
			subAudit.setSubComment("系统驳回，审核不通过");
		}
		// 添加订单补贴审核记录
		subAuditService.addSubAudit(subAudit);
		
		// 补贴审核日志
		try {
			AuditLogEntity auditLog = new AuditLogEntity();
			auditLog.setType("1");
			auditLog.setOrderNo(orderBase.getOrderNo());
			auditLog.setAuditUserId("SYS");
			auditLog.setAuditUserName("系统");
			auditLog.setDescription(orderBase.getSubAuditDesc());
			auditLog.setCreateUserId("SYS");
			auditLog.setUpdateUserId("SYS");
			auditLogService.addAuditLog(auditLog);
		} catch (Exception e) {
			throw new ServiceException("补贴审核日志新增失败", e);
		}
	}
	
	private void updateAccAmount(Integer memberId, Double subAmountStay) throws ServiceException {
		// 更新钱包账户
		AccInfoDTO adto = accInfoService.getWalletIndex(Long.valueOf(memberId.toString()));
		if(adto == null){
			AccInfoDTO adtotemp = new AccInfoDTO();
			adtotemp.setMemberId(memberId);
			adtotemp.setAccStatus(EAccStatus.VALID.getCode());
			adtotemp.setSubAmountStay(subAmountStay);
			adtotemp.setCreateuserId("SYS");
			accInfoService.addAccInfo(adtotemp);
		} else {
			AccInfoDTO upAccInfo = new AccInfoDTO();
			upAccInfo.setMemberId(memberId);
			upAccInfo.setSubAmountStay(subAmountStay);
			upAccInfo.setUpdateuserId("SYS");
			accInfoService.updateMemAmount(upAccInfo);
		}
	}

	@Override
	public void subTotalAmountCheckRule(Long orderNo) throws ServiceException {
		try {
			OrderBaseinfoDTO orderBase = orderBaseinfoService.queryOrderDetail(orderNo);
			if (orderBase == null) {
				throw new ServiceException("订单不存在");
			}
			// 根据商品所属市场，查询补贴限制规则
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("marketId", orderBase.getMarketId());
			map.put("timeRange", "Y");
			map.put("status", ESubLimitStatus.OPEN.getCode());
			List<SubLimitRuleDTO> subLimitRules = subLimitRuleService.getSubLimitRuleList(map);
			if (CollectionUtils.isEmpty(subLimitRules)) {
				throw new ServiceException("没有满足条件的补贴限制规则");
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(ICheckRule.ORDER_BASE, orderBase);
			paramMap.put(ICheckRule.SUB_LIMIT_RULE, subLimitRules.get(0));
			subTotalAmountCheckRule.validation(paramMap);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				throw (ServiceException) e;
			}
			throw new ServiceException("市场总补贴额检查失败", e);
		}
	}
	
}
