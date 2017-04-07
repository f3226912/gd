package com.gudeng.commerce.gd.promotion.dto;

import javax.persistence.Column;

import com.gudeng.commerce.gd.promotion.entity.GrdGdGiftEntity;
/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public class GrdGdGiftDTO extends GrdGdGiftEntity {
	public String[] getGiftImages() {
		return giftImages;
	}

	public void setGiftImages(String[] giftImages) {
		this.giftImages = giftImages;
	}

	/**
	 * 礼品图片数组
	 */
  private String[] giftImages;
  
  
  
  private Double unitPrice;

  private Integer count;

  private Double amount;
  
  public Double getUnitPrice(){

      return this.unitPrice;
  }
  public void setUnitPrice(Double unitPrice){

      this.unitPrice = unitPrice;
  }
  public Integer getCount(){

      return this.count;
  }
  public void setCount(Integer count){

      this.count = count;
  }
  public Double getAmount(){

      return this.amount;
  }
  public void setAmount(Double amount){

      this.amount = amount;
  }
  
}