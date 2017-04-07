package com.gudeng.commerce.left_right;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductDto;
import com.gudeng.commerce.gd.supplier.entity.ProductCategoryEntity;

/**
 * ProductCategoryDTO Entity
 * 
 */
public class ProductCategoryDTOCopy extends ProductCategoryEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -170099000429488270L;
	
	private List<ProductCategoryDTOCopy> childList;
	
	private List<ProductCategoryDTOCopy> childList3;
	
	private List<ProductDto> productList;
	
	private List<?> businessList;
	
	private List<?> adInfoList;
	
	

	public List<ProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
	}

	public List<?> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<?> businessList) {
		this.businessList = businessList;
	}

	public List<?> getAdInfoList() {
		return adInfoList;
	}

	public void setAdInfoList(List<?> adInfoList) {
		this.adInfoList = adInfoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ProductCategoryDTOCopy> getChildList() {
		return childList;
	}

	public void setChildList(List<ProductCategoryDTOCopy> childList) {
		this.childList = childList;
	}

	public List<ProductCategoryDTOCopy> getChildList3() {
		return childList3;
	}

	public void setChildList3(List<ProductCategoryDTOCopy> childList3) {
		this.childList3 = childList3;
	}
	
	

}
