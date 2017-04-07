package com.gudeng.commerce.gd.api.enums;

public enum NstOrderStatus {
	
	NOT_DEAL(4, "未完成");

	private NstOrderStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
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
