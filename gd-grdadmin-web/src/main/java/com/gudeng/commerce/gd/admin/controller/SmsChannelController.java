package com.gudeng.commerce.gd.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;


@Controller
@RequestMapping("smsChannel")
public class SmsChannelController  extends AdminBaseController {
	/** 记录日志 */
	private static final GdLogger logger = GdLoggerFactory.getLogger(SmsChannelController.class);
	@Autowired
	private IGDBinaryRedisClient redisClient;
	
	@RequestMapping("select")
	public String selectSmsChannel(HttpServletRequest request) throws Exception{
		//取redis缓存,获取通道号
		//redisClient.set("GDSMS_CHANNEL","1");
		String channel = "";
		try{
			Object obj = redisClient.get("GDSMS_CHANNEL");
			System.out.println("redis channel:###############"+ obj);
			channel = obj==null?"":obj.toString();
			System.out.println("redis channel:###############"+ channel);
		}catch(Exception e){
			//处理redis服务器异常
			e.printStackTrace();
			logger.info("获取redis 消息通道出错!");
		}
		if("".equals(channel)){
			channel = "1";
		}
		putModel("channel",channel);
		return "smsChannel/smsChannel";
	}
	
	@RequestMapping("update")
	public String updateSmsChannel(HttpServletRequest request) throws Exception{
		String channel = request.getParameter("channel");
		redisClient.set("GDSMS_CHANNEL", channel);
		return selectSmsChannel(request);
	}
}
