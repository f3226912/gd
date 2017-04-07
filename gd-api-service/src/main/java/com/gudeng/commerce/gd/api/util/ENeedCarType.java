package com.gudeng.commerce.gd.api.util;

public enum ENeedCarType {
	XXMB(0,"小型面包"),
	JB(1,"金杯"),
	XXPB(2,"小型平板"),
	ZXPB(3,"中型平板"),
	XXHX(4,"小型厢货"),
	DXHX(5,"大型厢货"),
	BX(6,"不限");
	
	public static ENeedCarType getELevelTyleByCode(Integer code){
		for (ENeedCarType  levelTyle: ENeedCarType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(Integer code){
		for (ENeedCarType  levelTyle: ENeedCarType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "不限";
	}
	private Integer code;
	private String value;
	private ENeedCarType(){
	}
	private ENeedCarType(Integer code,String value){
		this.code=code;
		this.value=value;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
