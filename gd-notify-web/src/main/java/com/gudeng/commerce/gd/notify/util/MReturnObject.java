/**
 *
 */
package com.gudeng.commerce.gd.notify.util;

import com.gudeng.commerce.gd.notify.constant.ErrorCodeEnum;


 
public class MReturnObject {

	private int statusCode= ErrorCodeEnum.FAIL.getValue();

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
