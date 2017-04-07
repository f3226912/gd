package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

/**
 * 农速通接口返回对象
 * @author zlb
 * @date 2016年12月9日
 */
public class NstCallbackDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3221701486831591609L;
	
	private Integer code;
	private String msg;
	//private String result;
	private boolean success;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
//	public String getResult() {
//		return result;
//	}
//	public void setResult(String result) {
//		this.result = result;
//	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
