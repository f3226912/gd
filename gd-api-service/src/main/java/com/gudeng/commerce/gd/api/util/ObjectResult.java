/**
 * 文件：ObjectResult.java
 * 公司：深圳祥云信息科技有限公司
 * 版权：Copyrigong © 2011 Shenzhen Innovane Information Technologies Co..Ltd, Inc. All rights reserved.
 * 系统：
 * 描述：ＩＮＮＯＶＡＮＥ（ＰＡＡＳ　&　ＳＡＡＳ）ＳＯＦＴＷＡＲＥ
 * 作者：Nail.zhang
 * 时间：Jan 31, 2012
 */
package com.gudeng.commerce.gd.api.util;

import java.io.Serializable;

/**
 * 便于自定义错误信息，用枚举不灵活，故加此实体类
 * @author weiwenke
 *
 */
public class ObjectResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String statusCode; //错误编号
	private String statusMsg;  //错误提示
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	
	
	
}
