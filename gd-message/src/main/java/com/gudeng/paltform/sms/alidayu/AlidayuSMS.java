package com.gudeng.paltform.sms.alidayu;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AlidayuSMS {
	//请求地址
	private final static String URL ="http://gw.api.taobao.com/router/rest";
	//APP证书
	private final static String APPKEY="23349219";
	private final static String SECRET="ba0ac234c46f110e20f2be5c6d368183";
	public Boolean SendSms(String content ,String...mobiles){
		try {
		JSONObject jsonContent = new JSONObject(content);  
		//模版id
		String templateCode=jsonContent.get(AlidayuUtil.TEMPLATECODE).toString();
		//短信签名
		String freeSignName = jsonContent.get(AlidayuUtil.FREESIGNNAME).toString();
		//短信模版
		String param = jsonContent.get(AlidayuUtil.PARAM).toString();
		//发送的手机号
		StringBuffer mob=new StringBuffer();
		String [] str = mobiles;
		for (int i = 0; i < str.length; i++) {
			if(i!=0){
				mob.append(",");
			}
			mob.append(str[i]);
		}
		
			TaobaoClient client = new DefaultTaobaoClient(URL, APPKEY, SECRET);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setSmsType("normal");
			req.setSmsFreeSignName(freeSignName);
			req.setSmsParamString(param);
			req.setRecNum(mob.toString());
			req.setSmsTemplateCode(templateCode);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println("#####阿里大鱼发送成功"+rsp.getBody());
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		}
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		AlidayuSMS sms = new AlidayuSMS();
		String json="{\"templateCode\":\"SMS_7811374\",\"freeSignName\":\"谷登科技\",\"param\":{\"code\":\"1234\"}}";
		sms.SendSms(json, "13544220327");
		

	}
}
