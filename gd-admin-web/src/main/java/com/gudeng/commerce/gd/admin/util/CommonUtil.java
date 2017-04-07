package com.gudeng.commerce.gd.admin.util;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class CommonUtil {

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
	public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }
	public static String getStartOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_DATETIME);
	}
	public static String getEndOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_DATETIME);
	}
	
	
	/**
	 * @Description formatNumber 数字格式化，将数值大于10000的值转化为以万为单位，保留两位小数，最后以为小数四舍五入，123456.56=12.35万
	 * @param price
	 * @return
	 * @CreationDate 2015年11月3日 下午12:20:44
	 * @Author lidong(dli@cnagri-products.com)
	*/
	public static String formatNumber(Double number) {
		String numberString = null;
		if (number==null) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		if (number < 10000) {
			numberString = "" + df.format(number);
		} else {
			number = number / 10000.0;
			numberString = df.format(number)+"万";
		}

		return numberString;
	}

	/**
	 * 将url参数转换成map
	 * @param param aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}
	
	/**
	 * post请求
	 * @param url url地址
	 * @param jsonParam 参数
	 * @param noNeedResponse 不需要返回结果
	 * @return
	 */
	public static JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		JSONObject jsonResult = null;
		HttpPost method = new HttpPost(url);
		try {
			if (null != jsonParam) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				String str = "";
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
					if (noNeedResponse) {
						return null;
					}
					/** 把json字符串转换成json对象 **/
					jsonResult = JSONObject.parseObject(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonResult;
	}
	
}

