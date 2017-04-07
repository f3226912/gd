/**
 * @Title: SubLimitRuleCheckException.java
 * @Package com.gudeng.commerce.gd.exception
 * @Description: TODO(用一句话描述该文件做什么)
 * @author mpan
 * @date 2015年12月5日 上午9:58:56
 * @version V1.0
 */ 
package com.gudeng.commerce.gd.exception;

/**
 * @Description: TODO(补贴限制规则检查异常)
 * @author mpan
 * @date 2015年12月5日 上午9:58:56
 */
public class SubLimitRuleCheckException extends ServiceException {

	private static final long serialVersionUID = -2346755538994534691L;

	public SubLimitRuleCheckException(String message) {
		super(message);
	}

}
