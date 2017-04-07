package com.gudeng.commerce.gd.admin.exception;

/** 自定义业务异常类
 * @author Administrator
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -3081112801138979958L;

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
}
