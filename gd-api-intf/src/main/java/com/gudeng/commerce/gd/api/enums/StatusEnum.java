package com.gudeng.commerce.gd.api.enums;
/**
 * 订单状态词
 * 
 * */
public enum StatusEnum {

	DFYFK("0","待付预付款"),
	DFK("1","待付款"), 
	DYH("2", "待验货"),
	DFWK("3", "待付尾款"),
	WYQXDD("4", "我已取消订单"),
	MJYQXDD("5", "卖家已取消订单"),
	FPWLSB("6", "分配物流失败"),
	YFKZFCS("7", "订单超时"),
	YHBTG("8", "验货不通过"),
	DTK("9", "待退款"),
	YTK("10", "已退款"),
	YWC("11", "已完成"),
	FKCS("12", "付款超时"),
	YHCS("13","验货超时");
	
	private final String key;
	private final String word;
	
	StatusEnum(String key, String word){
		this.key = key;
		this.word = word;
	}
	
	public String getKey(){
		return this.key;
	}
	public String getWord(){
		return this.word;
	}
	
	public static String getValue(String key){
		for (StatusEnum item :StatusEnum.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getWord();
			}
		}
		return "";
	}
	
	public static String getKey(String word){
		for (StatusEnum item :StatusEnum.values()){
			if (item.getWord().equalsIgnoreCase(word)){
				return item.getKey();
			}
		}
		return "";
	}
	
	
}
