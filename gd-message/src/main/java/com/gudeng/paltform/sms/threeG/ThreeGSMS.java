package com.gudeng.paltform.sms.threeG;

import java.io.UnsupportedEncodingException;
import java.net.*;

import com.gudeng.paltform.sms.hxrt.HttpSend;
import com.gudeng.paltform.sms.hxrt.HxrtConfig;

public class ThreeGSMS {
	
	public Boolean SendSms(String content , String...mobiles){
		try {
			String sn = ThreeGConfig.sn;
			String password = ThreeGConfig.password;
			Client client=new Client(sn,password);
			
			//短信发送	
	        content=URLEncoder.encode(content, "utf8");
	      //以下为所需的参数，测试时请修改,中文请先转为16进制再发送
			String mobileStr = "";
			for(String mobile : mobiles){
				mobileStr += mobile + ",";
			}
			if(!"".equals(mobileStr)){
				mobileStr = mobileStr.substring(0, mobileStr.length()-1);
			}
			
	        String result_mt = client.mdsmssend(mobileStr, content, "", "", "", "");
	        System.out.print(result_mt);
		
			
	        if(result_mt.indexOf("-") < 0){
	        	return true;
	        }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
        return false;
	}

	public static void main(String[] args) throws UnsupportedEncodingException
	{
		ThreeGSMS sms = new ThreeGSMS();
		sms.SendSms("你好,验证码:888888【谷登科技】", "17722517718");
		

	}

}
