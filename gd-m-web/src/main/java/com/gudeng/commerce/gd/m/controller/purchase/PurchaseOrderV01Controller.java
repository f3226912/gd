package com.gudeng.commerce.gd.m.controller.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.commerce.gd.m.controller.act.GDAPIBaseController;
import com.gudeng.commerce.gd.m.dto.OrderAppInputDTO;
import com.gudeng.commerce.gd.m.dto.StatusCodeEnumWithInfo;
import com.gudeng.commerce.gd.m.enums.ErrorCodeEnum;
import com.gudeng.commerce.gd.m.service.PurchaseOrderToolService;
import com.gudeng.commerce.gd.m.util.ObjectResult;

/**
 * 采购订单controller类
 * 农批商<->供应商
 * @author TerryZhang
 */
@Controller
@RequestMapping("/purchOrder")
public class PurchaseOrderV01Controller extends GDAPIBaseController {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(PurchaseOrderV01Controller.class);
	@Autowired
	private PurchaseOrderToolService purchaseOrderToolService;
	
	/**
	 * 添加采购订单
	 * @param request
	 * @param response
	 */
	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		ObjectResult result = new ObjectResult();
		OrderAppInputDTO inputDTO = (OrderAppInputDTO) getDecryptedObject(request, OrderAppInputDTO.class);
		
		try {
			StatusCodeEnumWithInfo statusCodeInfo = purchaseOrderToolService.addPurchaseOrder(inputDTO);
			ErrorCodeEnum statusCode = statusCodeInfo.getStatusCodeEnum();
			if( ErrorCodeEnum.SUCCESS == statusCode){
				String[] addResult = ((String)statusCodeInfo.getObj()).split("#_#");
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", addResult[0]); 
				map.put("orderStatus", addResult[1]);
				map.put("prepayAmt", addResult[2]);
				result.setObject(map);
			}
				
			setEncodeResult(result, statusCode, request, response);
		}catch (Exception e) {
			logger.error("[ERROR]添加采购订单异常", e);
			setEncodeResult(result, ErrorCodeEnum.FAIL, request, response);
		}
	}
	
}
