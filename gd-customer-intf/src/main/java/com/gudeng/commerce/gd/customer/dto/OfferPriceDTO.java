package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;

/**
 * 
 * @author Kwang
 * 推送报价信息Dto
 */
public class OfferPriceDTO implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   *采购信息ID
   */
   private Integer purchaseId;

   /**
   *商品名称
   */
   private String goodsName;

   /**
   *报价价格
   */
   private Double offerPrice;
   
   /**
    *用户Id
    */
    private Integer memberId;
    
    /**
     *device_tokens
     */
     private String device_tokens;

    public Integer getPurchaseId() {
      return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
      this.purchaseId = purchaseId;
    }

    public String getGoodsName() {
      return goodsName;
    }

    public void setGoodsName(String goodsName) {
      this.goodsName = goodsName;
    }

    public Double getOfferPrice() {
      return offerPrice;
    }

    public void setOfferPrice(Double offerPrice) {
      this.offerPrice = offerPrice;
    }

    public Integer getMemberId() {
      return memberId;
    }

    public void setMemberId(Integer memberId) {
      this.memberId = memberId;
    }

    public String getDevice_tokens() {
      return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
      this.device_tokens = device_tokens;
    } 
    
     
    
}
