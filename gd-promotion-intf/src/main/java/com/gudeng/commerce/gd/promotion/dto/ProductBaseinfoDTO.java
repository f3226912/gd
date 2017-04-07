package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.ProductBaseinfoEntity;

public class ProductBaseinfoDTO extends ProductBaseinfoEntity {
	private static final long serialVersionUID = 1L;
	
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * 商品参与活动状态
	 */
	private String attendStatus;

	public String getAttendStatus() {
		return attendStatus;
	}

	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}
}
