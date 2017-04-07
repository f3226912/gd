package com.gudeng.commerce.gd.exception;

public class SubAmountException extends ServiceException {

	private static final long serialVersionUID = 6046020690053234970L;

	public SubAmountException(String errCode, String message, Throwable ex) {
		super(errCode, message, ex);
	}

	public SubAmountException(String errCode, String message) {
		super(errCode, message);
	}

	public SubAmountException(String message, Throwable ex) {
		super(message, ex);
	}

	public SubAmountException(String message) {
		super(message);
	}

	public SubAmountException(Throwable ex) {
		super(ex);
	}
	
}
