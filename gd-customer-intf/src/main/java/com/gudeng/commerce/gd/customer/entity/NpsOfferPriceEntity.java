package com.gudeng.commerce.gd.customer.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "nps_offer_price")
public class NpsOfferPriceEntity implements java.io.Serializable {

  private static final long serialVersionUID = 4875458040391743661L;

  /**
   * ID
   */
  private Integer id;

  /**
   * 用户账号
   */
  private Integer userAcc;

  /**
   * 用户姓名
   */
  private String userName;

  /**
   * 手机号
   */
  private String mobilePhone;

  /**
   * 采购信息ID
   */
  private Integer purchaseId;

  /**
   * 商品名称
   */
  private String goodsName;

  /**
   * 报价价格
   */
  private BigDecimal offerPrice;

  /**
   * hessian版本太低 BigDecimal自动转为0
   */
  private String offerPriceStr;

  /**
   * 报价时间
   */
  private Date offerTime;

  /**
   * 状态 1已报价、2已删除 3隐藏
   */
  private String status;

  /**
   * 备注、其它要求
   */
  private String remark;

  @Id
  @Column(name = "id")
  public Integer getId() {

    return this.id;
  }

  public void setId(Integer id) {

    this.id = id;
  }

  @Column(name = "userAcc")
  public Integer getUserAcc() {

    return this.userAcc;
  }

  public void setUserAcc(Integer userAcc) {

    this.userAcc = userAcc;
  }

  @Column(name = "userName")
  public String getUserName() {

    return this.userName;
  }

  public void setUserName(String userName) {

    this.userName = userName;
  }

  @Column(name = "mobilePhone")
  public String getMobilePhone() {

    return this.mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {

    this.mobilePhone = mobilePhone;
  }

  @Column(name = "purchaseId")
  public Integer getPurchaseId() {

    return this.purchaseId;
  }

  public void setPurchaseId(Integer purchaseId) {

    this.purchaseId = purchaseId;
  }

  @Column(name = "goodsName")
  public String getGoodsName() {

    return this.goodsName;
  }

  public void setGoodsName(String goodsName) {

    this.goodsName = goodsName;
  }

  @Column(name = "offerPrice")
  public BigDecimal getOfferPrice() {

    return this.offerPrice;
  }

  public void setOfferPrice(BigDecimal offerPrice) {
    this.offerPrice = offerPrice;
  }

  @Column(name = "offerTime")
  public Date getOfferTime() {

    return this.offerTime;
  }

  public void setOfferTime(Date offerTime) {

    this.offerTime = offerTime;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Column(name = "remark")
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getOfferPriceStr() {
    return offerPriceStr;
  }

  public void setOfferPriceStr(String offerPriceStr) {
    this.offerPriceStr = offerPriceStr;
  }
}
