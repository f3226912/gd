package com.gudeng.commerce.gd.api.dto.output;

/**
 * E农平台应答数据格式
 * @author TerryZhang
 */
public class ENongResponseDTO {

	/** 响应码，详情见 */
	private String resultcode;
	
	/** 响应码说明 */
	private String resultmsg;
	
	/** 数据存放 json串格式 */
	private Object datajson;
	
	/** 响应json串签名 */
	private String signmsg;

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

	public String getSignmsg() {
		return signmsg;
	}

	public void setSignmsg(String signmsg) {
		this.signmsg = signmsg;
	}
}
