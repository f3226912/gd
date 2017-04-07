/**
 * 文件：SecurityException.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Oct 24, 2012
 */
package com.gudeng.commerce.gd.exception;



/**
 * 权限异常类
 * @author steven
 *
 */
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
