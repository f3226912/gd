package com.gudeng.commerce.gd.order.enm;



/**
 * 时间维度枚举类
 * 
 * @author  panmin
 */
public enum ERuleTimeRange {

	/**
	 * 每天
	 */
	DAY(1, "每天"),
	
	/**
	 * 每周
	 */
	WEEK(2, "每周"),
	
	/**
	 * 每月
	 */
	MONTH(3, "每月"),
	
	
	/**
	 * 整个活动
	 */
	ALL(4, "整个活动");

	ERuleTimeRange(int code, String desc) {
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
	public static ERuleTimeRange getEnumByCode(int code) {
		for (ERuleTimeRange enm : ERuleTimeRange.values()) {
			if (code == enm.getCode()) {
				return enm;
			}
		}
		return null;
	}
	
	/**
	 * 根据编码获取枚举描述
	 */
	public static String getDescByCode(int code) {
		ERuleTimeRange enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
	
	private int code;

	private String desc;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
