package com.gudeng.commerce.gd.order.enm;


/**
 * 订单状态枚举类
 * 
 * @author  panmin
 */
public enum EOrderStatus {

	/**
	 * 待付款
	 */
	WAIT_PAY("1", "待付款"),
	
	/**
	 * 部分付款
	 */
	PART_PAY("2", "部分付款"),
	
	/**
	 * 已付款
	 */
	PAYED("3", "已完成"),
	
	/**
	 * 已出场
	 */
	OUTMARKETED("4", "已出场"),
	
	/**
	 * 已取消
	 */
	CANNEL("8", "已关闭"),
	
	/**
	 * 已关闭
	 */
	INVALID("9", "已关闭"),
	/**
	 * 待发货
	 */
	WAIT_SEND("11","待发货"),
	/**
	 * 待收货
	 */
	WAIT_SIGN("12","待收货"),
	/**
	 * 待退款
	 */
	TO_REFUND("13","待确认")
	;
	

	EOrderStatus(String code, String desc) {
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
	public static EOrderStatus getEnumByCode(String code) {
		for (EOrderStatus enm : EOrderStatus.values()) {
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
		EOrderStatus enm = getEnumByCode(code);
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
