package com.gudeng.commerce.gd.order.enm;


/**
 * 补贴限制规则状态枚举类
 * 
 * @author  panmin
 */
public enum ESubLimitStatus {

	/**
	 * 开户
	 */
	OPEN("1", "开启"),
	
	/**
	 * 禁用
	 */
	CLOSE("0", "禁用");

	ESubLimitStatus(String code, String desc) {
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
	public static ESubLimitStatus getEnumByCode(String code) {
		for (ESubLimitStatus enm : ESubLimitStatus.values()) {
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
		ESubLimitStatus enm = getEnumByCode(code);
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
