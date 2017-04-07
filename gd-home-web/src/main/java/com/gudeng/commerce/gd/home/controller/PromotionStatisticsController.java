package com.gudeng.commerce.gd.home.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gudeng.commerce.gd.customer.entity.PromotionStatisticsEntity;
import com.gudeng.commerce.gd.home.Constant;
import com.gudeng.commerce.gd.home.service.PromotionManageService;
import com.gudeng.commerce.gd.home.util.IpUtils;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;

@Controller
@RequestMapping("promotion")
public class PromotionStatisticsController {

	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(PromotionStatisticsController.class);
	
	@Autowired
	public PromotionManageService promotionManageService;
	
	@ResponseBody
	@RequestMapping("addPromotionStatistics")
	public String addPromotionStatistics(String sourceId, String type,  HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PromotionStatisticsEntity entity = new PromotionStatisticsEntity();
			entity.setSourceId(Long.valueOf(sourceId));
			entity.setType(Integer.valueOf(type));
			entity.setIp(IpUtils.getIpAddr(request));
			entity.setCreateTime(new Date());
			Long result = promotionManageService.addPromotionStatistics(entity);
			map.put("status", Constant.SUCCESS);
			map.put("message", result);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", Constant.FALIED);
		}
		return JSONObject.toJSONString(map);
	}
}
