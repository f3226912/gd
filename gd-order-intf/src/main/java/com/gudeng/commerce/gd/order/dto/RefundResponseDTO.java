package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @purpose 退款 返回对象
 * @author zlb
 * @date 2016年12月8日
 */
public class RefundResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -247672640923650229L;

	private String refundNo; //退款单号
	private String refundTime; //退款时间
	private Integer code;//状态码 10000 退款成功, 20000 系统错误  20001 该笔退款已存在 20002 参数错误 30000 原路返回失败，转结算
	private String msg;//信息
	
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
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
