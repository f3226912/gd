package com.gudeng.commerce.gd.m.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gudeng.commerce.gd.m.controller.MBaseController;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.m.util.HttpClientUtil;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("v33")
public class ApiController extends MBaseController{
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(ApiController.class);
	
	
	@Autowired
	public GdProperties gdProperties;
	/**
	 * 调用api接口demo
	 * @author gcwu
	 * @param request
	 * @param response
	 */
	@RequestMapping("/order/miningOrderAdd")
	@ResponseBody
	public String getApi(HttpServletRequest request,HttpServletResponse response) {
		String url = gdProperties.getProperties().getProperty("gd_api_url")+"v33/order/miningOrderAdd";
		String reponseData=null;
		try {
			//requestData = Des3.encode(gson.toJson(requestData));
			String requestData =request.getParameter("param");
			logger.info("####################发送的字符串"+com.gudeng.commerce.gd.m.util.Des3Request.decode(requestData));
			Map<String, Object> map = new HashMap<>();
			map.put("param",requestData);
			reponseData=HttpClientUtil.doGet(url, map);
			logger.info("####################返回的字符串"+ com.gudeng.commerce.gd.m.util.Des3.decode(reponseData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reponseData;
	}

}
