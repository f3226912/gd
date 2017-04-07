package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;

public class PreSaleDetailDTO extends PreSaleDetailEntity  implements Serializable {

	private static final long serialVersionUID = 8772853989034362424L;

	private String imageUrl;

	/**  产品单位*/
	private String unitName;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
