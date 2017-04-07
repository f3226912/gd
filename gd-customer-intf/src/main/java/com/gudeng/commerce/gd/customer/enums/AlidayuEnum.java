package com.gudeng.commerce.gd.customer.enums;

public class AlidayuEnum {
	public static enum templateCode{
		
		TEMPLATECODE1("SMS_22475134"),//广告上线
		TEMPLATECODE2("SMS_23615004"),//注册成功金牌会员
		TEMPLATECODE3("SMS_22435154"),//浏览量短信提醒
		
		;
		private String _value;
		public String value()
	    {
	      return _value;
	    }
		private templateCode(String value){
			this._value=value;
		}
	}
public static enum FreeSignName{
		FREESIGNNAME1("谷登科技")//短信签名
		;
		private String _value;
		public String value()
	    {
	      return _value;
	    }
		private FreeSignName(String value){
			this._value=value;
		}
	}
}
