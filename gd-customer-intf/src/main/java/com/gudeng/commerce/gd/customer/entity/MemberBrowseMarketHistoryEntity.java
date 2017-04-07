package com.gudeng.commerce.gd.customer.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

@Entity(name = "member_browse_market_history")
public class MemberBrowseMarketHistoryEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long memberId;
	
	/**
	 * 市场id
	 */
	private Long marketId;
	
	/**
	 * 浏览次数
	 */
	private Integer browseCount;
	
	/**
	 * 浏览时间
	 */
	private Date browseTime;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Integer getBrowseCount() {
		return browseCount;
	}

	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}

	public Date getBrowseTime() {
		return browseTime;
	}

	public void setBrowseTime(Date browseTime) {
		this.browseTime = browseTime;
	}
}
