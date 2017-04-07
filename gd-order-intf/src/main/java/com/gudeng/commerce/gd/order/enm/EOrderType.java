package com.gudeng.commerce.gd.order.enm;

/**
 * 订单类型
 * 
 * @date 2016年11月7日
 */
public enum EOrderType {

	NSY("1", "农商友采购订单"), NPS("2", "农批商采购订单"), NSY_6_1("3", "农商友6+1订单"), SERVICE("4", "服务订单");

	private EOrderType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static EOrderType getItemByCode(String code){
		for(EOrderType item :EOrderType.values()){
			if(item.code.equals(code)){
				return item;
			}
		}
		return null;
		
	}

	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
