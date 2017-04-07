package com.gudeng.commerce.gd.report.dto;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 上午10:24:57
 */
public class GoodsServiceQuery extends DataServiceQuery {
	
	private static final long serialVersionUID = 8796519805894563846L;

	//排序CODE
	/*
	 * tradeAmt:DESC 默认
	 * tradeAmt:ASC
	 * pv:DESC
	 * pv:ASC
	 */
	private String sortCode;

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	
}
