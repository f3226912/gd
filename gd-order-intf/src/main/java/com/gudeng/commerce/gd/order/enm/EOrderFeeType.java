package com.gudeng.commerce.gd.order.enm;

public enum EOrderFeeType {

	COMMISION("1", "佣金"), 
	SUBSIDY("2", "补贴"),
	PRE_PAY("3","预付款"),
	PENALTY_PAY("4","违约金");

	EOrderFeeType(String code, String desc) {
		this.code = code;
		this.desc = desc;
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

	public static EOrderFeeType getEnumByCode(String code) {
		for (EOrderFeeType enm : EOrderFeeType.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
	}
	
	public static String getDescByCode(String code) {
		EOrderFeeType enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
}
