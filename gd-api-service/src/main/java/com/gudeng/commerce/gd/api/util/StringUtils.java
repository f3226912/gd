/**
 * 文件：StringUtils.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Jan 10, 2012
 */
package com.gudeng.commerce.gd.api.util;

/**
 * 〈功能详细描述〉
 *
 * @author Nail.zhang
 * @version [V0.1, Jan 10, 2012]
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	
	/**
	 * 第一个字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstWordToLowerCase(String str) {
		return str.substring(0, 1).toLowerCase()
				+ str.substring(1, str.length());
	}
	
	public static String firstTwoWordToLowerCase(String str) {
		return str.substring(0, 2).toLowerCase()
				+ str.substring(2, str.length());
	}
	
	public static String firstWordToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}
	
	/**
	 * 
	 * @param str
	 * @param digit
	 * @param seperator
	 * @return
	 */
	public static String format(String str,int digit,String seperator){
		int len = str.length();
		int count  = len/digit;
		int remainder = len%digit;
		String returnStr="";
		for(int i=1;i<=count;i++){
			if(i==count && remainder==0)
				returnStr+= str.substring((i-1)*digit,i*digit);
			else
				returnStr+= str.substring((i-1)*digit,i*digit)+seperator;
		}
		returnStr +=str.substring(len-remainder);
		return returnStr; 
	}
	
	/**
	 * 格式化字符串 
	 * @param srcStr   {0}{1}{2}
	 * @param obj	   
	 * @return
	 */
	public  static String format(String srcStr,String... obj){
		
		String[] searchList = new String[obj.length];
		for (int i = 0; i < searchList.length; i++) {
			searchList[i]="{"+i+"}";
			
		}
		srcStr = StringUtils.replaceEach(srcStr, searchList, obj);
		return srcStr;
		
	}
	
	public static int length(String value) {  
		int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }  
}
