package com.gudeng.commerce.gd.customer.enums;

public enum NpsOfferPriceStatusEnum {
	//'状态 1已报价、2已删除 3隐藏',
	YBJ(1, "已报价"),
	YSC(2, "已删除"),
	YC(3, "隐藏");
	private int code;
	
	private String name;

	private NpsOfferPriceStatusEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getNameByCode(int code){
		NpsOfferPriceStatusEnum[] values = NpsOfferPriceStatusEnum.values();
		for(NpsOfferPriceStatusEnum val : values){
			if(val.getCode()==code){
				return val.getName();
			}
		}
		return null;
	}
}
