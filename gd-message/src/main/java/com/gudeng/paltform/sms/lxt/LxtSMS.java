//package com.gudeng.paltform.sms.lxt;
//
//import java.net.URL;
//
//import org.codehaus.xfire.client.Client;
//
//
//public class LxtSMS{
//
//	
//	public LxtSMS() {
//		
//	}
//	
//	/**
//	 * 发送文字短信
//	 * @param text 短信文字
//	 * @param mobiles 手机号码:单个手机号或者集合
//	 * @return
//	 */
//	public Boolean SendSms(String text , String...mobiles){
//		try {
//			text = new String(text.toString().getBytes("utf-8"));
//			StringBuffer phonelist = new StringBuffer();
//			for(String str : mobiles){
//				phonelist.append(str + ","); 
//			}
//			String mobileStr = phonelist.toString().substring(0, phonelist.length()-1);
//			System.out.println("mobileStr:"+mobileStr);
//			Client c = new Client(new URL(LxtConfig.url));
//			Object[] o=c.invoke("SendSms", new Object[]{
//					LxtConfig.username,
//					LxtConfig.password,
//					mobileStr,
//					text,
//					LxtConfig.Passagewayid,
//					LxtConfig.SendDatetime});
//			System.out.println("rtnCode:"+ o[0]);
//			if(null != o[0]){
//				String rtnCode = (String)o[0];
//				if(rtnCode.contains("-")){
//					return false;
//				}else{
//					return true;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//	
//	/**
//	 * 查询账户余额
//	 * @param text 短信文字
//	 * @param mobiles 手机号码:单个手机号或者集合
//	 * @return
//	 */
//	public String getBalance(){
//		try {
//			Client c = new Client(new URL(LxtConfig.url));
//			Object[] o=c.invoke("GetBalance", new Object[]{
//					LxtConfig.username,
//					LxtConfig.password,
//					LxtConfig.Passagewayid
//					});
//			System.out.println("rtnCode:"+ o[0]);
//			return (String)o[0];
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
//	
//	public static void main(String[] args) {
//		LxtSMS sms = new LxtSMS();
//		sms.getBalance();
//		sms.SendSms("你好,验证码:888888【谷登科技】", "18390922006");
//	}
//}
