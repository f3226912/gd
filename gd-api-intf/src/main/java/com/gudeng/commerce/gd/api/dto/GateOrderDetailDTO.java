package com.gudeng.commerce.gd.api.dto;

/**
 * 门岗出场订单商品DTO
 * @author wind
 *
 */
public class GateOrderDetailDTO {

	private String productName;
	
    private String purQuantity;
    
    private String unit;
    
    private String unitName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPurQuantity() {
		return purQuantity;
	}

	public void setPurQuantity(String purQuantity) {
		this.purQuantity = purQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
    
    
}
