package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;


/**
 * 供应商查询输入类
 * @date 2016年10月18日
 */
public class SupplierInputDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1401558300768296637L;

	private String type;
	private String marketId;
	private String memberId;
	private String address;
	private String categoryId;
	private String keyWord;//关键词

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

}
