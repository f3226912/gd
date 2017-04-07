package com.gudeng.commerce.gd.report.dto;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:19:36
 */
public class UserAllTradeDataDTO extends DataDTO {

	private static final long serialVersionUID = 1320325201351050167L;

	private String orderStatus; // 订单状态
	private Integer hasGoods; // 是否有商品
	private Long orderNum; // 订单量
	private Double tradeAmt; // 交易额
	private Long buyerNum; // 交易买家
	private Long goodsNum; // 成交件数

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getHasGoods() {
		return hasGoods;
	}

	public void setHasGoods(Integer hasGoods) {
		this.hasGoods = hasGoods;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public Long getBuyerNum() {
		return buyerNum;
	}

	public void setBuyerNum(Long buyerNum) {
		this.buyerNum = buyerNum;
	}

	public Long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

}
