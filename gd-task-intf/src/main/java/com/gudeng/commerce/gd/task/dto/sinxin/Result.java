package com.gudeng.commerce.gd.task.dto.sinxin;

import java.io.Serializable;

/**
 * @Description: TODO(返回信息)
 * @author mpan
 * @date 2016年3月24日 下午4:32:19
 */
public class Result implements Serializable {

	private static final long serialVersionUID = -3181881414303524270L;

	public String returnValue; // 返回状态 1成功，-1失败
	public String returnMsg; // 返回消息

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
