package com.gudeng.commerce.gd.api.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.gudeng.commerce.gd.api.controller.GDAPIBaseController;
import com.gudeng.commerce.gd.api.dto.input.BusinessInputDTO;
import com.gudeng.commerce.gd.api.dto.input.GdActivityQueryDTO;
import com.gudeng.commerce.gd.api.dto.input.GdOrderActivityApiQueryDTO;
import com.gudeng.commerce.gd.api.dto.output.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.order.OrderActivityToolService;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.JSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;
import com.gudeng.commerce.gd.order.dto.OrderProductDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderActRelationEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.promotion.dto.GdOrderActivityResultDTO;
import com.gudeng.commerce.gd.promotion.dto.GdProductActInfoDTO;


@Controller
@RequestMapping("/v1/ordrAct")
public class OrderActivityV1Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderActivityV1Controller.class);
	@Autowired
	private OrderActivityToolService orderActivityToolService;
	
	@RequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			GdOrderActivityApiQueryDTO inputDTO = (GdOrderActivityApiQueryDTO) GSONUtils.fromJsonToObject(jsonStr, GdOrderActivityApiQueryDTO.class);
			//订单号和商品信息二者必须有一个
			if(StringUtils.isBlank(inputDTO.getOrderNo()) 
					&& StringUtils.isBlank(inputDTO.getProductListStr())){
				setEncodeResult(result, ErrorCodeEnum.PARAM_IS_NULL, request, response);
				return;
			}
			
			StatusCodeEnumWithInfo addResult = orderActivityToolService.queryOrderActivity(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				result.setObject(addResult.getObj());
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.error("[ERROR]查询订单活动异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/batchQuery")
	public void batchQuery(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			JSONArray businessDetailsJsonArr = JSONUtils.parseArray(jsonStr);
			StatusCodeEnumWithInfo queryResult = orderActivityToolService.batchQueryOrderActivity(businessDetailsJsonArr);
			if(ErrorCodeEnum.SUCCESS == queryResult.getStatusCodeEnum()){
				result.setObject(queryResult.getObj());
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, queryResult.getStatusCodeEnum(), request, response);
			}
		}catch (Exception e) {
			logger.error("[ERROR]批量查询订单活动异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/querySameCity")
	public void querySameCity(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			BusinessInputDTO inputDTO = (BusinessInputDTO) GSONUtils.fromJsonToObject(jsonStr, BusinessInputDTO.class);
			//收货地城市和收货地城市id二者必须有一个
			if(StringUtils.isBlank(inputDTO.getReceiptCityId()) 
					&& StringUtils.isBlank(inputDTO.getReceiptCityName())){
				setEncodeResult(result, ErrorCodeEnum.ORDER_RECEIPT_CITY_IS_NULL, request, response);
				return;
			}
			
			if(StringUtils.isBlank(inputDTO.getBusinessIdListStr())){
				setEncodeResult(result, ErrorCodeEnum.BUSINESS_ID_IS_NULL, request, response);
				return;
			}
			
			StatusCodeEnumWithInfo addResult = orderActivityToolService.querySameCity(inputDTO);
			if(ErrorCodeEnum.SUCCESS == addResult.getStatusCodeEnum()){
				result.setObject(addResult.getObj());
				setEncodeResult(result, ErrorCodeEnum.SUCCESS, request, response);
			}else{
				setEncodeResult(result, addResult.getStatusCodeEnum(), request, response);
			}
		} catch (Exception e) {
			logger.error("[ERROR]查询订单活动异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
	@RequestMapping("/queryForClear")
	public void queryForClear(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		try {
			String jsonStr = getParamDecodeStr(request);
			GdActivityQueryDTO inputDTO = (GdActivityQueryDTO) GSONUtils.fromJsonToObject(jsonStr, GdActivityQueryDTO.class);
			List<OrderProductDetailDTO> entityList = orderActivityToolService.findOrderProductDetailByOrderNo(inputDTO.getOrderNo());
			List<GdProductActInfoDTO> productList = new ArrayList<>();
			for(OrderProductDetailEntity product : entityList){
				GdProductActInfoDTO paDto = new GdProductActInfoDTO();
				paDto.setPrice(product.getPrice());
				paDto.setProductAmount(product.getNeedToPayAmount());
				paDto.setProductId(product.getProductId());
				paDto.setQuantity(product.getPurQuantity());
				productList.add(paDto);
			}
			inputDTO.setProductList(productList);
			
			GdOrderActivityResultDTO orderActResult = orderActivityToolService.queryActivity(inputDTO);
			//获取订单商品活动信息
			Long orderNo = null;
			if(inputDTO.getOrderNo() != null){
				orderNo = new Long(inputDTO.getOrderNo());
			}		
			List<OrderActRelationEntity> orderActList = orderActivityToolService.getOrderRelationDetail(orderActResult, orderNo);
//			Map<String, Object> totalMap = new HashMap<String, Object>();
//			if (orderActList != null && orderActList.size() > 0) {
//				totalMap.put("orderActList", orderActList);
//			}
//			// 插入订单活动信息
//			List<OrderActRelationEntity> orderActList = (List<OrderActRelationEntity>) map.get("orderActList");
			if (orderActList != null && orderActList.size() > 0) {
				orderActivityToolService.saveOrderActRelations(orderActList);
			}
			
		} catch (Exception e) {
			logger.error("[ERROR]清分前查询佣金补贴异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
}
