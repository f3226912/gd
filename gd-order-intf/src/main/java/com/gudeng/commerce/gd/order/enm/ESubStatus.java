package com.gudeng.commerce.gd.order.enm;


/**
 * 补贴状态枚举类
 * 
 * @author  panmin
 */
public enum ESubStatus {
	
	/**
	 * 无补贴
	 */
	NOT_SUB("0", "无补贴"),

	/**
	 * 已计算补贴
	 */
	SUBED("11", "已计算补贴"),
	
	/**
	 * 待出岗计算补贴
	 */
	WAIT_OUT_CAR("10", "待出岗计算补贴"),
	
	/**
	 * 已计算补贴
	 */
	CHECK_PASS("21", "验证通过"),
	
	/**
	 * 已计算补贴
	 */
	CHECK_NOT_PASS("20", "验证不通过"),
	
	/**
	 * 白名单（临时使用）
	 */
	CHECK_WHITE_LIST_PASS("22", "白名单");

	ESubStatus(String code, String desc) {
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
	public static ESubStatus getEnumByCode(String code) {
		for (ESubStatus enm : ESubStatus.values()) {
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
		ESubStatus enm = getEnumByCode(code);
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
