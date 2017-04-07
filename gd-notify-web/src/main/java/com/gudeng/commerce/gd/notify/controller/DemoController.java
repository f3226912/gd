package com.gudeng.commerce.gd.notify.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.gd.notify.dto.PosPayNotifyDto;
import com.gudeng.commerce.gd.notify.service.DemoToolService;
import com.gudeng.commerce.gd.notify.service.PosOrderToolService;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.web.controller.BaseController;

@Controller
@RequestMapping("demo")
public class DemoController extends BaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	public DemoToolService demoToolService;

	@Resource
	private PosOrderToolService posOrderToolService;
	
	/**
	 * demo
	 * @return
	 */
	@RequestMapping("")
	public String demo(HttpServletRequest request){
		return "demo/demo";
	}
	
	
	/**
	 * 分页查询
	 * @param request
	 * @return
	 */
	
	@RequestMapping("query")
	@ResponseBody
	public String query(HttpServletRequest request){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 设置查询参数
			map.put("id", "1");
			//记录数
			map.put("total", demoToolService.getTotal(map));
			//设定分页,排序
			//list
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * demo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("nsyCreate")
	@ResponseBody
	public String nsyCreate(PosPayNotifyDto dto,HttpServletRequest request) throws Exception{
		posOrderToolService.payByCard(dto);
		return "ok";
	}

}
