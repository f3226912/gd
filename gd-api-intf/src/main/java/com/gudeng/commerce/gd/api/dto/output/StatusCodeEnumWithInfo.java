package com.gudeng.commerce.gd.api.dto.output;

import com.gudeng.commerce.gd.api.enums.ErrorCodeEnum;

/**
 * 错误码对象
 * 附带返回信息
 * @author TerryZhang
 */
public class StatusCodeEnumWithInfo {

	private ErrorCodeEnum statusCodeEnum;
	
	private Object obj;

	public ErrorCodeEnum getStatusCodeEnum() {
		return statusCodeEnum;
	}

	public void setStatusCodeEnum(ErrorCodeEnum statusCodeEnum) {
		this.statusCodeEnum = statusCodeEnum;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	@Override
	public String toString(){
		return "Status code: " + statusCodeEnum.getStatusCode() + ", status msg: " + statusCodeEnum.getStatusMsg();
	}
}
