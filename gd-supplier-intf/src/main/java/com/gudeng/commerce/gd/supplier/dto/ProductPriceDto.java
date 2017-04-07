package com.gudeng.commerce.gd.supplier.dto;

import java.io.Serializable;

import com.gudeng.commerce.gd.supplier.entity.ProductPriceEntity;

public class ProductPriceDto extends ProductPriceEntity implements Serializable , Comparable<ProductPriceDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2235488075999307128L;

	private String createTimeString;
	
	private String updateTimeString;

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

/*	@Override
	public int compare(ProductPriceDto o1, ProductPriceDto o2) {
		if (o1.getPrice().doubleValue() - o2.getPrice().doubleValue() < 0){
			return -1;
		}else if (o1.getPrice().doubleValue() - o2.getPrice().doubleValue() > 0){
			return 1;
		}
		return 0;
	}*/

	@Override
	public int compareTo(ProductPriceDto o) {
		if (this.getPrice().doubleValue() - o.getPrice().doubleValue() < 0){
			return -1;
		}else if (this.getPrice().doubleValue() - o.getPrice().doubleValue() > 0){
			return 1;
		}
		return 0;
	}
	
}
