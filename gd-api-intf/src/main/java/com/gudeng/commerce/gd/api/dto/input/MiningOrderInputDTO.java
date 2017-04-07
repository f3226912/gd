package com.gudeng.commerce.gd.api.dto.input;

/**
 * 现场采销入参
 * @author wesley
 *
 */
public class MiningOrderInputDTO {
    private String orderBaseInfo;
    private String orderProductDetail;
	public String getOrderBaseInfo() {
		return orderBaseInfo;
	}
	public void setOrderBaseInfo(String orderBaseInfo) {
		this.orderBaseInfo = orderBaseInfo;
	}
	public String getOrderProductDetail() {
		return orderProductDetail;
	}
	public void setOrderProductDetail(String orderProductDetail) {
		this.orderProductDetail = orderProductDetail;
	}
    
}
