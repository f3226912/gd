package com.gudeng.commerce.gd.api.enums;

public enum ClosedReasonEnum {

	BUYER_CANCLE("0","我已取消"),
	SELLER_CANCLE("1","卖家已取消"), 
	PAY_TIMEOUT("2", "付款超时"),
	LOGISTICS_FAILURE("3", "分配物流失败"),
	PREPAY_TIMEOUT("4", "预付款支付超时"),
	INSPECTION_FAILURE("5", "验货不通过"),
	YH_TIMEOUT("7", "验货超时"),
	REFUSED("6", "已拒收");
	
	private final String closeKey;
	private final String closeReason;
	
	ClosedReasonEnum(String closeKey, String closeReason){
		this.closeKey = closeKey;
		this.closeReason = closeReason;
	}
	
	public String getCloseKey(){
		return this.closeKey;
	}
	public String getCloseReason(){
		return this.closeReason;
	}
	
	public static String getValue(String closeKey){
		for (ClosedReasonEnum item :ClosedReasonEnum.values()){
			if (item.getCloseKey().equalsIgnoreCase(closeKey)){
				return item.getCloseReason();
			}
		}
		return "";
	}
	
	public static String getKey(String closeReason){
		for (ClosedReasonEnum item :ClosedReasonEnum.values()){
			if (item.getCloseReason().equalsIgnoreCase(closeReason)){
				return item.getCloseKey();
			}
		}
		return "";
	}
	
	
}
