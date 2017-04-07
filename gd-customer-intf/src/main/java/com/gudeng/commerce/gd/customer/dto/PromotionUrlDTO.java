package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionUrlEntity;

public class PromotionUrlDTO extends PromotionUrlEntity  implements Serializable, Comparator{

	
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8864078685856491342L;

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	} 
    private Long sourceId;

    private Integer type;

    private String url;

    private String urlImg;

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

}
