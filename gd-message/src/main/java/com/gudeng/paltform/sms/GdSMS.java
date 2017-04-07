package com.gudeng.paltform.sms;

import com.gudeng.paltform.sms.alidayu.AlidayuSMS;
import com.gudeng.paltform.sms.hxrt.HxrtSMS;
import com.gudeng.paltform.sms.montnets.MontnetsSMS;
import com.gudeng.paltform.sms.threeG.ThreeGSMS;

public class GdSMS {

	public GdSMS(){
		
	}
	
	
	/**
	 * 
	 * @param channel 短信通道：1:北京华兴软通 2:深圳朗讯通 3:深圳3G时代4阿里大鱼
	 * @param text
	 * @param mobiles
	 * @return
	 */
	public Boolean SendSms(String channel, String text, String...mobiles){
		//默认为阿里大鱼
		if(null == channel || "".equals(channel)){
			channel = "4";
		}
		if("1".equals(channel)){
			HxrtSMS hxrtSMS = new HxrtSMS();
			return hxrtSMS.SendSms(text, mobiles);
		//}
//		else if("2".equals(channel)){
////			LxtSMS lxtSMS = new LxtSMS();
//			return lxtSMS.SendSms(text, mobiles);
		}else if("3".equals(channel)){
			ThreeGSMS threeGSMS = new ThreeGSMS();
			return threeGSMS.SendSms(text, mobiles);
		}else if("4".equals(channel)){
			AlidayuSMS alidayu =new AlidayuSMS();
			return alidayu.SendSms(text, mobiles);
		}else if("5".equals(channel)){
			return MontnetsSMS.sendSms(text,mobiles);
		}
		return false;
	}
	
	public static void main(String[] args) {
		GdSMS gdSMS = new GdSMS();
		gdSMS.SendSms("1", "您好,谷登农批网手机验证码:111111【谷登科技】", "17722517718");
		
	}
}
