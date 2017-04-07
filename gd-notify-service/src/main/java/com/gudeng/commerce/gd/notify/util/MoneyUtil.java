package com.gudeng.commerce.gd.notify.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * 金钱转换工具类
 * @author steven
 *
 */
public class MoneyUtil {
	public static String format(Double money) {
		if (null==money|| "".equals(money)) {
			return "";
		}
		
		
		String retString="";
		DecimalFormat df = new DecimalFormat("#,##0.00");
		if (money.equals(0.0)) {
			retString="面议";
		}else{
			retString= df.format(money).toString();
		}
		
//		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		 
//		if (money>=10000000) {
//			 money=money/10000000;
//			 BigDecimal bg = new BigDecimal(money);  
//	         money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
//			 retString= df.format(money).toString()+"千万";
//		}
//		else if (money>=10000) {
//			  money=money/10000;
//			  BigDecimal bg = new BigDecimal(money);  
//			   money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
//			  
//			  retString= df.format(money).toString() +"万";
//		}
//		else if (money.equals(0.0)) {
//			retString="面议";
//		}
//		else {
//			  BigDecimal bg = new BigDecimal(money);  
//			   money = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
//			   retString= df.format(money).toString(); 
//		}
		
		return retString;
	}
	
	/**
	 * 格式化金额
	 * @param money 金额
	 * @return
	 */
	public static String formatMoney(Double money) {
		return formatMoney(money, null);
	}
	
	/**
	 * 格式化到以分为单位
	 * @param money
	 * @return
	 */
	public static String formatToCentsUnit(Double money){
		BigDecimal bMoney = new BigDecimal(money+"");
		BigDecimal centMoney = bMoney.multiply(new BigDecimal(100)).setScale(0);
		return centMoney.toString();
	}
	
	/**
	 * 格式化金额
	 * @param money 金额
	 * @param df 金额格式
	 * @return
	 */
	public static String formatMoney(Double money, DecimalFormat df) {
		String retString="";
		
		if (null == money|| "".equals(money)) {
			return "0.00";
		}
		
		if(df == null){
			df = new DecimalFormat("#,##0.00");
		}
		
		retString= df.format(money).toString();
		if (money.equals(0.0)) {
			retString="面议";
		}else{
			retString= df.format(money).toString();
		}
		
		return retString;
	}
	
	public static void main(String[] args) {

		Double moneyDouble = 0.0;
		
		System.out.println(MoneyUtil.format(moneyDouble));
		System.out.println(MoneyUtil.formatMoney(moneyDouble));
		System.out.println(MoneyUtil.formatToCentsUnit(moneyDouble));
	}
}
