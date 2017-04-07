package com.gudeng.commerce.gd.api.dto.input;

import java.util.Map;

/**
 * 农速通接口端
 * 返回对象
 * @author TerryZhang
 */
public class NstBaseResponseDTO {

	private Integer code;
	
	private String msg;
	
	private Map<String, String> result;
	
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}
}
