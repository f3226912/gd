package com.innovane.win9008.exception;


public class PermissionException extends RuntimeException {

	public PermissionException(String message, Throwable cause) {
		super(message, cause);
	}

	public PermissionException(String message) {
		super(message);
	}

	public PermissionException(Throwable cause) {
		super(cause);
	}
}
