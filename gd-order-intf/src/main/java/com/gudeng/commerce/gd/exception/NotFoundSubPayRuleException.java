/**
 * @Title: NotFoundSubPayRuleException.java
 * @Package com.gudeng.commerce.gd.exception
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年12月5日 上午9:49:43
 * @version V1.0
 */ 
package com.gudeng.commerce.gd.exception;

/**
 * @Description: TODO(未找到补贴发放规则异常)
 * @author mpan
 * @date 2015年12月5日 上午9:49:43
 */
public class NotFoundSubPayRuleException extends ServiceException {

	private static final long serialVersionUID = -6845518349262975356L;

	public NotFoundSubPayRuleException(String message) {
		super(message);
	}

}
