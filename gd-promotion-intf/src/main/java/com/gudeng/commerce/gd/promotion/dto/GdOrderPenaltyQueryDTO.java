package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

/**
 * 订单违约金查询DTO
 * @author TerryZhang
 */
public class GdOrderPenaltyQueryDTO implements java.io.Serializable{

	private static final long serialVersionUID = -6223720171046498868L;
	
	/** 订单号 */
	private String orderNo;
	
	/** 订单金额 */
	private Double orderAmount;
	
	/** 订单实付金额 */
	private Double payAmount;
	
	/** 商铺id */
	private Integer businessId;
	
	/** 市场id */
	private Integer marketId;

	/** 买家id */
	private Integer buyerId;
	
	/** 卖家id */
	private Integer sellerId;
	
	/** 订单预付款金额 */
	private Double prepayAmt = 0D;
	
	/** 卖家违约金 */
	private Double sellerPenalty = 0D;
	
	/** 平台违约金 */
	private Double platPenalty = 0D;
	
	/** 物流公司违约金 */
	private Double companyPenalty = 0D;
	
	/** 是否有违约金标志 0否1是 */
	private boolean hasPenalty = false;

	/** 商品id(无商品则不用设置) */
	private List<GdProductActInfoDTO> productList;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Double getSellerPenalty() {
		return sellerPenalty;
	}

	public void setSellerPenalty(Double sellerPenalty) {
		this.sellerPenalty = sellerPenalty;
	}

	public Double getPlatPenalty() {
		return platPenalty;
	}

	public void setPlatPenalty(Double platPenalty) {
		this.platPenalty = platPenalty;
	}

	public Double getCompanyPenalty() {
		return companyPenalty;
	}

	public void setCompanyPenalty(Double companyPenalty) {
		this.companyPenalty = companyPenalty;
	}

	public boolean isHasPenalty() {
		return hasPenalty;
	}

	public void setHasPenalty(boolean hasPenalty) {
		this.hasPenalty = hasPenalty;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Penalty query params info, ");
		sb.append(", orderNo: " + getOrderNo());
		sb.append(", orderAmount: " + getOrderAmount());
		sb.append(", payAmount: " + getPayAmount());
		sb.append(", buyerId: " + getBuyerId());
		sb.append(", sellerId: " + getSellerId());
		sb.append(", marketId: " + getMarketId());
		sb.append(", businessId: " + getBusinessId());
		sb.append(", prepayAmt: " + getPrepayAmt());
		if(getProductList() != null && getProductList().size() > 0){
			sb.append(", productList info: ");
			for(GdProductActInfoDTO product : getProductList()){
				sb.append(", product info: " + product.toString());
			}
		}else{
			sb.append(", productList: null");
		}
		
		sb.append(", hasPenalty: " + isHasPenalty());
		sb.append(", sellerPenalty: " + getSellerPenalty());
		sb.append(", platPenalty: " + getPlatPenalty());
		sb.append(", companyPenalty: " + getCompanyPenalty());
		return sb.toString();
	}

	public List<GdProductActInfoDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<GdProductActInfoDTO> productList) {
		this.productList = productList;
	}
}
