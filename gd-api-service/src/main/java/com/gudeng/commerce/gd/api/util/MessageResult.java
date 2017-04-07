package com.gudeng.commerce.gd.api.util;

import java.io.Serializable;

public class MessageResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statusCode; //错误编码
	private String statusMsg;  //错误信息
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	

}
