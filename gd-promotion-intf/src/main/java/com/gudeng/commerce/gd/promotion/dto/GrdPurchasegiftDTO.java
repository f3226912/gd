package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdPurchasegiftEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdPurchasegiftDTO extends GrdPurchasegiftEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String purchaser;

    private String marketName;

    private String name;
    
    private String unit;
    
    private String warehouseName;
    
	private  Double prototypePrice;
	
	private Integer instockCount;
	
    private String giftName;
    
    private Integer purchaseCount;

	public Integer getInstockCount() {
		return instockCount;
	}

	public void setInstockCount(Integer instockCount) {
		this.instockCount = instockCount;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Double getPrototypePrice() {
		return prototypePrice;
	}

	public void setPrototypePrice(Double prototypePrice) {
		this.prototypePrice = prototypePrice;
	} 
	
    public String getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(String purchaser) {
		this.purchaser = purchaser;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	
	
}