package com.gudeng.commerce.gd.api.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.gudeng.commerce.gd.api.Constant;
import com.gudeng.commerce.gd.api.enums.AlidayuEnum;
import com.gudeng.commerce.gd.api.enums.MessageTemplateEnum;
import com.gudeng.paltform.sms.GdSMS;

public class CommonUtils {

    /**
     * 
     * @param obj
     * @return
     * @throws Exception 
     */
    public static boolean isEmpty(Object obj)  {
    	if (obj instanceof String ){
    		return isEmpty((String)obj);
    	} else if (obj instanceof Object[]){
    		return isEmpty((Object[])obj);
    	} else if (obj instanceof Collection<?>){
    		return isEmpty((Collection<?>)obj);
    	} else if (obj instanceof Map<?,?>){
    		return isEmpty((Map<?,?>)obj);
    	}
    	return obj == null;
    }
    
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input==null || input.trim().isEmpty() ;
	}
	
	/**
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return array==null || array.length==0 ;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c==null || c.isEmpty() ;
	}
	
    /**
     * 
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map) {
    	return map==null || map.isEmpty() ;
    }
    
	/**
	 * 生成手机校验码
	 * @param onlyNumber 是否只是数字
	 * @param length 长度
	 * @return
	 */
	public static String genVerfiyCode(boolean onlyNumber, int length){
		String retStr = "";  
		String strTable = onlyNumber ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";  
		int len = strTable.length();  
		boolean bDone = true;  
		do {  
			retStr = "";  
			int count = 0;  
			for (int i = 0; i < length; i++) {  
				double dblR = Math.random() * len;  
				int intR = (int) Math.floor(dblR);  
				char c = strTable.charAt(intR);  
				if (('0' <= c) && (c <= '9')) {
					count++;  
				}  
				retStr += strTable.charAt(intR);  
			}  
			if (count >= 2) {  
				bDone = false;  
			}  
		} while (bDone);  
  
  		return retStr;
	}
	
	/**
	 * 发送短信
	 * @param content
	 * @param mobile
	 * @return
	 */
	public static boolean sendMsg(String channel,String content, String mobile){
		//LxtSMS sms = new LxtSMS();
		GdSMS sms = new GdSMS();
		boolean isSuccess = sms.SendSms(channel,content, mobile);
		return isSuccess;
	}
	
	/**
	 * 产生随机的六位数
	 * @return
	 */
	public static String getSixCode(){
		Random rad=new Random();
		
		String result  = rad.nextInt(1000000) +"";
		
		if(result.length()!=6){
			return getSixCode();
		}
		return result;
	}
	
	/**
	 * 产生随机的八位数
	 * @return
	 */
	public static String getEightCode(){
		Random rad=new Random();
		
		String result  = rad.nextInt(100000000) +"";
		
		if(result.length()!=8){
			return getEightCode();
		}
		return result;
	}
	
	/***
	 * 门岗app自动注册发送短信
	 * @param passwrod
	 * @param mobile
	 */
	public static void autoRegistSendMsg(String channel,String password, String mobile){
		String appDownloadUrl = UserSettingPropertyUtils.getEncrytphrase("gd.app.downloadUrl");
		String content = String.format(MessageTemplateEnum.AUTO_REGIST.getTemplate(), password, appDownloadUrl);
		CommonUtils.sendMsg(channel,content, mobile);
	}
	
	/**
	 * 验证手机是否正确
	 * @param mobile 手机号码
	 * @param ciphertext 加密后密文
	 * @return
	 */
	public static boolean verifyMobile(String mobile, String ciphertext) {
		String key = UserSettingPropertyUtils.getEncrytphrase("gd.encrypt.key");
		String newCiphertext = JavaMd5.getMD5(mobile + key).toUpperCase();
		if(ciphertext.equals(newCiphertext)){
			return true;
		}
		return false;
	}
	
	public static String generatePictureName(String fileName, int width){
		int index = fileName.lastIndexOf(".");
		String fileExt = fileName.substring(index);
		String newName = fileName.substring(0, index) + width + "_" + width + fileExt;
		return newName;
	}
	public static String generateSimpleFileName(String orgName){
		int index = orgName.lastIndexOf(File.separator);
		String fileName = "";
		if (index != -1){
			fileName = orgName.substring(index+1);
		}else {
			fileName = orgName;
		}
		return fileName;
	}
	public static boolean isNumber(String input){
		Pattern pattern = Pattern.compile("\\d{1,}");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
		
	}
	/**
	 * 
	 * @param messageType 消息类型 1:找回密码 2：WEB端注册验证码3：手机端注册验证码4商户添加用户短信通知
	 * @param param 参数
	 * @return
	 * @throws JSONException 
	 */
	public static String alidayuUtil(Integer messageType,String param) throws JSONException{
		JSONObject  alidayujson = new JSONObject();
		JSONObject alidayuParam = new JSONObject();
		if(messageType==Constant.Alidayu.MESSAGETYPE1){
			//模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE1.value());
			//短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME,AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.CODE, param);
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
		}
		if(messageType==Constant.Alidayu.MESSAGETYPE2){
		//模版id
		alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE2.value());
		//短信签名
		alidayujson.put(Constant.Alidayu.FREESIGNNAME,AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
		alidayuParam.put(Constant.Alidayu.CODE, param);
		alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
		}
		if(messageType==Constant.Alidayu.MESSAGETYPE3){
			//模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE3.value());
			//短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME,AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.CODE, param);
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
			}
		if(messageType==Constant.Alidayu.MESSAGETYPE4){
			//模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE4.value());
			//短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME,AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.PASSWORD, param);
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
			}
		if(messageType==Constant.Alidayu.MESSAGETYPE5){
			//模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE5.value());
			//短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME,AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.CODE, param);
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
			}
		return  alidayujson.toString();
	}

	public static void main(String[] args) {
		System.out.println(isNumber("32"));;
//		String verifycode =genVerfiyCode(true, 6);
//		String mobile = "17722517718";
//		System.out.println(CommonUtils.sendMsg("",MessageTemplateEnum.REGISTER.getTemplate().replace("{P}", verifycode), mobile));
	}
}
