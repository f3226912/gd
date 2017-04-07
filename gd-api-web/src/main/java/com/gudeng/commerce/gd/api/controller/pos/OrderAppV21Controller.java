package com.gudeng.commerce.gd.api.controller.pos;

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
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v21/order")
public class OrderAppV21Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV21Controller.class);
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@RequestMapping("/add")
	public void addOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			StatusCodeEnumWithInfo addResult = orderTool2Service.addOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map );
			}
				
			setResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]增加用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/cancel")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum cancelResult = orderTool2Service.cancelOrder(inputDTO);
			setResult(result, cancelResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]取消用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 卖家确认收款
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/confirmReceive")
	public void confirmReceive(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			ErrorCodeEnum confirmResult =orderTool2Service.confirmReceive(inputDTO);
			setResult(result, confirmResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]确认收款异常", e);
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
			int total = orderTool2Service.getSubListTotal(map);
			List<OrderAppReturnDTO> orderList = orderTool2Service.getSubList(map);
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
	
	@RequestMapping("/buylist")
	public void buyerOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long memberId = inputDTO.getMemberId();
		//订单状态 1待付款  3已付款  8已关闭
		Integer status = inputDTO.getOrderStatus();
		if(memberId == null){
			setResult(result, ErrorCodeEnum.ORDER_BUYER_IS_NULL, request, response);
			return;
		}
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status == null ? "1" : status);
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = orderTool2Service.getOrdersTotal(map);
			List<OrderAppReturnDTO> orderList = orderTool2Service.getBuyerOrderList(map);
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
	public void sellerOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		//订单状态  1待收款  3已收款 8已取消 9已关闭
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
			int total = orderTool2Service.getOrdersTotal(map);
			List<SellerOrderListDTO> orderList = orderTool2Service.getSellerOrderList(map);
				
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
		
		try {
			OrderDetailAppDTO orderDetail = orderTool2Service.getOrderDetail(orderNo, memberId);
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
