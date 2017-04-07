package com.gudeng.commerce.gd.promotion.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单活动详细信息实体类
 * @author TerryZhang
 */
public class GdOrderActivityDetailDTO implements java.io.Serializable{

	private static final long serialVersionUID = 140676689317588192L;
	
	/** 活动基本信息列表 */
	private GdOrderActivityDTO activityInfo;
	
	/** 商品活动信息列表 productId -> act info list */
	private Map<Integer, List<GdOrderActivityDTO>> productActInfo;
	
	/** 配送方式 0自提(默认) 1平台配送 2商家配送*/
	private List<Integer> distributeModeList;
	
	/** 平台佣金 */
	private Double platCommision = 0D;

	/** 市场佣金 */
	private Double marketCommision = 0D;

	/** 补贴金额 */
	private Double subsidy = 0D;
	
	/** 预付金额 */
	private Double prepayAmt = 0D;
	
	/** 卖家违约金 */
	private Double sellerPenalty = 0D;
	
	/** 平台违约金 */
	private Double platPenalty = 0D;
	
	/** 物流公司违约金 */
	private Double companyPenalty = 0D;
	
	/** 卖家是否有补贴标志 0否1是 */
	private boolean hasSellerSub = false;
	
	/** 卖家是否有佣金标志 0否1是 */
	private boolean hasSellerCommsn = false;
	
	/** 买家是否有佣金标志 0否1是 */
	private boolean hasBuyerCommsn = false;
	
	/** 是否有预付款标志 0否1是 */
	private boolean hasPrepayAmt = false;
	
	/** 是否有违约金标志 0否1是 */
	private boolean hasPenalty = false;

	public List<Integer> getDistributeModeList() {
		if(distributeModeList == null){
			distributeModeList = new ArrayList<>();
		}
		return distributeModeList;
	}

	public void setDistributeModeList(List<Integer> distributeModeList) {
		this.distributeModeList = distributeModeList;
	}
	
	public Double getPlatCommision() {
		return platCommision;
	}

	public void setPlatCommision(Double platCommision) {
		this.platCommision = platCommision;
	}

	public Double getMarketCommision() {
		return marketCommision;
	}

	public void setMarketCommision(Double marketCommision) {
		this.marketCommision = marketCommision;
	}

	public Double getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Double subsidy) {
		this.subsidy = subsidy;
	}

	public boolean getHasSellerSub() {
		return hasSellerSub;
	}

	public void setHasSellerSub(boolean hasSellerSub) {
		this.hasSellerSub = hasSellerSub;
	}

	public boolean isHasSellerCommsn() {
		return hasSellerCommsn;
	}

	public void setHasSellerCommsn(boolean hasSellerCommsn) {
		this.hasSellerCommsn = hasSellerCommsn;
	}

	public boolean isHasBuyerCommsn() {
		return hasBuyerCommsn;
	}

	public void setHasBuyerCommsn(boolean hasBuyerCommsn) {
		this.hasBuyerCommsn = hasBuyerCommsn;
	}
	
	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
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

	public Map<Integer, List<GdOrderActivityDTO>> getProductActInfo() {
		if(productActInfo == null){
			productActInfo = new HashMap<>();
		}
		return productActInfo;
	}

	public void setProductActInfo(Map<Integer, List<GdOrderActivityDTO>> productActInfo) {
		this.productActInfo = productActInfo;
	}
	
	public boolean isHasPrepayAmt() {
		return hasPrepayAmt;
	}

	public void setHasPrepayAmt(boolean hasPrepayAmt) {
		this.hasPrepayAmt = hasPrepayAmt;
	}

	public boolean isHasPenalty() {
		return hasPenalty;
	}

	public void setHasPenalty(boolean hasPenalty) {
		this.hasPenalty = hasPenalty;
	}
	
	public GdOrderActivityDTO getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(GdOrderActivityDTO activityInfo) {
		this.activityInfo = activityInfo;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("activityInfo: " + getActivityInfo());
		sb.append(", distributeMode: " + getDistributeModeList());
		
		sb.append(", platCommision: " + getPlatCommision());
		sb.append(", marketCommision: " + getMarketCommision());
		sb.append(", subsidy: " + getSubsidy());
		sb.append(", prepayAmt: " + getPrepayAmt());
		sb.append(", sellerPenalty: " + getSellerPenalty());
		sb.append(", platPenalty: " + getPlatPenalty());
		sb.append(", companyPenalty: " + getCompanyPenalty());
		
		sb.append(", hasSellerSub: " + getHasSellerSub());
		sb.append(", hasSellerCommsn: " + isHasSellerCommsn());
		sb.append(", hasBuyerCommsn: " + isHasBuyerCommsn());
		
		sb.append(", hasPrepayAmt: " + isHasPrepayAmt());
		sb.append(", hasPenalty: " + isHasPenalty());
		
		return sb.toString();
	}
}
