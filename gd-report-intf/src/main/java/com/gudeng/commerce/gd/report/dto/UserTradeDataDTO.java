package com.gudeng.commerce.gd.report.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月13日 下午4:19:36
 */
public class UserTradeDataDTO extends DataDTO {

	private static final long serialVersionUID = 1320325201351050167L;

	private Long orderNum; // 订单量
	private Long payedOrderNum; // 已收款订单量
	private Long nonPayOrderNum; // 待收款订单量
	private Long closeOrderNum; // 已关闭订单量
	private Long nogPayedOrderNum; // 无商品已收款订单量
	private Long nogNonPayOrderNum; // 无商品待收款订单量
	private Long nogCloseOrderNum; // 无商品已关闭订单量
	private Double tradeAmt; // 交易额
	private Double payedTradeAmt; // 已收款交易额
	private Double nonPayTradeAmt; // 待收款交易额
	private Double closeTradeAmt; // 已关闭交易额
	private Double nogPayedTradeAmt; // 无商品已收款交易额
	private Double nogNonPayTradeAmt; // 无商品待收款交易额
	private Double nogCloseTradeAmt; // 无商品已关闭交易额
	private Long buyerNum; // 交易买家
	private Long goodsNum; // 成交件数
	private Double orderAvgTradeAmt; // 订单平均交易额
	private Double buyerAvgTradeAmt; // 客单价
	private List<TradeResult> tradeResultList; // 时间顺序排列
	private List<Long> buyerList; // 交易买家集合
	private Map<String, Object> buyerMap; // 去重复交易买家map
	
	public static UserTradeDataDTO generate() {
		UserTradeDataDTO userTradeData = new UserTradeDataDTO();
		userTradeData.setOrderNum(0L);
		userTradeData.setPayedOrderNum(0L);
		userTradeData.setNonPayOrderNum(0L);
		userTradeData.setCloseOrderNum(0L);
		userTradeData.setNogPayedOrderNum(0L);
		userTradeData.setNogNonPayOrderNum(0L);
		userTradeData.setNogCloseOrderNum(0L);
		userTradeData.setTradeAmt(0D);
		userTradeData.setPayedTradeAmt(0D);
		userTradeData.setNonPayTradeAmt(0D);
		userTradeData.setCloseTradeAmt(0D);
		userTradeData.setNogPayedTradeAmt(0D);
		userTradeData.setNogNonPayTradeAmt(0D);
		userTradeData.setNogCloseTradeAmt(0D);
		userTradeData.setBuyerNum(0L);
		userTradeData.setGoodsNum(0L);
		userTradeData.setOrderAvgTradeAmt(0D);
		userTradeData.setBuyerAvgTradeAmt(0D);
//		userTradeData.setTradeResultList(new ArrayList<TradeResult>());
		userTradeData.setBuyerList(new ArrayList<Long>());
		userTradeData.setBuyerMap(new HashMap<String, Object>());
		return userTradeData;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getPayedOrderNum() {
		return payedOrderNum;
	}

	public void setPayedOrderNum(Long payedOrderNum) {
		this.payedOrderNum = payedOrderNum;
	}

	public Long getNonPayOrderNum() {
		return nonPayOrderNum;
	}

	public void setNonPayOrderNum(Long nonPayOrderNum) {
		this.nonPayOrderNum = nonPayOrderNum;
	}

	public Long getCloseOrderNum() {
		return closeOrderNum;
	}

	public void setCloseOrderNum(Long closeOrderNum) {
		this.closeOrderNum = closeOrderNum;
	}

	public Long getNogPayedOrderNum() {
		return nogPayedOrderNum;
	}

	public void setNogPayedOrderNum(Long nogPayedOrderNum) {
		this.nogPayedOrderNum = nogPayedOrderNum;
	}

	public Long getNogNonPayOrderNum() {
		return nogNonPayOrderNum;
	}

	public void setNogNonPayOrderNum(Long nogNonPayOrderNum) {
		this.nogNonPayOrderNum = nogNonPayOrderNum;
	}

	public Long getNogCloseOrderNum() {
		return nogCloseOrderNum;
	}

	public void setNogCloseOrderNum(Long nogCloseOrderNum) {
		this.nogCloseOrderNum = nogCloseOrderNum;
	}

	public Double getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(Double tradeAmt) {
		this.tradeAmt = tradeAmt;
	}

	public Double getPayedTradeAmt() {
		return payedTradeAmt;
	}

	public void setPayedTradeAmt(Double payedTradeAmt) {
		this.payedTradeAmt = payedTradeAmt;
	}

	public Double getNonPayTradeAmt() {
		return nonPayTradeAmt;
	}

	public void setNonPayTradeAmt(Double nonPayTradeAmt) {
		this.nonPayTradeAmt = nonPayTradeAmt;
	}

	public Double getCloseTradeAmt() {
		return closeTradeAmt;
	}

	public void setCloseTradeAmt(Double closeTradeAmt) {
		this.closeTradeAmt = closeTradeAmt;
	}

	public Double getNogPayedTradeAmt() {
		return nogPayedTradeAmt;
	}

	public void setNogPayedTradeAmt(Double nogPayedTradeAmt) {
		this.nogPayedTradeAmt = nogPayedTradeAmt;
	}

	public Double getNogNonPayTradeAmt() {
		return nogNonPayTradeAmt;
	}

	public void setNogNonPayTradeAmt(Double nogNonPayTradeAmt) {
		this.nogNonPayTradeAmt = nogNonPayTradeAmt;
	}

	public Double getNogCloseTradeAmt() {
		return nogCloseTradeAmt;
	}

	public void setNogCloseTradeAmt(Double nogCloseTradeAmt) {
		this.nogCloseTradeAmt = nogCloseTradeAmt;
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

	public Double getOrderAvgTradeAmt() {
		return orderAvgTradeAmt;
	}

	public void setOrderAvgTradeAmt(Double orderAvgTradeAmt) {
		this.orderAvgTradeAmt = orderAvgTradeAmt;
	}

	public Double getBuyerAvgTradeAmt() {
		return buyerAvgTradeAmt;
	}

	public void setBuyerAvgTradeAmt(Double buyerAvgTradeAmt) {
		this.buyerAvgTradeAmt = buyerAvgTradeAmt;
	}

	public List<TradeResult> getTradeResultList() {
		return tradeResultList;
	}

	public void setTradeResultList(List<TradeResult> tradeResultList) {
		this.tradeResultList = tradeResultList;
	}

	public List<Long> getBuyerList() {
		return buyerList;
	}

	public void setBuyerList(List<Long> buyerList) {
		this.buyerList = buyerList;
	}

	public Map<String, Object> getBuyerMap() {
		return buyerMap;
	}

	public void setBuyerMap(Map<String, Object> buyerMap) {
		this.buyerMap = buyerMap;
	}

}
