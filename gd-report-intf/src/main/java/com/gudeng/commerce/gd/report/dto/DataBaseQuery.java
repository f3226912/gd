package com.gudeng.commerce.gd.report.dto;

import java.io.Serializable;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 上午10:24:57
 */
public abstract class DataBaseQuery implements Serializable {

	private static final long serialVersionUID = -3487464395344510936L;
	
	private Long memberId; // 会员ID

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

}
