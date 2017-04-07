package com.gudeng.commerce.gd.order.enm;


/**
 * 银行支付流水使用状态枚举类
 * 
 * @author  panmin
 */
public enum EBillUseStatus {

	/**
	 * 已使用
	 */
	USEED("1", "已使用"),
	
	/**
	 * 未使用
	 */
	UNUSE("0", "未使用");

	EBillUseStatus(String code, String desc) {
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
	public static EBillUseStatus getEnumByCode(String code) {
		for (EBillUseStatus enm : EBillUseStatus.values()) {
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
		EBillUseStatus enm = getEnumByCode(code);
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
