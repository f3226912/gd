package com.gudeng.commerce.gd.api.dto.sinxin;

/**
 * 智能秤应答数据格式
 * @author TerryZhang
 */
public class Result {

	/** 响应码，详情见 */
	private String resultcode;
	
	/** 响应码说明 */
	private String resultmsg;
	
	private Object datajson; // 返回数据
	
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
