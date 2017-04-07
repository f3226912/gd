package com.gudeng.commerce.gd.api.controller.v160428;

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
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v28/order")
public class OrderAppV28Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV28Controller.class);
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@RequestMapping("/add")
	public void addOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO=(OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);//将json 字符串封装为对象 
			Long time1 = System.currentTimeMillis();
			StatusCodeEnumWithInfo addResult = orderTool2Service.addOrder(inputDTO);
			Long time2 = System.currentTimeMillis();
			System.out.println("Add order cost time: " + (time2 - time1));
			System.out.println("add order request param json: " + jsonStr);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]增加用户订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/cancel")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO=(OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);//将json 字符串封装为对象 
			ErrorCodeEnum cancelResult = orderTool2Service.cancelOrder(inputDTO);
			setEncodeResult(result, cancelResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]取消用户订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
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
			String jsonStr = getParamDecodeStr(request);
			inputDTO=(OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);//将json 字符串封装为对象 
			ErrorCodeEnum confirmResult =orderTool2Service.confirmReceive(inputDTO);
			setEncodeResult(result, confirmResult, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]确认收款异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/subList")
	public void subList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			//补贴状态 1已通过补贴 0未通过补贴
			Integer status = inputDTO.getSubStatus();
			
			if(status == null || (status != 0 && status != 1)){
				setEncodeResult(result, ErrorCodeEnum.ORDER_SUB_STATUS_ERROR, request, response);
				return;
			}
		
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
			
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = orderTool2Service.getSubListTotal(map);
			List<OrderAppReturnDTO> orderList = orderTool2Service.getSubList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询买家订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/buylist")
	public void buyerOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			Long memberId = inputDTO.getMemberId();
			//订单状态 1待付款  3已付款  8已关闭
			Integer status = inputDTO.getOrderStatus();
			if(memberId == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_BUYER_IS_NULL, request, response);
				return;
			}
	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status == null ? "1" : status);
			map.put("orderSource", "3"); //订单来源 3为pos来源
			map.put("hasCustomer", "0"); //1为补充订单
			map.put("orderType", "1");   //1:农商友采购订单
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = orderTool2Service.getOrdersTotal(map);
			List<OrderAppReturnDTO> orderList = orderTool2Service.getBuyerOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询买家订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/sellList")
	public void sellerOrderList(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			//订单状态  1待收款  3已收款 8已取消 9已关闭
			Integer status = inputDTO.getOrderStatus();
			Long businessId = inputDTO.getBusinessId();
			if(businessId == null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			if(status == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_STATUS_ERROR, request, response);
				return;
			}
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);	
//			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = orderTool2Service.getOrdersTotal(map);
			List<SellerOrderListDTO> orderList = orderTool2Service.getSellerOrderList(map);
				
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询卖家订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/detail")
	public void detailOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			Long orderNo = inputDTO.getOrderNo();
			Long memberId = inputDTO.getMemberId();
			if(orderNo == null){
				setEncodeResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
		
			OrderDetailAppDTO orderDetail = orderTool2Service.getOrderDetail(orderNo, memberId);
			if(orderDetail != null){
				result.setObject(orderDetail);
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}
			
		} catch (Exception e) {
			logger.warn("[ERROR]获取用户订单详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
