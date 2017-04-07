package com.gudeng.commerce.gd.order.enm;


/**
 * 清算类型(包含付方类型与收方类型)
 * @date 2016年11月7日
 */
public enum EOrderFeeUserType {

	NSY("nsy","农商友"),
	NPS("nps","农批商"),
	PLATFORM("platform","平台"),
	MARKET("market","市场"),
	WLGS("wlgs","物流公司");
	
	EOrderFeeUserType(String code,String desc){
	 this.code=code;
	 this.desc=desc;
	}
	
	private String code;
	private String desc;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static EOrderFeeUserType getEnumByCode(String code) {
		for (EOrderFeeUserType enm : EOrderFeeUserType.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
	}
	
	public static String getDescByCode(String code) {
		EOrderFeeUserType enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
}
