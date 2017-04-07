package com.gudeng.commerce.gd.report.enm;

/**
 * 订单来源枚举类
 * 
 * @author  panmin
 */
public enum EOrderSource {

	/**
	 * 农批商APP
	 */
	SELL_ORDER("1", "农批商APP"),
	
	/**
	 * 农商友APP
	 */
	BUY_ORDER("2", "农商友APP"),
	
	/**
	 * POS机
	 */
	PAY_ORDER("3", "POS机"),
	
	/**
	 * 智能秤
	 */
	SINXIN("4", "智能秤");

	EOrderSource(String code, String desc) {
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
	public static EOrderSource getEnumByCode(String code) {
		for (EOrderSource enm : EOrderSource.values()) {
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
		EOrderSource enm = getEnumByCode(code);
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
