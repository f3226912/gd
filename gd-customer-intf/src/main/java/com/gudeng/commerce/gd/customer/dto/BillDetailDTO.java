package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.customer.entity.BillDetailEntity;

public class BillDetailDTO extends BillDetailEntity  implements Serializable{
	 
	private static final long serialVersionUID = 2357702235837200943L;

	/**
	 * 支付时间描述
	 */
	private String payTimeDesc;
	
	private List<Long> orderNos;

	public String getPayTimeDesc() {
		return payTimeDesc;
	}

	public void setPayTimeDesc(String payTimeDesc) {
		this.payTimeDesc = payTimeDesc;
	}

	public List<Long> getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(List<Long> orderNos) {
		this.orderNos = orderNos;
	}

}
