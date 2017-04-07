package com.gudeng.commerce.gd.order.enm;

/**
 * 退款状态枚举类
 * @date 2016年12月6日
 */
public enum EOrderRefundStatus {

	/**
	 * 待退款
	 */
	TO_REFUND("1", "待退款"),
	/**
	 * 已退款
	 */
	EXIST_REFUND("3", "已退款"),
	/**
	 * 退款失败
	 */
	FAIL_REFUND("4", "退款失败");
	
	private String code;
	private String value;
	EOrderRefundStatus(String code,String value){
		this.code=code;
		this.value=value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 根据编码获取枚举对象
	 * 
	 * @param code
	 *            枚举对象的编码
	 * @return 如果存在，返回对应枚举对象，否则返回null
	 */
	public static EOrderRefundStatus getEnumByCode(String code) {
		for (EOrderRefundStatus enm : EOrderRefundStatus.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
	}
	
	
}
