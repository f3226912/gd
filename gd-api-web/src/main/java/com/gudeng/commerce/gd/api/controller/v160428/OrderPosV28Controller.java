package com.gudeng.commerce.gd.api.controller.v160428;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.api.Constant.Order;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.CommonPageDTO;
import com.gudeng.commerce.gd.api.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderDetailDTO;
import com.gudeng.commerce.gd.api.dto.output.PosOrderListDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.SystemLogToolService;
import com.gudeng.commerce.gd.api.service.pos.PosOrderToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.SensitiveWordUtil;
import com.gudeng.commerce.gd.api.util.SpecialCharacterUtil;
import com.gudeng.commerce.gd.api.util.ParamsUtil;

@Controller
@RequestMapping("/pos28/order")
public class OrderPosV28Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderPosV28Controller.class);
	@Autowired
	private PosOrderToolService posOrderToolService;
	
	@Autowired
	private SystemLogToolService systemLogToolService;
	
	@Resource
	private GdProperties gdProperties;
	
	private boolean isSign;
	
	@PostConstruct
	private void init(){
		String sign = gdProperties.getProperties().getProperty("gateway.sign");
		if(StringUtils.equals(sign, "false")){
			isSign = false;
		} else {
			isSign = true;
		}
		
	}
	
	
	@RequestMapping("/add")
	public void addOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			logger.info("来自" + request.getRemoteAddr() + "的请求[isSign="+isSign+"]：" + request.getParameter("param"));
			String jsonStr = null;
			if(isSign){
				jsonStr = getParamDecodeStr(request);
			} else {
				String param = request.getParameter("param");
				jsonStr = SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(StringUtils.isBlank(param) ? "{}" : param));
			}
			
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			StatusCodeEnumWithInfo addResult = posOrderToolService.addOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map );
			}
			
			setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]POS卖家增加用户订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/addAno")
	public void addAnonymousOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			logger.info("来自" + request.getRemoteAddr() + "的请求[isSign="+isSign+"]：" + request.getParameter("param"));
			String jsonStr = null;
			if(isSign){
				jsonStr = getParamDecodeStr(request);
			} else {
				String param = request.getParameter("param");
				jsonStr = SensitiveWordUtil.stripXss(SpecialCharacterUtil.stripXss(StringUtils.isBlank(param) ? "{}" : param));
			}
			//String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			inputDTO.setOrderStatus(Integer.parseInt(Order.STATUS_NOT_PAY));
			
			StatusCodeEnumWithInfo addResult = posOrderToolService.addAnonymousOrder(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map );
				//成功，匿名下单记录日志
				systemLogToolService.insertOrderApiLog("匿名下单接口新增订单成功，订单号 ["+addResult.getObj()+"]", 
						addResult.getObj().toString());
			}
			
			setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]POS卖家增加匿名订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/lock")
	public void lockOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			ErrorCodeEnum lockResult = posOrderToolService.lock(inputDTO, true);
			if(ErrorCodeEnum.SUCCESS == lockResult){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				//成功后记录日志
				systemLogToolService.insertOrderApiLog("成功锁住定单,订单号["+inputDTO.getOrderNo()+"]", inputDTO.getOrderNo()+"");
			}else{
				setEncodeResult(result, lockResult, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]用户订单加锁异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/unlock")
	public void unlockOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			ErrorCodeEnum unlockResult = posOrderToolService.lock(inputDTO, false);
			if(ErrorCodeEnum.SUCCESS == unlockResult){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				//成功后记录日志
				systemLogToolService.insertOrderApiLog("成功解锁订单,订单号["+inputDTO.getOrderNo()+"]", inputDTO.getOrderNo()+"");
			}else{
				setEncodeResult(result, unlockResult, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]用户订单加锁异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/cancel")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			setEncodeResult(result, posOrderToolService.cancelOrder(inputDTO), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]取消用户订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/list")
	public void listOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			Long businessId = inputDTO.getBusinessId();
			//订单状态 1待付款  3已付款  8已关闭
			Integer status = inputDTO.getOrderStatus();
			if(businessId == null){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);
//			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status == null ? "1":status);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = posOrderToolService.getPosOrdersTotal(map);
			List<PosOrderListDTO> orderList = posOrderToolService.getPosOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询POS机订单列表异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/detail")
	public void detailOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			Long orderNo = inputDTO.getOrderNo();
			if(orderNo == null){
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			
			PosOrderDetailDTO orderDetail = posOrderToolService.getOrderByOrderNo(orderNo);
			if(orderDetail != null){
				result.setObject(orderDetail);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}
			
		} catch (Exception e) {
			logger.warn("[ERROR]获取POS机订单详情异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/update")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			Long orderNo = inputDTO.getOrderNo();
			Double newPayAmount = inputDTO.getPayAmount();
			Integer version = ParamsUtil.getIntFromString(inputDTO.getVersion());
			if(orderNo == null){
				setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
				return;
			}
			
			if(newPayAmount == null || newPayAmount.compareTo(0D) < 0){
				setResult(result, ErrorCodeEnum.ORDER_ILLEAGLE_CHANGED_PAYAMOUNT, request, response);
				return;
			}
			
			StatusCodeEnumWithInfo statusCode = posOrderToolService.update(orderNo, newPayAmount, version);
			if(statusCode.getStatusCodeEnum() == ErrorCodeEnum.SUCCESS){
				//记录日志
				systemLogToolService.insertOrderApiLog("修改订单支付金额成功,订单号["+orderNo+"]", orderNo+"");
			}
			
			result.setObject(statusCode.getObj());
			setEncodeResult(result, statusCode.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]修改POS机订单支付金额异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	/**
	 * 卖家确认现金收款
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/cashReceive")
	public void cashReceive(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderAppInputDTO inputDTO = (OrderAppInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderAppInputDTO.class);
			
			inputDTO.setPayType(null);
			ErrorCodeEnum confirmResult = posOrderToolService.confirmReceive(inputDTO);
			if(ErrorCodeEnum.SUCCESS == confirmResult){
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
				//记录收款成功日志
				systemLogToolService.insertOrderApiLog("现金收款成功，订单号 ["+inputDTO.getOrderNo()+"]", inputDTO.getOrderNo()+"");
			}else{
				setEncodeResult(result, confirmResult, request, response);
			}
		} catch (Exception e) {
			logger.warn("[ERROR]确认收款异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
