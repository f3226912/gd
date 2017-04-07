package com.gudeng.commerce.gd.api.enums;

public enum ActivityTypeEnum {

	CXH_ACT("1","产销汇"), 
	CX6_ACT("2","采销6+1"), 
	OTHER_ACT("0", "其他活动");
	
	private final String actType;
	private final String actTypeName;
	
	ActivityTypeEnum(String actType, String actTypeName){
		this.actType = actType;
		this.actTypeName = actTypeName;
	}

	public String getActTypeName() {
		return actTypeName;
	}

	public String getActType() {
		return actType;
	}
	
	public static String getActTypeNameByActType(String actType){
		for (ActivityTypeEnum item :ActivityTypeEnum.values()){
			if (item.getActType().equalsIgnoreCase(actType)){
				return item.getActTypeName();
			}
		}
		return "";
	}
	
	public static ActivityTypeEnum getActivityTypeEnumByActType(String actType){
		for (ActivityTypeEnum item :ActivityTypeEnum.values()){
			if (item.getActType().equalsIgnoreCase(actType)){
				return item;
			}
		}
		return null;
	}
}
