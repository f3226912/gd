/**
 * 文件：ObjectResult.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Jan 31, 2012
 */
package com.gudeng.commerce.gd.home.util;

import java.io.Serializable;

/**
 * 基础JSON类
 * 用于保存对于操作结果的JSON对象，便于处理JSON返回
 * @author Administrator
 *
 */
public class BaseJsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4980292627262511751L;

	/**
	 * 处理结果
	 */
	private int statusCode = -1;

	/**
	 * 处理返回信息
	 */
	private String msg;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}