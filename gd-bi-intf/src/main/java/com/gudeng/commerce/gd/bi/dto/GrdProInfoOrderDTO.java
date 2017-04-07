package com.gudeng.commerce.gd.bi.dto;

import com.gudeng.commerce.gd.bi.entity.GrdProInfoOrderEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdProInfoOrderDTO extends GrdProInfoOrderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8494527265817750983L;
  	/**
    *接单时间
    */
    private String recieveTimeString;
    
    /**
     *确认时间
     */
     private String confirmTimeString;
     
     private String orderStatusStr;
     
     private String payStatusStr;
     
     
	public String getRecieveTimeString() {
		return recieveTimeString;
	}

	public void setRecieveTimeString(String recieveTimeString) {
		this.recieveTimeString = recieveTimeString;
	}

	public String getConfirmTimeString() {
		return confirmTimeString;
	}

	public void setConfirmTimeString(String confirmTimeString) {
		this.confirmTimeString = confirmTimeString;
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