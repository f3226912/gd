package com.gudeng.commerce.gd.order.enm;

/**
 * 补贴单位枚举类
 * 
 * @author  panmin
 */
public enum ESubUnit {

	/**
	 * 元
	 */
	YUAN("1", "元"),
	
	/**
	 * 元/吨
	 */
	YUAN_TON("31", "元/吨"),
	
	/**
	 * 元/公斤或元/千克
	 */
	YUAN_KG("32", "公斤"),
	
	/**
	 * 千
	 */
	YUAN_QIAN("41", "千"),
	
	/**
	 * 万
	 */
	YUAN_WAN("42", "万"),
	
	/**
	 * 十万
	 */
	YUAN_SHI_WAN("43", "十万"),
	
	/**
	 * 百万
	 */
	YUAN_BAI_WAN("44", "百万"),
	
	/**
	 * 千万
	 */
	YUAN_QIAN_WAN("45", "千万"),
	
	/**
	 * 元/天/车
	 */
	YUAN_DAY_CAR("51", "元/天/车");

	ESubUnit(String code, String desc) {
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
	public static ESubUnit getEnumByCode(String code) {
		for (ESubUnit enm : ESubUnit.values()) {
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
		ESubUnit enm = getEnumByCode(code);
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
