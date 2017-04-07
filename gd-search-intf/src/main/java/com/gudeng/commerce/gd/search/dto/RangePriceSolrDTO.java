package com.gudeng.commerce.gd.search.dto;

import org.apache.solr.client.solrj.beans.Field;

public class RangePriceSolrDTO {
	  
	@Field("buyCountStart")
	private Double buyCountStart;
	public Double getBuyCountStart() {
		return buyCountStart;
	}
	public void setBuyCountStart(Double buyCountStart) {
		this.buyCountStart = buyCountStart;
	}
	public Double getBuyCountEnd() {
		return buyCountEnd;
	}
	public void setBuyCountEnd(Double buyCountEnd) {
		this.buyCountEnd = buyCountEnd;
	}
	public Double getRangePrice() {
		return rangePrice;
	}
	public void setRangePrice(Double rangePrice) {
		this.rangePrice = rangePrice;
	}
	   
	@Field("buyCountEnd")
	private Double buyCountEnd;  
	
	
	@Field("rangePrice")
	private Double  rangePrice;
	
}
