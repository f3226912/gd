package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @purpose 接口通用 返回对象
 * @author zlb
 * @date 2016年12月8日
 */
public class ServiceResponseDTO implements Serializable{


	private static final long serialVersionUID = -5673180400745917949L;
	
	private Integer code;
	private String msg;
	private String token;
	private RefundResponseLogResult result;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public RefundResponseLogResult getResult() {
		return result;
	}
	public void setResult(RefundResponseLogResult result) {
		this.result = result;
	}
	

	
}
