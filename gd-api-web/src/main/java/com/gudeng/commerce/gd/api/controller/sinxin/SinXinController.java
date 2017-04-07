package com.gudeng.commerce.gd.api.controller.sinxin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.sinxin.Result;
import com.gudeng.commerce.gd.api.enums.ENongResponseCodeEnum;
import com.gudeng.commerce.gd.api.service.PaySerialnumberToolService;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.service.sinxin.MemberInfoToolService;
import com.gudeng.commerce.gd.api.service.sinxin.OrderInfoToolService;
import com.gudeng.commerce.gd.api.service.sinxin.ProductInfoToolService;
import com.gudeng.commerce.gd.api.util.DateUtil;
import com.gudeng.commerce.gd.customer.dto.MemberSinxinDTO;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.OrderSinxinDTO;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.enm.EOrderStatus;
import com.gudeng.commerce.gd.supplier.dto.ProductSinxinDTO;
@Controller
@RequestMapping("/sinxin")
public class SinXinController extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(SinXinController.class);
	
	@Autowired
	private OrderTool2Service orderTool2Service;
	
	@Autowired
	private PaySerialnumberToolService PaySerialnumberToolService;

	@Autowired
	private OrderInfoToolService orderInfoToolService;

	@Autowired
	private MemberInfoToolService memberInfoToolService;
	
	@Autowired
	private ProductInfoToolService productInfoToolService;
	
	@RequestMapping(value = "/syncOrder", method = {RequestMethod.POST })
    @ResponseBody
	public void syncOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderSinxinDTO orderBaseDTO) {
		try {
			Long orderNo = orderInfoToolService.syncOrder(orderBaseDTO);
			Map<String, String> retMap = new HashMap<String, String>();
			retMap.put("orderNo", orderNo.toString());
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "订单同步成功", retMap);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/queryPayInfo", method = {RequestMethod.POST })
    @ResponseBody
	public void queryPayInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderSinxinDTO orderBaseDTO) {
		try {
			if (orderBaseDTO == null || orderBaseDTO.getOrderNo() == null) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "订单编号不能为空", null);
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderNo", orderBaseDTO.getOrderNo());
			List<OrderBaseinfoDTO> orderBases = orderTool2Service.getListByCondition(map);
			if (CollectionUtils.isEmpty(orderBases)) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "订单不存在", null);
				return;
			}
			List<PaySerialnumberDTO> payList = PaySerialnumberToolService.getListByCondition(map);
			PaySerialnumberDTO payResult = new PaySerialnumberDTO();
			payResult.setOrderNo(orderBaseDTO.getOrderNo());
			if (CollectionUtils.isEmpty(payList)) {
				payResult.setPayStatus("0");
				payResult.setRemark("未支付");
			} else {
				PaySerialnumberDTO pay = payList.get(0);
				payResult.setPayStatus(pay.getPayStatus());
				payResult.setRemark(pay.getRemark());
				payResult.setPayAmount(pay.getPayAmount());
				payResult.setPayTimeView(DateUtil.toString(pay.getPayTime(), DateUtil.DATE_FORMAT_DATETIME));
				payResult.setStatementId(pay.getStatementId());
			}
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "查询成功", payResult);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/queryMember", method = {RequestMethod.POST })
    @ResponseBody
	public void queryMember(HttpServletRequest request, HttpServletResponse response, @RequestBody MemberSinxinDTO queryDTO) {
		try {
			if (StringUtils.isBlank(queryDTO.getMacAddr())) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "秤mac不能为空", null);
				return;
			}
			List<MemberSinxinDTO> members = memberInfoToolService.queryMember(queryDTO);
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "查询成功", members);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/queryProduct", method = {RequestMethod.POST })
    @ResponseBody
	public void queryProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody ProductSinxinDTO queryDTO) {
		try {
			if (StringUtils.isBlank(queryDTO.getMacAddr())) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "秤mac不能为空", null);
				return;
			}
			List<ProductSinxinDTO> products = productInfoToolService.queryProduct(queryDTO);
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "查询成功", products);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/queryOrder", method = {RequestMethod.POST })
    @ResponseBody
	public void queryOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderSinxinDTO queryDTO) {
		try {
			if (queryDTO == null || CollectionUtils.isEmpty(queryDTO.getOrderNos())) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "订单编号不能为空", null);
				return;
			}
			List<OrderSinxinDTO> orderBases = orderInfoToolService.queryOrder(queryDTO);
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "查询成功", orderBases);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	@RequestMapping(value = "/updateOrder", method = {RequestMethod.POST })
    @ResponseBody
	public void updateOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody OrderSinxinDTO orderBaseDTO) {
		try {
			if (orderBaseDTO == null || orderBaseDTO.getOrderNo() == null) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "订单编号不能为空", null);
				return;
			}
			if (orderBaseDTO.getPayAmount() == null) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "用户支付金额不能为空", null);
				return;
			}
			List<OrderSinxinDTO> orderBases = orderInfoToolService.queryOrder(orderBaseDTO);
			if (CollectionUtils.isEmpty(orderBases)) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "订单不存在", null);
				return;
			}
			if (!EOrderStatus.WAIT_PAY.getCode().equals(orderBases.get(0).getOrderStatus())) {
				setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), "此订单不能修改支付金额", null);
				return;
			}
			orderInfoToolService.updateOrder(orderBaseDTO);
			setResponse(response, ENongResponseCodeEnum.PAY_SUCCESS.getResultCode(), "更新成功", null);
		} catch (Exception e) {
			setResponse(response, ENongResponseCodeEnum.OPERATION_FAIL.getResultCode(), e.getMessage(), null);
		}
	}
	
	/**
	 * 响应报文信息
	 * @param response
	 * @param msg
	 * @throws Exception
	 */
	private void setResponse(HttpServletResponse response, String resultCode, String msg, Object respObj) {
		try {
			Result result = new Result();
			result.setResultcode(resultCode);
			result.setResultmsg(msg);
			result.setDatajson(respObj);
			//签名
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
		} catch (Exception e) {
			logger.error("");
		}
	}
}
