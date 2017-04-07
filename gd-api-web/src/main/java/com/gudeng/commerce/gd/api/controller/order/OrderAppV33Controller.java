package com.gudeng.commerce.gd.api.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.MiningOrderInputDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddBusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.input.OrderBatchAddInputDTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.order.OrderTool3Service;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.api.util.RestultEntity;

@Controller
@RequestMapping("/v33/order")
public class OrderAppV33Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV33Controller.class);
	@Autowired
	private OrderTool3Service<?> orderTool3Service;

	@RequestMapping("/batchAdd")
	public void batchAdd(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			OrderBatchAddInputDTO inputDTO = (OrderBatchAddInputDTO) GSONUtils.fromJsonToObject(jsonStr, OrderBatchAddInputDTO.class);
			Long time1 = System.currentTimeMillis();
			RestultEntity addResult = (RestultEntity) orderTool3Service.batchAddOrder(inputDTO);

			Long time2 = System.currentTimeMillis();
			System.out.println("batch add order request param : " + jsonStr);
			System.out.println("Batch add order cost time: " + (time2 - time1));

			setEncodeResult(addResult, request, response);
		} catch (Exception e) {
			logger.error("[ERROR] 增加订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}

	/**
	 * 现场采销的订单信息和订单详情添加
	 * @param request
	 * @param response
	 */
	@RequestMapping("/miningOrderAdd")
	public void miningOrderAdd(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			Long time1 = System.currentTimeMillis();
			RestultEntity addResult=(RestultEntity) orderTool3Service.miningSalesAddOrder(jsonStr);
			Long time2 = System.currentTimeMillis();
			System.out.println("miningOrde add order cost time: " + (time2 - time1));
			setEncodeResult(addResult, request, response);
		} catch (Exception e) {
			logger.error("[ERROR] 增加订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
