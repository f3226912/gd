package com.gudeng.commerce.gd.exception;


/**
 * @purpose 退款异常
 * @author zlb
 * @date 2016年12月8日
 */
public class RefundException extends Exception {

	private static final long serialVersionUID = -4841089165626408293L;
	/** 编码 **/
	private Integer code;
	/** 描述 **/
	private String msg;

	public RefundException(Integer code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
		
	}
	
	public RefundException(String msg){
		super(msg);
		this.msg = msg;
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
}
