package com.gudeng.commerce.gd.order.enm;

/**
 * 支付子类型枚举类
 * 
 * @author  panmin
 */
public enum EPaySubType4Stament {

	/**
	 *  E农管家
	 */
	ENONG("1","1", "E农"),
	
	/**
	 * 农信社
	 */
	GXRCB("GXRCB", "2","农信社"),
	
	NNCCB("NNCCB","3","南宁建行");

	EPaySubType4Stament(String channel,String code, String desc) {
		this.channel = channel;
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
	public static EPaySubType4Stament getEnumByCode(String code) {
		for (EPaySubType4Stament enm : EPaySubType4Stament.values()) {
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
		EPaySubType4Stament enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
	
	private String code;

	private String desc;
	
	private String channel;

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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
