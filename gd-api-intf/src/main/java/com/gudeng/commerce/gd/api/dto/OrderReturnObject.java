/**
 *
 */
package com.gudeng.commerce.gd.api.dto;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;


public class OrderReturnObject {

	private int statusCode= ErrorCodeEnum.FAIL.getStatusCode();

	private String msg="";

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
