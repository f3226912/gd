/**
 *
 */
package com.gudeng.commerce.gd.home.util;



 
public class ApiReturnObject {

	private int statusCode=ErrorCodeEnum.FAIL.getValue();

	private String msg;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
