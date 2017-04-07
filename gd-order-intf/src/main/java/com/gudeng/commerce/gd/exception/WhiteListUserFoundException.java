package com.gudeng.commerce.gd.exception;

/**
 * @Description: TODO(白名单用户异常)
 * @author mpan
 * @date 2015年12月20日 下午8:16:28
 */
public class WhiteListUserFoundException extends ServiceException {

	private static final long serialVersionUID = -4090331275319716907L;

	public WhiteListUserFoundException(String message) {
		super(message);
	}
	
}
