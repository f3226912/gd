package com.gudeng.commerce.gd.exception;

/**
* 任务失败异常，任务池处理返回此异常，会重新尝试此任务
*/
public class ServiceTaskFailException extends ServiceException {

	private static final long serialVersionUID = 1506254831763284127L;

	public ServiceTaskFailException(String message) {
		super(message);
	}

}
