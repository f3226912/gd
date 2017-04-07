package com.gudeng.commerce.gd.m.controller.bill;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.enums.ResponseCodeEnum;
import com.gudeng.commerce.gd.m.service.BillDetailToolService;

@Controller
@RequestMapping("billDetail")
public class BillDetailController extends MBaseController{
	
	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(BillDetailController.class);
	
	@Autowired
	private BillDetailToolService billDetailToolService;

	/**
	 * 显示账单列表页面
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 1农商友 2农批商 3供应商
	 * @return String 
	 */
	@RequestMapping("showBill")
	public String showBill(HttpServletRequest request, String account, String payTime, String channelType)
			throws Exception{
		logger.info("account=" + account + ",payTime=" + payTime + ",channelType=" + channelType);
		request.setAttribute("account", account);
		request.setAttribute("payTime", payTime);
		request.setAttribute("channelType", channelType);
		return "H5/bill/bills";
	}
	
	/**
	 * 显示账单统计页面
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 1农商友 2农批商 3供应商 
	 * @return String 
	 */
	@RequestMapping("showOrderReport")
	public String showOrderReport(HttpServletRequest request, String account, String payTime, String channelType)
			throws Exception{
		logger.info("account=" + account + ",payTime=" + payTime + ",channelType=" + channelType);
		request.setAttribute("account", account);
		request.setAttribute("payTime", payTime);
		request.setAttribute("channelType", channelType);
		return "H5/bill/order-report";
	}
	
	/**
	 * 按月份查询账单列表
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 1农商友 2农批商 3供应商
	 * @return String 
	 */
	@RequestMapping("queryBillDetailList")
	@ResponseBody
	public String queryBillDetailList(HttpServletRequest request, String account, String payTime, String channelType)
			throws Exception{
		try{
			logger.info("account=" + account + ",payTime=" + payTime + ",channelType=" + channelType);
			if(StringUtils.isBlank(account)){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			if(StringUtils.isBlank(payTime)){
				return getResponseJson(ResponseCodeEnum.PAYTIME_IS_NULL);
			}
			if(StringUtils.isBlank(channelType)){
				return getResponseJson(ResponseCodeEnum.CHANNELTYPE_IS_NULL);
			}
			return getResponseJson(billDetailToolService.queryBillDetailList(account, payTime, channelType));
		}catch(Exception e){
			logger.error("查询账单列表:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	/**
	 * 按月份查询采购金额
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 1农商友 2农批商 3供应商
	 * @return String 
	 */
	@RequestMapping("queryMonthAmountList")
	@ResponseBody
	public String queryMonthAmountList(HttpServletRequest request, String account, String payTime, String channelType)
			throws Exception{
		try{
			logger.info("account=" + account + ",payTime=" + payTime + ",channelType=" + channelType);
			if(StringUtils.isBlank(account)){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			if(StringUtils.isBlank(payTime)){
				return getResponseJson(ResponseCodeEnum.PAYTIME_IS_NULL);
			}
			if(StringUtils.isBlank(channelType)){
				return getResponseJson(ResponseCodeEnum.CHANNELTYPE_IS_NULL);
			}
			return getResponseJson(billDetailToolService.queryMonthAmountList(account, payTime, channelType));
		}catch(Exception e){
			logger.error("统计采购金额:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
	
	/**
	 * 按月份查询订单量
	 * 
	 * @param account 会员账号
	 * @param payTime 支付时间
	 * @param channelType 渠道类型 1农商友 2农批商 3供应商
	 * @return String 
	 */
	@RequestMapping("queryMonthOrderList")
	@ResponseBody
	public String queryMonthOrderList(HttpServletRequest request, String account, String payTime, String channelType)
			throws Exception{
		try{
			logger.info("account=" + account + ",payTime=" + payTime + ",channelType=" + channelType);
			if(StringUtils.isBlank(account)){
				return getResponseJson(ResponseCodeEnum.ACCOUNT_IS_NULL);
			}
			if(StringUtils.isBlank(payTime)){
				return getResponseJson(ResponseCodeEnum.PAYTIME_IS_NULL);
			}
			if(StringUtils.isBlank(channelType)){
				return getResponseJson(ResponseCodeEnum.CHANNELTYPE_IS_NULL);
			}
			return getResponseJson(billDetailToolService.queryMonthOrderList(account, payTime, channelType));
		}catch(Exception e){
			logger.error("统计订单量:", e);
			return getResponseJson(ResponseCodeEnum.OPERATION_FAIL);
		}
	}
}
