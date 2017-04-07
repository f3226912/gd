package com.gudeng.commerce.gd.api.util;

/**
 * 格式化工具类
 * @author TerryZhang
 */
public class FormatUtils {

	public static Long L_STOCK_COUNT = new Long(1000);
	
	public static Double D_STOCK_COUNT = new Double(1000);
	
	/**
	 * 格式化库存
	 * @param stock
	 * @param unitName
	 * @return
	 */
	public static String formattedStock(Long stock, String unitName) {
		String formattedStock = "";
		//库存大于等于1000
		if(L_STOCK_COUNT.compareTo(stock) <= 0){
			formattedStock = "大量";
		}else{
			formattedStock = stock + unitName;
		}
		
		return formattedStock;
	}
	
	/**
	 * 格式化库存
	 * @param stock
	 * @param unitName
	 * @return
	 */
	public static String formattedStock(Double stock, String unitName) {
		String formattedStock = "";
		//库存大于等于1000
		if(D_STOCK_COUNT.compareTo(stock) <= 0){
			formattedStock = "大量";
		}else{
			formattedStock = stock + unitName;
		}
		
		return formattedStock;
	}
	
	public static void main(String[] args) {
		Long count1 = new Long(1001);
		Double count2 = new Double(999);
		String unit = "吨";
		System.out.println(formattedStock(count1, unit));
		System.out.println(formattedStock(count2, unit));
	}
}
