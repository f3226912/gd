package com.gudeng.commerce.gd.promotion.dto;

/**
 * 商品活动信息查询DTO
 * @author TerryZhang
 */
public class GdProductActInfoDTO implements java.io.Serializable{

	private static final long serialVersionUID = 6266937069545929625L;

	/** 商品id */
	private Integer productId;
	
	/** 商品购买单价 */
	private Double price;
	
	/** 商品购买数量 */
	private Double quantity;
	
	/** 商品购买金额 */
	private Double productAmount;
	
	/** 活动详细信息 */
	private GdOrderActivityDetailDTO actInfo;
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public GdOrderActivityDetailDTO getActInfo() {
		return actInfo;
	}

	public void setActInfo(GdOrderActivityDetailDTO actInfo) {
		this.actInfo = actInfo;
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("productId: " + getProductId());
		sb.append(", price: " + getPrice());
		sb.append(", quantity: " + getQuantity());
		sb.append(", product act info: " + getActInfo().toString());
		return sb.toString();
	}
}
