package com.gudeng.commerce.gd.admin.util;

/**
 *重量单位枚举 
 * @author xiaojun
 */
public enum EHundredweightType {
	D(0l,"吨"),
	GJ(1l,"公斤");
	
	public static EHundredweightType getELevelTyleByCode(Long code){
		for (EHundredweightType  levelTyle: EHundredweightType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(Long code){
		for (EHundredweightType  levelTyle: EHundredweightType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "";
	}
	private Long code;
	private String value;
	private EHundredweightType(){
	}
	private EHundredweightType(Long code,String value){
		this.code=code;
		this.value=value;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
