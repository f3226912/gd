package com.gudeng.commerce.gd.api.dto.input;

/**
 * E农平台请求数据对象
 * @author TerryZhang
 */
public class ENongRequestDTO {
	
	/**
	 * 请求数据
	 */
	private String reqdata;

	/**
	 * 签名域
	 */
	private String signmsg;

	public String getReqdata() {
		return reqdata;
	}

	public void setReqdata(String reqdata) {
		this.reqdata = reqdata;
	}

	public String getSignmsg() {
		return signmsg;
	}

	public void setSignmsg(String signmsg) {
		this.signmsg = signmsg;
	}
}
