package com.gudeng.commerce.gd.order.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pre_sale")
public class PreSaleEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3204663665515665522L;

	private Integer id;

    private Long orderNo;

    private String orderSource;

    private String channel;

    private String orderType;

    private Double sumAmount;

    private Double deductibleAmount;

    private Double subAmount;

    private Double amountPayable;

    private String orderStatus;

    private Integer memberid;

    private Date orderTime;

    private String shopsName;

    private Integer businessId;

    private Integer marketId;

    private Date createTime;

    private String createuserid;

    private Date updatetime;

    private String updateuserid;

    private String QRCode;

    private String QRCodeImgUrl;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "orderNo")
    public Long getOrderNo(){

        return this.orderNo;
    }
    public void setOrderNo(Long orderNo){

        this.orderNo = orderNo;
    }
    @Column(name = "orderSource")
    public String getOrderSource(){

        return this.orderSource;
    }
    public void setOrderSource(String orderSource){

        this.orderSource = orderSource;
    }
    @Column(name = "channel")
    public String getChannel(){

        return this.channel;
    }
    public void setChannel(String channel){

        this.channel = channel;
    }
    @Column(name = "orderType")
    public String getOrderType(){

        return this.orderType;
    }
    public void setOrderType(String orderType){

        this.orderType = orderType;
    }
    @Column(name = "sumAmount")
    public Double getSumAmount(){

        return this.sumAmount;
    }
    public void setSumAmount(Double sumAmount){

        this.sumAmount = sumAmount;
    }
    @Column(name = "deductibleAmount")
    public Double getDeductibleAmount(){

        return this.deductibleAmount;
    }
    public void setDeductibleAmount(Double deductibleAmount){

        this.deductibleAmount = deductibleAmount;
    }
    @Column(name = "subAmount")
    public Double getSubAmount(){

        return this.subAmount;
    }
    public void setSubAmount(Double subAmount){

        this.subAmount = subAmount;
    }
    @Column(name = "amountPayable")
    public Double getAmountPayable(){

        return this.amountPayable;
    }
    public void setAmountPayable(Double amountPayable){

        this.amountPayable = amountPayable;
    }
    @Column(name = "orderStatus")
    public String getOrderStatus(){

        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus){

        this.orderStatus = orderStatus;
    }
    @Column(name = "memberid")
    public Integer getMemberid(){

        return this.memberid;
    }
    public void setMemberid(Integer memberid){

        this.memberid = memberid;
    }
    @Column(name = "orderTime")
    public Date getOrderTime(){

        return this.orderTime;
    }
    public void setOrderTime(Date orderTime){

        this.orderTime = orderTime;
    }
    @Column(name = "shopsName")
    public String getShopsName(){

        return this.shopsName;
    }
    public void setShopsName(String shopsName){

        this.shopsName = shopsName;
    }
    @Column(name = "businessId")
    public Integer getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Integer businessId){

        this.businessId = businessId;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createuserid")
    public String getCreateuserid(){

        return this.createuserid;
    }
    public void setCreateuserid(String createuserid){

        this.createuserid = createuserid;
    }
    @Column(name = "updatetime")
    public Date getUpdatetime(){

        return this.updatetime;
    }
    public void setUpdatetime(Date updatetime){

        this.updatetime = updatetime;
    }
    @Column(name = "updateuserid")
    public String getUpdateuserid(){

        return this.updateuserid;
    }
    public void setUpdateuserid(String updateuserid){

        this.updateuserid = updateuserid;
    }
    @Column(name = "QRCode")
    public String getQRCode(){

        return this.QRCode;
    }
    public void setQRCode(String QRCode){

        this.QRCode = QRCode;
    }
    @Column(name = "QRCodeImgUrl")
    public String getQRCodeImgUrl(){

        return this.QRCodeImgUrl;
    }
    public void setQRCodeImgUrl(String QRCodeImgUrl){

        this.QRCodeImgUrl = QRCodeImgUrl;
    }
}
