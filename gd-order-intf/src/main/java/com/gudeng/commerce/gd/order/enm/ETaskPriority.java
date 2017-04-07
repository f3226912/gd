package com.gudeng.commerce.gd.order.enm;


/**
 * 任务优先级枚举类（优先级1-9，默认5，值越小，优先级越高）
 * 
 * @author  panmin
 * @version  [版本号, 2014-4-3]
 * @since  [产品/模块版本]
 */
public enum ETaskPriority {

	/**
	 * 1级（最高级）
	 */
	ONE(1, "1级"),

	/**
	 * 2级
	 */
	TWO(2, "2级"),
	
	/**
	 * 3级
	 */
	THREE(3, "3级"),
	
	/**
	 * 4级
	 */
	FOUR(4, "4级"),
	
	/**
	 * 5级
	 */
	FIVE(5, "5级"),
	
	/**
	 * 6级
	 */
	SIX(6, "6级"),
	
	/**
	 * 7级
	 */
	SEVEN(7, "7级"),
	
	/**
	 * 8级
	 */
	EIGHT(8, "8级"),
	
	/**
	 * 9级（最低级）
	 */
	NINE(9, "9级");
	
	ETaskPriority(int code, String desc) {
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
	public static ETaskPriority getEnumObject(int code) {
		for (ETaskPriority enm : ETaskPriority.values()) {
			if (code == enm.getCode()) {
				return enm;
			}
		}
		return null;
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
