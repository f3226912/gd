package com.gudeng.paltform.sms.hxrt;


public class HxrtSMS {
	
	/**
	 * 
	 * @param text
	 * @param mobiles
	 * @return
	 */
	public Boolean SendSms(String content , String...mobiles){
		try {
			//以下为所需的参数，测试时请修改,中文请先转为16进制再发送
			String mobileStr = "";
			for(String mobile : mobiles){
				mobileStr += mobile + ",";
			}
			if(!"".equals(mobileStr)){
				mobileStr = mobileStr.substring(0, mobileStr.length()-1);
			}
			//手机号码，多个手机号用半角逗号分开，最多1000个
	        String contentStr = HttpSend.paraTo16(content);//短信内容
	        String strSmsParam = "reg=" + HxrtConfig.strReg + "&pwd=" + HxrtConfig.strPwd + "&sourceadd=" + HxrtConfig.strSourceAdd + "&phone=" + mobileStr + "&content=" + contentStr;
	        
	        //发送短信
	         String strRes = HttpSend.postSend(HxrtConfig.strSmsUrl, strSmsParam);
	         System.out.println(strRes+":"+strRes.indexOf("result=0"));
	         if(strRes.indexOf("result=0") >= 0){
	        	 return true;
	         }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
        return false;
	}
	
	public static void main(String[] args) {
		HxrtSMS sms = new HxrtSMS();
		System.out.println(sms.SendSms("您好,谷登农批网手机验证码:1112211【谷登科技】", "17722517718"));
	}
}
