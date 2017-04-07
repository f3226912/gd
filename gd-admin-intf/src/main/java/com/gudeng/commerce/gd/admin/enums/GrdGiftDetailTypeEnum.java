package com.gudeng.commerce.gd.admin.enums;

public enum GrdGiftDetailTypeEnum {
	GIFT_DETAIL("1","礼品明细"), 
	ORDER_DETAIL("2", "订单明细"),
	NP_REGISTER("3","农批注册所发放礼品"), 
	NST_REGISTER("4", "首次邀请注册"), //农速通注册所发放礼品
	RELEASE_RESOURCE("5","发布货源"), 
	MESSAGE_ORDER("6", "信息订单"),
	GOODS_ORDER("7","货运订单"), 
	OTHER("8", "其它");
	
	private final String grdType;
	private final String grdTypeName;
	private GrdGiftDetailTypeEnum(String grdType, String grdTypeName){
		this.grdType=grdType;
		this.grdTypeName=grdTypeName;
	}
	public String getGrdType() {
		return grdType;
	}
	public String getGrdTypeName() {
		return grdTypeName;
	}
	
	public static String getTypeNameByType(String grdType){
		for (GrdGiftDetailTypeEnum item :GrdGiftDetailTypeEnum.values()){
			if (item.getGrdType().equals(grdType)){
				return item.getGrdTypeName();
			}
		}
		return "";
	}
}
