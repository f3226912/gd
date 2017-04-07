package com.gudeng.commerce.gd.order.enm;


/**
 * 补贴限制类型枚举类
 * 
 * @author  panmin
 */
public enum ESubLimitType {

	/**
	 * 车辆限制
	 */
	VEH_LIMIT("1", "车辆限制", "VehLimitCheckRuleImpl"),
	
	/**
	 * 补贴用户额度限制
	 */
	UAMOUNT_LIMIT("2", "补贴用户额度限制", "UamountLimitCheckRuleImpl"),
	
	/**
	 * 补贴用户次数限制
	 */
	UTIMES_LIMIT("3", "补贴用户次数限制", "UtimesLimitCheckRuleImpl"),
	
	/**
	 * 用户间交易频率限制
	 */
	TWO_UTIMES_LIMIT("4", "用户间交易频率限制", "TwoUtimesLimitCheckRuleImpl"),
	
	/**
	 * 补贴总额数限制
	 */
	TAMOUNT_LIMIT("5", "补贴总额数限制", "TamountLimitCheckRuleImpl"),
	
	/**
	 * 白名单
	 */
	WHITE_LIMIT("6", "白名单", "WhiteListCheckRuleImpl");

	ESubLimitType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	ESubLimitType(String code, String desc, String className) {
		this.code = code;
		this.desc = desc;
		this.className = className;
	}

	/**
	 * 根据编码获取枚举对象
	 * 
	 * @param code
	 *            枚举对象的编码
	 * @return 如果存在，返回对应枚举对象，否则返回null
	 */
	public static ESubLimitType getEnumByCode(String code) {
		for (ESubLimitType enm : ESubLimitType.values()) {
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
		ESubLimitType enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
	
	/**
	 * 根据类名获取代码
	 */
	public static String getCodeByClassName(String className) {
		for (ESubLimitType enm : ESubLimitType.values()) {
			if (className.equals(enm.getClassName())) {
				return enm.getCode();
			}
		}
		return null;
	}
	
	private String code;

	private String desc;
	
	private String className; // 类名

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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
