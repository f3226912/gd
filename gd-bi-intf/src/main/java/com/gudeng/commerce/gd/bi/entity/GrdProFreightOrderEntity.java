package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_pro_freight_order")
public class GrdProFreightOrderEntity  implements java.io.Serializable{
	
   /**
	* 
	*/
	private static final long serialVersionUID = 6624758126802112564L;
    /**
    *
    */
    private Long id;
    /**
    *
    */
    private Long marketId;
    /**
    *
    */
    private String marketName;
    /**
    *团队id
    */
    private Integer teamId;
    /**
    *团队名称
    */
    private String teamName;
    /**
    *地推人员信息Id
    */
    private Integer grdId;
    /**
    *地推姓名
    */
    private String grdUserName;
    /**
    *地推人员手机号
    */
    private String grdMobile;
    /**
    *货运订单号
    */
    private String freightOrderNo;
    /**
    *承运人
    */
    private String reciever;
    /**
    *发运人
    */
    private String publisher;
    /**
    *订单生成时间
    */
    private Date generateTime;
    /**
    *订单状态
    */
    private String orderStatus;
    /**
    *支付状态
    */
    private String payStatus;
    /**
    *
    */
    private String createUserId;
    /**
    *
    */
    private Date createTime;
    /**
    *
    */
    private String updateUserId;
    /**
    *
    */
    private Date updateTime;
    @Id
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @Column(name = "marketId")
    public Long getMarketId(){
        return this.marketId;
    }
    public void setMarketId(Long marketId){
        this.marketId = marketId;
    }
    @Column(name = "marketName")
    public String getMarketName(){
        return this.marketName;
    }
    public void setMarketName(String marketName){
        this.marketName = marketName;
    }
    @Column(name = "teamId")
    public Integer getTeamId(){
        return this.teamId;
    }
    public void setTeamId(Integer teamId){
        this.teamId = teamId;
    }
    @Column(name = "teamName")
    public String getTeamName(){
        return this.teamName;
    }
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
    @Column(name = "grdId")
    public Integer getGrdId(){
        return this.grdId;
    }
    public void setGrdId(Integer grdId){
        this.grdId = grdId;
    }
    @Column(name = "grdUserName")
    public String getGrdUserName(){
        return this.grdUserName;
    }
    public void setGrdUserName(String grdUserName){
        this.grdUserName = grdUserName;
    }
    @Column(name = "grdMobile")
    public String getGrdMobile(){
        return this.grdMobile;
    }
    public void setGrdMobile(String grdMobile){
        this.grdMobile = grdMobile;
    }
    @Column(name = "freightOrderNo")
    public String getFreightOrderNo(){
        return this.freightOrderNo;
    }
    public void setFreightOrderNo(String freightOrderNo){
        this.freightOrderNo = freightOrderNo;
    }
    @Column(name = "reciever")
    public String getReciever(){
        return this.reciever;
    }
    public void setReciever(String reciever){
        this.reciever = reciever;
    }
    @Column(name = "publisher")
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    @Column(name = "generateTime")
    public Date getGenerateTime(){
        return this.generateTime;
    }
    public void setGenerateTime(Date generateTime){
        this.generateTime = generateTime;
    }
    @Column(name = "orderStatus")
    public String getOrderStatus(){
        return this.orderStatus;
    }
    public void setOrderStatus(String orderStatus){
        this.orderStatus = orderStatus;
    }
    @Column(name = "payStatus")
    public String getPayStatus(){
        return this.payStatus;
    }
    public void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
