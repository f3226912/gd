package com.gudeng.commerce.gd.search.dto;

import java.io.Serializable;

/** 
* @author  bdhuang 
* @date 创建时间：2016年5月16日 下午12:54:38 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class FaceWordDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String name;//词
	
	public Integer num;//个数

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
