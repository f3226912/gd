package com.gudeng.commerce.gd.api.dto;

public class PushAdAppDTO {
	
	/**
	 * 渠道(01农批web 02农批宝 03农速通)
	 */
	private String adCanal;

	/**
	 * 广告链接地址
	 */
	private String adLinkUrl;
	
	/**
	 * 广告名
	 */
	private String adName;
	
	/**
	 * 广告图片地址
	 */
	private String adUrl;
	
	/**
	 * 广告市场id
	 */
	private Long marketId;
	
	/**
	 * 广告产品id
	 */
	private Long productId;
	
	
	/**
	 * 公告 拼接的字符串
	 */
	private String noticeString;
	
	public String getNoticeString() {
		return noticeString;
	}

	public void setNoticeString(String noticeString) {
		this.noticeString = noticeString;
	}

	public String getAdCanal() {
		return adCanal;
	}

	public void setAdCanal(String adCanal) {
		this.adCanal = adCanal;
	}

	public String getAdLinkUrl() {
		return adLinkUrl;
	}

	public void setAdLinkUrl(String adLinkUrl) {
		this.adLinkUrl = adLinkUrl;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
}
