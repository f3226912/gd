package com.gudeng.commerce.gd.api.enums;

public enum NpsPurchaseStatusEnum {
	DSH(1, "待审核"),
	SHTG(2, "审核通过"),
	YBH(3, "已驳回"),
	YHCX(4, "用户撤销"),
	YGB(5, "已关闭"),
	YJS(6, "已结束");
	private int code;
	
	private String name;

	private NpsPurchaseStatusEnum(int code, String name) {
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
		NpsPurchaseStatusEnum[] values = NpsPurchaseStatusEnum.values();
		for(NpsPurchaseStatusEnum val : values){
			if(val.getCode()==code){
				return val.getName();
			}
		}
		return null;
	}
}
