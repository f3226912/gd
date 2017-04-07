package com.gudeng.commerce.gd.api.dto.input;

import com.alibaba.fastjson.JSONArray;

/**
 * 支付中心api接口端
 * 返回对象
 * @author TerryZhang
 */
public class PayCenterApiBaseResponseDTO {

	private Integer code;
	
	private String msg;
	
	private JSONArray result;
	
	private boolean success;

	private JSONArray data;

	public JSONArray getData() {
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

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

	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}
}
