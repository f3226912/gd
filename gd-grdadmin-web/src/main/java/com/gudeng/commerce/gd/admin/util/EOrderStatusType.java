package com.gudeng.commerce.gd.admin.util;

/**
 *农速通订单状态枚举 
 * @author xiaojun
 */
public enum EOrderStatusType {
	DCJ(1,"待成交"),
	YCJ(2,"已成交"),
	YWC(3,"已完成"),
	WWC(4,"未完成"),
	YQX(5,"已取消");
	
	public static EOrderStatusType getELevelTyleByCode(Integer code){
		for (EOrderStatusType  levelTyle: EOrderStatusType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(Integer code){
		for (EOrderStatusType  levelTyle: EOrderStatusType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "";
	}
	private Integer code;
	private String value;
	private EOrderStatusType(){
	}
	private EOrderStatusType(Integer code,String value){
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
