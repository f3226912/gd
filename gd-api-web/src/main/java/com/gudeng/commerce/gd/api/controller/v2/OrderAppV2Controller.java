package com.gudeng.commerce.gd.api.controller.v2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppReturnDTO;
import com.gudeng.commerce.gd.api.dto.OrderDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.SellerOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.OrderToolService;
import com.gudeng.commerce.gd.api.service.PreSaleToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v2/order")
public class OrderAppV2Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV2Controller.class);
	@Autowired
	private OrderToolService orderToolService;
	@Autowired
	private PreSaleToolService preSaleToolService;
	
	@RequestMapping("/buyerAdd")
	public void buyerAddOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			StatusCodeEnumWithInfo addResult = orderToolService.addOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map );
			}
				
			setResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]买家增加用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/sellerAdd")
	public void sellerAddOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			StatusCodeEnumWithInfo addResult = preSaleToolService.addOrders(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				String infoArr[] = ((String)addResult.getObj()).split("#_#");
				Map<String, String> map = new HashMap<String, String>();
				map.put("qcCode", infoArr[0]);
				map.put("orderNo", infoArr[1]);
				result.setObject(map );
			}
			
			setResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]卖家增加用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/cancel")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum cancelResult =orderToolService.cancelOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == cancelResult){
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				cancelResult = preSaleToolService.cancelOrder(inputDTO);
				setResult(result, cancelResult, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]取消用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/confirmPay")
	public void confirmPayOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum confirmResult =orderToolService.confirmPay(inputDTO);
			setResult(result, confirmResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]确认支付订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 确认收款
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/confirmReceive")
	public void confirmReceive(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum confirmResult =orderToolService.confirmReceive(inputDTO);
			setResult(result, confirmResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]确认收款异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/uploadVoucherAgain")
	public void uploadVoucherAgain(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum confirmResult =orderToolService.uploadVoucherAgain(inputDTO);
			setResult(result, confirmResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]再次上传凭证异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/confirm")
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		
		try {
			StatusCodeEnumWithInfo confirmResult = preSaleToolService.confirmOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == confirmResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", confirmResult.getObj().toString());
				result.setObject(map);
			}
			
			setResult(result, confirmResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]用户确认订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	@RequestMapping("/subList")
	public void subList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		//补贴状态 1已通过补贴 0未通过补贴
		Integer status = inputDTO.getSubStatus();
		
		if(status == null || (status != 0 && status != 1)){
			setResult(result, ErrorCodeEnum.ORDER_SUB_STATUS_ERROR, request, response);
			return;
		}
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", inputDTO.getMemberId());
			map.put("marketId", inputDTO.getMarketId());
			map.put("businessId", inputDTO.getBusinessId());
			map.put("subStatus", status);
			if(1 == status){
				//增加是否买家标志 1是 0否， 过滤掉已补贴中补贴为0的订单
				int isBuyer = 1;
				if(inputDTO.getBusinessId() != null){
					isBuyer = 0;
				}
				map.put("isBuyer", isBuyer);
			}
			
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = orderToolService.getSubListTotal(map);
			List<OrderAppReturnDTO> orderList = orderToolService.getSubList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询买家订单列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/list")
	public void buyerOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long memberId = inputDTO.getMemberId();
		//订单状态 1待付款  3已付款  10已完成
		Integer status = inputDTO.getOrderStatus();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.ORDER_BUYER_IS_NULL, request, response);
			return;
		}
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status == null ? "12":status);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = orderToolService.getOrdersTotal(map);
			List<OrderAppReturnDTO> orderList = orderToolService.getBuyerOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询买家订单列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/sellList")
	public void sellOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		//订单状态  1待收款  3已收款 5待确认
		Integer status = inputDTO.getOrderStatus();
		Long businessId = inputDTO.getBusinessId();
		if(businessId == null){
			setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}
		
		if(status == null){
			setResult(result, ErrorCodeEnum.ORDER_STATUS_ERROR, request, response);
			return;
		}
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);	
//			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = 0;
			List<SellerOrderListDTO> orderList = null;
			if(5 == status){   //卖家未确认订单要在预售表查
				map.put("orderStatus", 1);
				total = preSaleToolService.getOrdersTotal(map);
				orderList = preSaleToolService.getOrderList(map);
			}else{
				if(3 == status){ //卖家已收款包括买家已付款和已完成
					map.put("orderStatus", 11);
					total = orderToolService.getOrdersTotal(map);
					orderList = orderToolService.getSellerOrderList(map);
				}else{
					total = orderToolService.getOrdersTotal(map);
					orderList = orderToolService.getSellerOrderList(map);
				}
				
			}
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询卖家订单列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/detail")
	public void detailOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long orderNo = inputDTO.getOrderNo();
		Long memberId = inputDTO.getMemberId();
		if(orderNo == null){
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}
		
		if(memberId == null){
			setResult(result, ErrorCodeEnum.ORDER_MEMBERID_IS_NULL, request, response);
			return;
		}
		
		try {
			OrderDetailAppDTO orderDetail = orderToolService.getOrderDetail(orderNo, memberId);
			if(orderDetail == null){
				orderDetail = preSaleToolService.getOrderDetail(orderNo, memberId, inputDTO.getQcCode());
			}
			if(orderDetail != null){
				result.setObject(orderDetail);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}
			
		} catch (Exception e) {
			logger.warn("[ERROR]获取用户订单详情异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
