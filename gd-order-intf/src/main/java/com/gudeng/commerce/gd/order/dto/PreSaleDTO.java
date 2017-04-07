package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.order.entity.PreSaleEntity;

public class PreSaleDTO extends PreSaleEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -33850969883000961L;

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
