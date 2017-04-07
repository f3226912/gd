package com.gudeng.commerce.gd.api.enums;

/**
 * 产品出货状态
 * @author TerryZhang
 */
public enum ProductDeliverStatusEnum {
	
	NOT_DELIVER(0,"未出货"), 
	IS_DELIVERING(1, "正出货"),
	ALREADY_DELIVERED(2, "已出货"),
	PARTIAL_DELIVERED(3, "部分出货"),
	NO_NEED_DELIVERED(4, "无出货信息");
	
	private final Integer key;
	private final String value;
	
	ProductDeliverStatusEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}
	
	public Integer getkey(){
		return this.key;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public static String getValueByKey(Integer key){
		for (ProductDeliverStatusEnum item :ProductDeliverStatusEnum.values()){
			if (item.getkey() == key){
				return item.getValue();
			}
		}
		return "";
	}
	
	public static ProductDeliverStatusEnum getByKey(Integer key){
		for (ProductDeliverStatusEnum item :ProductDeliverStatusEnum.values()){
			if (item.getkey() == key){
				return item;
			}
		}
		return null;
	}
}
