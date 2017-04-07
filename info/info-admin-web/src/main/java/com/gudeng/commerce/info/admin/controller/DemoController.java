package com.gudeng.commerce.info.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gudeng.commerce.info.admin.service.DemoToolService;
import com.gudeng.commerce.info.customer.dto.DictDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("demo")
public class DemoController extends AdminBaseController
{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	public DemoToolService demoToolService;

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
			setCommParameters(request, map);
			//list
			List<DictDTO> list = demoToolService.getByCondition(map);
			map.put("rows", list);//rows键 存放每页记录 list  
			return JSONObject.toJSONString(map,SerializerFeature.WriteDateUseDateFormat);
		} catch (Exception e) {
			
		}
		return null;
	}

}
