package com.gudeng.commerce.gd.notify.dto;

/**
 * 应答数据格式
 * @author TerryZhang
 */
public class ResponseDTO {

	/** 响应码，详情见 */
	private String resultcode;
	
	/** 响应码说明 */
	private String resultmsg;
	
	/** 数据存放 json串格式 */
	private Object datajson;

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
	}

	public Object getDatajson() {
		return datajson;
	}

	public void setDatajson(Object datajson) {
		this.datajson = datajson;
	}
}
