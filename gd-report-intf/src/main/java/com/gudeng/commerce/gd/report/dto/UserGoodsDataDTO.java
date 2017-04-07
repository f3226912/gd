package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 商品统计分析
 * @author mpan
 * @date 2016年6月13日 下午4:19:36
 */
public class UserGoodsDataDTO extends DataDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2809493312225328287L;
	
	private List<TimeTradeResult> timeTradeResultList;
	
	private List<GoodsTradeResult> goodsTradeResultLists;

	public List<TimeTradeResult> getTimeTradeResultList() {
		return timeTradeResultList;
	}

	public void setTimeTradeResultList(List<TimeTradeResult> timeTradeResultList) {
		this.timeTradeResultList = timeTradeResultList;
	}

	public List<GoodsTradeResult> getGoodsTradeResultLists() {
		return goodsTradeResultLists;
	}

	public void setGoodsTradeResultLists(List<GoodsTradeResult> goodsTradeResultLists) {
		this.goodsTradeResultLists = goodsTradeResultLists;
	}
	
	

}
