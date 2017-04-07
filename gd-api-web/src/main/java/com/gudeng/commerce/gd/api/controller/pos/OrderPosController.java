package com.gudeng.commerce.gd.api.controller.pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gudeng.commerce.gd.api.service.MemberToolService;
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
import com.gudeng.commerce.gd.api.service.pos.PosOrderToolService;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.ParamsUtil;

@Controller
@RequestMapping("/pos/order")
public class OrderPosController extends GDAPIBaseController {

	/**
	 * 记录日志
	 */
	private static Logger logger = LoggerFactory.getLogger(OrderPosController.class);
	@Autowired
	private PosOrderToolService posOrderToolService;
	@Autowired
	private MemberToolService memberToolService;

	@RequestMapping("/add")
	public void addOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			StatusCodeEnumWithInfo addResult = posOrderToolService.addOrder(inputDTO);
			if (ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map);
			}

			setResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]POS卖家增加用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/addAno")
	public void addAnonymousOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			inputDTO.setOrderStatus(Integer.parseInt(Order.STATUS_NOT_PAY));
			StatusCodeEnumWithInfo addResult = posOrderToolService.addAnonymousOrder(inputDTO);
			if (ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult.getObj().toString());
				result.setObject(map);
			}

			setResult(result, addResult.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]POS卖家增加匿名订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/lock")
	public void lockOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			setResult(result, posOrderToolService.lock(inputDTO, true), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]用户订单加锁异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/unlock")
	public void unlockOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			setResult(result, posOrderToolService.lock(inputDTO, false), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]用户订单加锁异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/cancel")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			setResult(result, posOrderToolService.cancelOrder(inputDTO), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]取消用户订单异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/list")
	public void listOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long businessId = inputDTO.getBusinessId();
		//订单状态 1待付款  3已付款  8已关闭
		Integer status = inputDTO.getOrderStatus();
		if (businessId == null) {
			setResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
			return;
		}

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", businessId);
//			map.put("marketId", inputDTO.getMarketId());
			map.put("orderStatus", status == null ? "1" : status);
			map.put("hasArrived", 1);//已送达
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = posOrderToolService.getPosOrdersTotal(map);
			List<PosOrderListDTO> orderList = posOrderToolService.getPosOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询POS机订单列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 输入买家手机号或者买家id查询订单列表
	 * 且订单已送达
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/list2")
	public void listOrder2(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long memberId = inputDTO.getMemberId();
		String mobile = inputDTO.getMobile();
		//订单状态 1待付款  3已付款  8已关闭
		Integer status = inputDTO.getOrderStatus();
		if (memberId == null && StringUtils.isEmpty(mobile)) {
			setResult(result, ErrorCodeEnum.ACCOUNT_IS_NULL, request, response);
			return;
		}
		/**
		 * 若没有传入买家id，则根据手机号查找memberid
		 */
		if (memberId == null) {
			try {
				memberId = memberToolService.getByMobile(mobile).getMemberId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (memberId == null) {
			setResult(result, ErrorCodeEnum.ACCOUNT_NOT_EXISTED, request, response);
			return;
		}
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("orderStatus", status == null ? "1" : status);
			map.put("hasArrived", 1);//已送达
			CommonPageDTO pageDTO = getPageInfo(request, map);
			int total = posOrderToolService.getPosOrdersTotal(map);
			List<PosOrderListDTO> orderList = posOrderToolService.getPosOrderList(map);
			//根据总数设置pageDTO信息
			pageDTO.setRecordCount(total);
			pageDTO.initiatePage(total);
			pageDTO.setRecordList(orderList);
			result.setObject(pageDTO);
			setResult(result, ErrorCodeEnum.SUCCESS, request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]查询POS机订单列表异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/detail")
	public void detailOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long orderNo = inputDTO.getOrderNo();
		if (orderNo == null) {
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}

		try {
			PosOrderDetailDTO orderDetail = posOrderToolService.getOrderByOrderNo(orderNo);
			if (orderDetail != null) {
				result.setObject(orderDetail);
				setResult(result, ErrorCodeEnum.SUCCESS, request, response);
			} else {
				setResult(result, ErrorCodeEnum.ORDER_NOT_EXISTED, request, response);
			}

		} catch (Exception e) {
			logger.warn("[ERROR]获取POS机订单详情异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	@RequestMapping("/update")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		Long orderNo = inputDTO.getOrderNo();
		Double newPayAmount = inputDTO.getPayAmount();
		Integer version = ParamsUtil.getIntFromString(inputDTO.getVersion());
		Double zero = new Double(0);
		if (orderNo == null) {
			setResult(result, ErrorCodeEnum.ORDER_ORDERNO_IS_NULL, request, response);
			return;
		}

		if (newPayAmount == null || newPayAmount.compareTo(zero) < 0) {
			setResult(result, ErrorCodeEnum.ORDER_ILLEAGLE_CHANGED_PAYAMOUNT, request, response);
			return;
		}

		try {
			StatusCodeEnumWithInfo statusCode = posOrderToolService.update(orderNo, newPayAmount, version);
			result.setObject(statusCode.getObj());
			setResult(result, statusCode.getStatusCodeEnum(), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]修改POS机订单支付金额异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 卖家确认现金收款
	 *
	 * @param request
	 * @param response
	 * @param inputDTO
	 */
	@RequestMapping("/cashReceive")
	public void cashReceive(HttpServletRequest request, HttpServletResponse response, OrderAppInputDTO inputDTO) {
		ObjectResult result = new ObjectResult();
		try {
			inputDTO.setPayType(null);
			setResult(result, posOrderToolService.confirmReceive(inputDTO), request, response);
		} catch (Exception e) {
			logger.warn("[ERROR]确认收款异常", e);
			setResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
