package com.gudeng.paltform.sms.hxrt;

public class HxrtConfig {
	//以下为所需的参数，测试时请修改,中文请先转为16进制再发送
	public static String strReg = "101100-WEB-HUAX-737440";   //注册号（由华兴软通提供）
	public static String strPwd = "IBKAKMTL";                 //密码（由华兴软通提供）
	public static String strSourceAdd = "";                   //子通道号，可为空（预留参数一般为空）
    
    
	public static String strMobile = "13391750223";            //手机号，不可为空
	public static String strRegPhone = "01065688262";             //座机，不可为空
    public static String strFax = "01065685318";               //传真，不可为空
    public static String strEmail = "hxrt@stongnet.com";       //电子邮件，不可为空
    public static String strPostcode = "100080";               //邮编，不可为空
  
    
    //以下参数为服务器URL,以及发到服务器的参数，不用修改
    public static String strRegUrl = "http://www.stongnet.com/sdkhttp/reg.aspx"; //注册
    public static String strBalanceUrl = "http://www.stongnet.com/sdkhttp/getbalance.aspx"; //查询余额
    public static String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx"; //发送短信
    public static String strSchSmsUrl = "http://www.stongnet.com/sdkhttp/sendschsms.aspx"; //定时发送
    public static String strStatusUrl = "http://www.stongnet.com/sdkhttp/getmtreport.aspx";
    public static String strUpPwdUrl = "http://www.stongnet.com/sdkhttp/uptpwd.aspx";
}
