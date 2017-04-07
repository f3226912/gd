package com.gudeng.commerce.gd.order.dto;

import com.gudeng.commerce.gd.order.entity.SubAmountTipConfEntity;

/**
 * @Description: TODO(补贴额使用提示配置DTO)
 * @author mpan
 * @date 2015年12月23日 下午8:45:20
 */
public class SubAmountTipConfDTO extends SubAmountTipConfEntity {

	private static final long serialVersionUID = -8330359681695008348L;

	private String marketName; // 市场名称

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

}
