package com.gudeng.commerce.gd.order.enm;

/**
 * 支付子类型枚举类
 * 
 * @author  panmin
 */
public enum EPaySubType {

	/**
	 * 旺POS
	 */
	WANG_POS("1", "旺POS"),
	
	/**
	 * E农管家
	 */
	ENGJ("2", "E农管家"),
	
	NANING("3","南宁建行");

	EPaySubType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据编码获取枚举对象
	 * 
	 * @param code
	 *            枚举对象的编码
	 * @return 如果存在，返回对应枚举对象，否则返回null
	 */
	public static EPaySubType getEnumByCode(String code) {
		for (EPaySubType enm : EPaySubType.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
	}
	
	/**
	 * 根据编码获取枚举描述
	 */
	public static String getDescByCode(String code) {
		EPaySubType enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
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

}
