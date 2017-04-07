package com.gudeng.commerce.gd.api.util;

public enum ENeedTrunkCarType {
	XSHC(0,"厢式货车"),
	BC(1,"敞车"),
	LCC(2,"冷藏车"),
	BWC(3,"保温车"),
	QI(4,"其他"),
	GLC(5,"高栏车"),
	PBC(6,"平板车"),
	HXSC(7,"活鲜水车"),
	JZX(8,"集装箱"),
	BX(9,"不限");
	
	public static ENeedTrunkCarType getELevelTyleByCode(Integer code){
		for (ENeedTrunkCarType  levelTyle: ENeedTrunkCarType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(Integer code){
		for (ENeedTrunkCarType  levelTyle: ENeedTrunkCarType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "不限";
	}
	private Integer code;
	private String value;
	private ENeedTrunkCarType(){
	}
	private ENeedTrunkCarType(Integer code,String value){
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
