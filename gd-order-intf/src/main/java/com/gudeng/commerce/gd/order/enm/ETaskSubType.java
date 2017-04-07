package com.gudeng.commerce.gd.order.enm;

/**
 * 任务子类型枚举类
 * 
 * @author  panmin
 * @version  [版本号, 2014-4-3]
 * @since  [产品/模块版本]
 */
public enum ETaskSubType {

	/**
	 * 补贴限制规则
	 */
	SUB_LIMIT_RULE("1", "补贴限制规则", "subLimitRuleTask"),
	
	/**
	 * 计算补贴金额
	 */
	CALC_SUB_AMT("2", "计算补贴金额", "calcSubAmtTask");

	ETaskSubType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	ETaskSubType(String code, String desc, String serviceName) {
		this.code = code;
		this.desc = desc;
		this.serviceName = serviceName;
	}

	/**
	 * 根据编码获取枚举对象
	 * 
	 * @param code
	 *            枚举对象的编码
	 * @return 如果存在，返回对应枚举对象，否则返回null
	 */
	public static ETaskSubType getEnumObject(String code) {
		for (ETaskSubType enm : ETaskSubType.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
	}
	
	/**
	 * 根据编码获取服务名
	 */
	public static String getServiceNameByCode(String code) {
		for (ETaskSubType enm : ETaskSubType.values()) {
			if (enm.getCode().equals(code)) {
				return enm.getServiceName();
			}
		}
		return null;
	}
	
	private String code;

	private String desc;
	
	private String serviceName;

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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
