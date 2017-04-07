package com.gudeng.commerce.gd.customer.dto;

import com.gudeng.commerce.gd.customer.entity.SensitiveLogEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class SensitiveLogDTO extends SensitiveLogEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -740925022460392195L;
	
	private Integer cnt;//被替换的个数
	private String strs;//替换后的str
	
	public Integer getCnt() {
		return cnt;
	}
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
	public String getStrs() {
		return strs;
	}
	public void setStrs(String strs) {
		this.strs = strs;
	}
		
}