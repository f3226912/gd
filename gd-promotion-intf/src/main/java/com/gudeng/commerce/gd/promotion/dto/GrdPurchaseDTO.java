package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.GrdPurchaseEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdPurchaseDTO extends GrdPurchaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1724751844441057328L;
	
    private String marketName;

    private String warehouseName;
    
    private Double orderamount;
	
	private Double unitPrice;
	
	private String giftNO;
	
	private Double totalPrice;

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getGiftNO() {
		return giftNO;
	}

	public void setGiftNO(String giftNO) {
		this.giftNO = giftNO;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
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

	public Double getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(Double orderamount) {
		this.orderamount = orderamount;
	}

}