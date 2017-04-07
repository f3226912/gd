package com.gudeng.commerce.gd.admin.enums;

/**
 * 农商友用户类型
 * @author TerryZhang
 */
public enum NsyUserTypeEnum {

	DOWNSTREAM_WHOLESALERS(1,"下游批发商"), 
	FRESH_SUPERMARKET(2, "生鲜超市"),
	SCHOOL_CATEEN(3,"学校食堂"), 
	FOOD_PROCESSING_FACTORY(4, "食品加工工厂"),
	COMMUNITY_STORES(5,"社区门店"), 
	RESTAURANT(6, "餐厅"),
	VERTICAL_FRESH(7,"垂直生鲜"), 
	ORTHER(8, "其它");
	
	private final Integer nsyUserType;
	private final String nsyUserTypeName;
	
	NsyUserTypeEnum(Integer nsyUserType, String nsyUserTypeName){
		this.nsyUserType = nsyUserType;
		this.nsyUserTypeName = nsyUserTypeName;
	}
	
	public Integer getNsyUserType(){
		return this.nsyUserType;
	}
	
	public String getNsyUserTypeName(){
		return this.nsyUserTypeName;
	}
	
	public static String getNsyUserTypeNameByNsyUserType(Integer nsyUserType){
		for (NsyUserTypeEnum item :NsyUserTypeEnum.values()){
			if (item.getNsyUserType() == nsyUserType){
				return item.getNsyUserTypeName();
			}
		}
		return "";
	}
	
	public static NsyUserTypeEnum getByNsyUserType(Integer nsyUserType){
		for (NsyUserTypeEnum item :NsyUserTypeEnum.values()){
			if (item.getNsyUserType() == nsyUserType){
				return item;
			}
		}
		return null;
	}
}
