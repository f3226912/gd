package com.gudeng.commerce.gd.api.controller.v160526;

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
import com.gudeng.commerce.gd.api.dto.output.SellerOrderList2DTO;
import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.api.service.pos.OrderTool2Service;
import com.gudeng.commerce.gd.api.util.GSONUtils;
import com.gudeng.commerce.gd.api.util.ObjectResult;

@Controller
@RequestMapping("/v30/order")
public class OrderAppV30Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(OrderAppV30Controller.class);
	@Autowired
	private OrderTool2Service orderTool2Service;
	
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
			map.put("orderStatus", status);
			CommonPageDTO pageDTO = getPageInfoEncript(jsonStr, map);
			int total = orderTool2Service.getOrdersTotal(map);
			List<SellerOrderList2DTO> orderList = orderTool2Service.getSellerOrderList2(map);
				
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
}
