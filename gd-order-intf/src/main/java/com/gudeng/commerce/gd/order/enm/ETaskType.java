package com.gudeng.commerce.gd.order.enm;

/**
 * 任务类型枚举类
 * 
 * @author  panmin
 * @version  [版本号, 2014-4-3]
 * @since  [产品/模块版本]
 */
public enum ETaskType {

	/**
	 * 补贴
	 */
	SUB("1", "补贴"),
	
	/**
	 * 发送短信
	 */
	SEND_SMS("2", "发送短信"),
	
	/**
	 * 自动撤单
	 */
	AUTO_CANCEL_ORDER("3", "自动撤单"),
	
	/**
	 * 发送邮件
	 */
	SEND_EMAIL("4", "发送邮件", "sendEmailTask"),
	
	/**
	 * 商品同步
	 */
	PRODUCT_SYNC("5", "商品同步", "productSyncTask"),
	
	ORDER_SYNC("6","订单发送给支付中心","orderSyncTask");

	ETaskType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	ETaskType(String code, String desc, String serviceName) {
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
	public static ETaskType getEnumObject(String code) {
		for (ETaskType enm : ETaskType.values()) {
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
		for (ETaskType enm : ETaskType.values()) {
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
