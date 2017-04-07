package com.gudeng.commerce.gd.api.enums;

public enum PictureTypeEmum {

	/** App图 */
	APP(4),
	/** 多角度图 */
	MULTIPLE(2),
	/** 主图 */
	Main(1);

	private final Integer value;

	PictureTypeEmum(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
