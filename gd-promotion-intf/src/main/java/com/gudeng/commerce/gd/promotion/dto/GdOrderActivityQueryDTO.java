package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

/**
 * 商品/商铺活动查询DTO
 * @author TerryZhang
 */
public class GdOrderActivityQueryDTO implements java.io.Serializable{

	private static final long serialVersionUID = -6223720171046498868L;
	
	/** 活动id list */
	private List<GdOrderActivityDTO> actIdList;
	
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

	/** 商品id(无商品则不用设置) */
	private List<GdProductActInfoDTO> productList;
	
	/** 支付银行卡信息(未支付则不用设置) */
	private GdPayBankCardInfoDTO payCardInfo;
	
	/** 标志  1表示清算*/
	private String flag;
	
	/** 是否有商品的标志*/
	private boolean hasProduct = false;
	
	/** 是否已下单的标志*/
	private boolean isOrdered = true;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public List<GdProductActInfoDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<GdProductActInfoDTO> productList) {
		this.productList = productList;
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
	
	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	
	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	
	public GdPayBankCardInfoDTO getPayCardInfo() {
		return payCardInfo;
	}

	public void setPayCardInfo(GdPayBankCardInfoDTO payCardInfo) {
		this.payCardInfo = payCardInfo;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isHasProduct() {
		return hasProduct;
	}

	public void setHasProduct(boolean hasProduct) {
		this.hasProduct = hasProduct;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public List<GdOrderActivityDTO> getActIdList() {
		return actIdList;
	}

	public void setActIdList(List<GdOrderActivityDTO> actIdList) {
		this.actIdList = actIdList;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Activity query params info, ");
		sb.append("order info: actIdList: " + getActIdList());
		sb.append(", orderNo: " + getOrderNo());
		sb.append(", orderAmount: " + getOrderAmount());
		sb.append(", payAmount: " + getPayAmount());
		sb.append(", buyerId: " + getBuyerId());
		sb.append(", sellerId: " + getSellerId());
		sb.append(", marketId: " + getMarketId());
		sb.append(", businessId: " + getBusinessId());
		if(getProductList() != null && getProductList().size() > 0){
			for(int i=0, len=getProductList().size(); i<len; i++){
				GdProductActInfoDTO productInfo = getProductList().get(i);
				sb.append(", product info"+i+": " + productInfo.toString());
			}
		}
		if(getPayCardInfo() != null){
			sb.append(", pay card info: " + getPayCardInfo().toString());
		}
		return sb.toString();
	}
}
