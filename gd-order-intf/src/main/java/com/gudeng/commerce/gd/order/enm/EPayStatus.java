package com.gudeng.commerce.gd.order.enm;

/**
 * 支付状态枚举类
 * 
 * @author  panmin
 */
public enum EPayStatus {

	/**
	 * 未支付
	 */
	WAIT_PAY("0", "未支付"),
	
	/**
	 * 已支付
	 */
	PAID("1", "已支付"),
	
	/**
	 * 支付失败
	 */
	PAY_FAIL("9", "支付失败");

	EPayStatus(String code, String desc) {
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
	public static EPayStatus getEnumByCode(String code) {
		for (EPayStatus enm : EPayStatus.values()) {
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
		EPayStatus enm = getEnumByCode(code);
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
