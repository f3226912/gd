package com.gudeng.commerce.gd.pay.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 存放自定义异常信息
 */
public class ResultDTO implements Serializable {
	
	private static final long serialVersionUID = -885250931139165821L;

	/**
	 * 代码
	 */
	private int statusCode;
	
	/**
	 * 内容
	 */
	private String msg;
	
	
	private Object object;
	
	private Map extra;
	
	public String getMsg() {
		return msg;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Map getExtra() {
		return extra;
	}

	public void setExtra(Map extra) {
		this.extra = extra;
	}


}
