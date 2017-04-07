package com.gudeng.commerce.gd.admin.util;

/**
 *用户角色枚举 
 * @author xiaojun
 */
public enum ELevelTyle {
	GDNP("1","谷登农批"),
	NST("2","农速通"),
	NSY("3","农商友"),
	CDGYS("4","产地供应商"),
	MG("5","门岗");
	
	public static ELevelTyle getELevelTyleByCode(String code){
		for (ELevelTyle  levelTyle: ELevelTyle.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(String code){
		for (ELevelTyle  levelTyle: ELevelTyle.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "";
	}
	private String code;
	private String value;
	private ELevelTyle(){
	}
	private ELevelTyle(String code,String value){
		this.code=code;
		this.value=value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
