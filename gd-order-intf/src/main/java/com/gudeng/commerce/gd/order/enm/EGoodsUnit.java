package com.gudeng.commerce.gd.order.enm;


/**
 * 商品单位枚举类
 * 
 * @author  panmin
 */
public enum EGoodsUnit {

	/**
	 * 吨
	 */
	TON("1", "吨"),
	
	/**
	 * 千克
	 */
	KG("2", "千克"),
	
	/**
	 * 公斤
	 */
	GJ("3", "公斤"),
	
	/**
	 * 克
	 */
	K("4", "克");

	EGoodsUnit(String code, String desc) {
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
	public static EGoodsUnit getEnumByCode(String code) {
		for (EGoodsUnit enm : EGoodsUnit.values()) {
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
		EGoodsUnit enm = getEnumByCode(code);
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
