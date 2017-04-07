package com.gudeng.commerce.gd.api.enums;

/**
 * 物流状态词
 * 
 * */

public enum NstStatusEnum {

	FPWLGSSB("0","分配物流公司失败"),
	DYH("1","待验货"), 
	YHBTG("2","验货不通过"), 
	YGB("3", "已关闭"),
	YFH("4", "已发货"),
	YSD("5", "已送达"),
	YJS("6", "已拒收");
	

	private final String key;
	private final String word;
	
	NstStatusEnum(String key, String word){
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
		for (NstStatusEnum item :NstStatusEnum.values()){
			if (item.getKey().equalsIgnoreCase(key)){
				return item.getWord();
			}
		}
		return "";
	}
	public static String getKey(String word){
		for (NstStatusEnum item :NstStatusEnum.values()){
			if (item.getWord().equalsIgnoreCase(word)){
				return item.getKey();
			}
		}
		return "";
	}
	
}
