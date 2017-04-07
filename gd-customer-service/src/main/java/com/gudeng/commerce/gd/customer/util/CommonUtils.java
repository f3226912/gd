package com.gudeng.commerce.gd.customer.util;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gudeng.commerce.gd.customer.enums.AlidayuEnum;
import com.gudeng.paltform.sms.GdSMS;

public class CommonUtils {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			return isEmpty((String) obj);
		} else if (obj instanceof Object[]) {
			return isEmpty((Object[]) obj);
		} else if (obj instanceof Collection<?>) {
			return isEmpty((Collection<?>) obj);
		} else if (obj instanceof Map<?, ?>) {
			return isEmpty((Map<?, ?>) obj);
		}
		return obj == null;
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isEmpty(String input) {
		return input == null || input.trim().isEmpty();
	}

	/**
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * 生成手机校验码
	 * 
	 * @param onlyNumber
	 *            是否只是数字
	 * @param length
	 *            长度
	 * @return
	 */
	public static String genVerfiyCode(boolean onlyNumber, int length) {
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
	 * 
	 * @param messageType
	 *            消息类型 1:广告上线 2:注册成功 3:达到浏览量
	 * @param param
	 *            参数内容
	 * @return
	 * @throws JSONException
	 */
	public static String alidayuUtil(Integer messageType, Map<String, Object> params) throws JSONException {
		JSONObject alidayujson = new JSONObject();
		JSONObject alidayuParam = new JSONObject();
		if (messageType == Constant.Alidayu.MESSAGETYPE1) { //广告上线
			// 模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE1.value());
			// 短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME, AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.ACCOUNT, params.get(Constant.Alidayu.ACCOUNT));
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
		} else if (messageType == Constant.Alidayu.MESSAGETYPE2) { //金牌会员注册成功
			// 模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE2.value());
			// 短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME, AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.ACCOUNT, params.get(Constant.Alidayu.ACCOUNT));
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
		} else if (messageType == Constant.Alidayu.MESSAGETYPE3) { //达到浏览量
			// 模版id
			alidayujson.put(Constant.Alidayu.TEMPLATECODE, AlidayuEnum.templateCode.TEMPLATECODE3.value());
			// 短信签名
			alidayujson.put(Constant.Alidayu.FREESIGNNAME, AlidayuEnum.FreeSignName.FREESIGNNAME1.value());
			alidayuParam.put(Constant.Alidayu.ACCOUNT, params.get(Constant.Alidayu.ACCOUNT));
			alidayuParam.put(Constant.Alidayu.COUNT, params.get(Constant.Alidayu.COUNT));
			alidayujson.put(Constant.Alidayu.PARAM, alidayuParam);
		}
		
		return alidayujson.toString();
	}

	/**
	 * 发送短信
	 * 
	 * @param content
	 * @param mobile
	 * @return
	 */
	public static boolean sendMsg(String channel, String content, String mobile) {
		// LxtSMS sms = new LxtSMS();
		GdSMS sms = new GdSMS();
		boolean isSuccess = sms.SendSms(channel, content, mobile);
		return isSuccess;
	}

	/**
	 * 产生随机的六位数
	 * 
	 * @return
	 */
	public static String getSixCode() {
		Random rad = new Random();

		String result = rad.nextInt(1000000) + "";

		if (result.length() != 6) {
			return getSixCode();
		}
		return result;
	}

	/**
	 * 产生随机的八位数
	 * 
	 * @return
	 */
	public static String getEightCode() {
		Random rad = new Random();

		String result = rad.nextInt(100000000) + "";

		if (result.length() != 8) {
			return getEightCode();
		}
		return result;
	}

	public static String generatePictureName(String fileName, int width) {
		int index = fileName.lastIndexOf(".");
		String fileExt = fileName.substring(index);
		String newName = fileName.substring(0, index) + width + "_" + width + fileExt;
		return newName;
	}

	public static String generateSimpleFileName(String orgName) {
		int index = orgName.lastIndexOf(File.separator);
		String fileName = "";
		if (index != -1) {
			fileName = orgName.substring(index + 1);
		} else {
			fileName = orgName;
		}
		return fileName;
	}

	public static boolean isNumber(String input) {
		Pattern pattern = Pattern.compile("\\d{1,}");
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();

	}

	public static void main(String[] args) {
		System.out.println(isNumber("32"));
		;
		// String verifycode =genVerfiyCode(true, 6);
		// String mobile = "17722517718";
		// System.out.println(CommonUtils.sendMsg("",MessageTemplateEnum.REGISTER.getTemplate().replace("{P}",
		// verifycode), mobile));
		// System.out.println(CommonUtils.sendMsg("4","{'templateCode':'SMS_13350131','freeSignName':'谷登科技','param':{'code':'987823'}}",
		// "13430135673"));
	}
}
