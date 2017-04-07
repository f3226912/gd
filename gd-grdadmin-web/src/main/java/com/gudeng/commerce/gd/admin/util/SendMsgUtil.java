package com.gudeng.commerce.gd.admin.util;

import com.gudeng.framework.sedis.client.IGDBinaryRedisClient;
import com.gudeng.paltform.sms.GdSMS;

public class SendMsgUtil {
	
	/**
	 * 农速通后台认证不通过，发送短信给推荐人
	 */
    public static  final  String CERTIFICATE_MSG ="农速通认证在%s被驳回，姓名:%s，手机号码:%s，公司名称:%s，驳回原因：%s。【谷登科技】"; 
	
    public static String getChannel(IGDBinaryRedisClient redisClient) {
		String channel = "";
		try {
			Object obj = redisClient.get("GDSMS_CHANNEL");
			System.out.println("redis channel:###############" + obj);
			channel = obj != null ?  obj.toString():"";
			System.out.println("redis channel:###############" + channel);
		} catch (Exception e) {
			// 处理redis服务器异常
			e.printStackTrace();
			System.out.println("获取redis 消息通道出错!");
		}
		return channel;
	}
	
	
	/**
	 * 发送短信
	 * @param content
	 * @param mobile
	 * @return
	 */
	public static boolean sendMsg(String channel,String content, String mobile){
		GdSMS sms = new GdSMS();
		boolean isSuccess = sms.SendSms(channel,content, mobile);
		return isSuccess;
	}
	
	public static void main(String[] args) {
		String msg =String.format("Hi, %s, goodluck%s", "jack"," today");
		System.err.println(msg);
	}

}
