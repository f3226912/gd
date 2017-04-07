package com.gudeng.commerce.gd.bi.dto;

import com.gudeng.commerce.gd.bi.entity.GrdProFreightOrderEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProFreightOrderDTO extends GrdProFreightOrderEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8494527365817751983L;
	 /**
     *订单生成时间
     */
     private String generateTimeString;
     
     private String orderStatusStr;
     
     private String payStatusStr;

	public String getGenerateTimeString() {
		return generateTimeString;
	}

	public void setGenerateTimeString(String generateTimeString) {
		this.generateTimeString = generateTimeString;
	}

	public String getOrderStatusStr() {
		return orderStatusStr;
	}

	public void setOrderStatusStr(String orderStatusStr) {
		this.orderStatusStr = orderStatusStr;
	}

	public String getPayStatusStr() {
		return payStatusStr;
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}
    
	
}