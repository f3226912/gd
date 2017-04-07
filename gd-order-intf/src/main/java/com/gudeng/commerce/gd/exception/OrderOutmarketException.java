package com.gudeng.commerce.gd.exception;


public class OrderOutmarketException extends ServiceException{

	private static final long serialVersionUID = 1L;
	
	private String msg;
	
	public OrderOutmarketException(String message) {
		super(message);
		this.msg = message;
	}

	@Override
	public String getMessage() {
		return msg;
	}
	
	
}
