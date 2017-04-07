package com.gudeng.commerce.gd.admin.dto;

import java.util.Date;
import java.util.List;

/**
 * 农速通物流进度接口返回对象
 * 
 * @date 2016年12月6日
 */
public class NstLogisticalProgressDTO {
	private Integer code;
	private String msg;
	private NstResult result;

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

	public NstResult getResult() {
		return result;
	}

	public void setResult(NstResult result) {
		this.result = result;
	}

}


  