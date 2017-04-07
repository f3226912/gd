package com.gudeng.commerce.gd.order.enm;


/**
 * 会员类型枚举类
 * 
 * @author  panmin
 */
public enum EMemberType {

	/**
	 * 产地供应商
	 */
	SUPPLIER("1", "产地供应商"),
	
	/**
	 * 农批商
	 */
	WHOLESALER("2", "农批商"),
	
	/**
	 * 采购商
	 */
	BUYER("3", "采购商");

	EMemberType(String code, String desc) {
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
	public static EMemberType getEnumByCode(String code) {
		for (EMemberType enm : EMemberType.values()) {
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
		EMemberType enm = getEnumByCode(code);
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
