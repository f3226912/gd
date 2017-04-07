package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;

public class PromotionSourceDTO extends PromotionSourceEntity  implements Serializable, Comparator{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4164157256178498531L;

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	} 
    private Long sourceId;

    private Integer type;

    private String url;

    private String urlImg;
    
    private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

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
