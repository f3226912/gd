package com.gudeng.commerce.gd.order.enm;


/**
 * 补贴审核状态枚举类
 * 
 * @author  panmin
 */
public enum ESubAuditStatus {

	/**
	 * 待补贴
	 */
	SYSTEM_PASS_SUB("1", "待补贴"),
	
	/**
	 * 系统驳回
	 */
	SYSTEM_NOT_SUB("2", "系统驳回"),
	
	/**
	 * 已补贴
	 */
	AUDIT_SUBED("3", "已补贴"),
	
	/**
	 * 不予补贴
	 */
	AUDIT_NOT_SUB("4", "不予补贴");

	ESubAuditStatus(String code, String desc) {
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
	public static ESubAuditStatus getEnumByCode(String code) {
		for (ESubAuditStatus enm : ESubAuditStatus.values()) {
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
		ESubAuditStatus enm = getEnumByCode(code);
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
