package com.gudeng.commerce.gd.order.enm;


/**
 * 补贴类型枚举类
 * 
 * @author  panmin
 */
public enum ESubType {

	/**
	 * 成交金额比例补贴
	 */
	PAY_AMT_PERCENT_SUB("1", "成交金额比例补贴"),
	
	/**
	 * 按订单补贴
	 */
	GOODS_SUB("2", "按订单补贴"),
	
	/**
	 * 按采购重量区间进行补贴
	 */
	WEIGHT_RANGE_SUB("3", "按采购重量区间进行补贴"),
	
	/**
	 * 按采购金额区间进行补贴
	 */
	PAY_AMT_RANGE_SUB("4", "按采购金额区间进行补贴"),
	
	/**
	 * 门岗目测审查
	 */
	GATE_SENTRY_LOOK_SUB("5", "门岗目测审查");

	ESubType(String code, String desc) {
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
	public static ESubType getEnumByCode(String code) {
		for (ESubType enm : ESubType.values()) {
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
		ESubType enm = getEnumByCode(code);
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
