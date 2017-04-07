package com.gudeng.commerce.gd.order.enm;


/**
 * 任务状态枚举类
 * 
 * @author  panmin
 * @version  [版本号, 2014-4-3]
 * @since  [产品/模块版本]
 */
public enum ETaskStatus {

	/**
	 * 初始状态
	 */
	INIT("0", "初始状态"),

	/**
	 * 运行中
	 */
	RUNNING("1", "运行中"),
	
	/**
	 * 已完成
	 */
	FINISH("2", "已完成"),
	
	/**
	 * 失败
	 */
	FAIL("9", "失败"),
	
	/**
	 * 异常
	 */
	EXCEPTION("10", "异常"),
	
	/**
	 * 锁定
	 */
	LOCK("11", "锁定");

	ETaskStatus(String code, String desc) {
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
	public static ETaskStatus getEnumObject(String code) {
		for (ETaskStatus enm : ETaskStatus.values()) {
			if (enm.getCode().equals(code)) {
				return enm;
			}
		}
		return null;
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
