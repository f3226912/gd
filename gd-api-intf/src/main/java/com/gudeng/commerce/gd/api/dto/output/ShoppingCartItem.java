package com.gudeng.commerce.gd.api.dto.output;

import java.io.Serializable;

/**
 * 购物项
 * @date 2016年11月3日
 */
public class ShoppingCartItem implements Serializable{
	
	private static final long serialVersionUID = -3465477915547210068L;
	private Double amount ;                 //该商品总计
	private Double price    ;               //该商品购物车价格
	private Double newPrice  ;              //该商品最新价格
	private Long productId  ;               //商品
	private String productName;             //商品名称
	private Double quantity  ;              //采购数量
	private String unitName  ;              //商品单位名称
	private String selected ;               //是否勾选 0未勾选 1勾选
	private String status  ;                //状态（售罄0、已下架-1、正常1）
	private Double stockCount;              //库存
	private String imgUrl;                  //商品图片尺寸170
	private Long shoppingItemId;          //购物项Id
	private Integer platform;
	
	public Integer getPlatform() {
		return platform;
	}
	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
	public Long getShoppingItemId() {
		return shoppingItemId;
	}
	public void setShoppingItemId(Long shoppingItemId) {
		this.shoppingItemId = shoppingItemId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getStockCount() {
		return stockCount;
	}
	public void setStockCount(Double stockCount) {
		this.stockCount = stockCount;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	

}
