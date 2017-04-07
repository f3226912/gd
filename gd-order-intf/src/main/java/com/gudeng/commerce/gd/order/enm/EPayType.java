package com.gudeng.commerce.gd.order.enm;

/**
 * 支付方式枚举类
 * 
 * @author  panmin
 */
public enum EPayType {

	/**
	 * 账户余额
	 */
	ACC_BALANCE("1", "账户余额"),
	
	/**
	 * POS刷卡
	 */
	OFFLINE_CARD("2", "POS刷卡"),
	
	/**
	 * 现金
	 */
	CASH("3", "现金"),
	
	/**
	 * 支付宝
	 */
	ALIPAY("4", "支付宝"),
	
	/**
	 * 账户余额+POS刷卡
	 */
	ACC_BALANCE_AND_OFFLINE_CARD("12", "账户余额+POS刷卡"),
	
	/**
	 *账户余额+现金
	 */
	ACC_BALANCE_AND_CASH("13", "账户余额+现金");

	EPayType(String code, String desc) {
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
	public static EPayType getEnumByCode(String code) {
		for (EPayType enm : EPayType.values()) {
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
		EPayType enm = getEnumByCode(code);
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
