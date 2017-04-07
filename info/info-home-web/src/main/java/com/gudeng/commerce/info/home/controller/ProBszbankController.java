package com.gudeng.commerce.info.home.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.home.service.ProBszbankToolService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("probszbank")
public class ProBszbankController extends AdminBaseController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ProBszbankController.class);
	
	@Autowired
	public ProBszbankToolService proBszbankToolService;
	
	/**
	 * 交易流水：日交易金额
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTradeByDay")
	@ResponseBody
	public String getTradeByDay(HttpServletRequest request){
		Map<Object, Object> map = new HashMap<>();
		Map<String, Object> parm = new HashMap<>();
		try {
			map.put("rows", proBszbankToolService.getTradeByDay(parm));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
	}
	
	/**
	 * 交易流水：日订单量
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOrderByDay")
	@ResponseBody
	public String getOrderByDay(HttpServletRequest request){
		Map<Object, Object> map = new HashMap<>();
		Map<String, Object> parm = new HashMap<>();
		try {
			map.put("rows", proBszbankToolService.getOrderByDay(parm));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
		return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
	}

}
