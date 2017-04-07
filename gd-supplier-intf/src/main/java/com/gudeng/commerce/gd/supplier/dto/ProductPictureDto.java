package com.gudeng.commerce.gd.supplier.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.supplier.entity.ProductPictureEntity;

public class ProductPictureDto extends ProductPictureEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6062098549619930820L;
	
	private String createTimeString;
	
	private String updateTimeString;
	
	private String imgHost ; 

	public String getImgHost() {
		return imgHost;
	}

	public void setImgHost(String imgHost) {
		this.imgHost = imgHost;
	}

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getUpdateTimeString() {
		return updateTimeString;
	}

	public void setUpdateTimeString(String updateTimeString) {
		this.updateTimeString = updateTimeString;
	}

}
