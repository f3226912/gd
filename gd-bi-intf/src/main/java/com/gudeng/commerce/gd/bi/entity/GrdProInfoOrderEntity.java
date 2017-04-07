package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_pro_info_order")
public class GrdProInfoOrderEntity  implements java.io.Serializable{
	
   /**
	* 
    */
	private static final long serialVersionUID = 6624758126802012574L;
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
    *物流订单号
    */
    private String logistOrderNo;
    /**
    *物流公司名称
    */
    private String logistCompanyName;
    /**
    *司机姓名
    */
    private String driverName;
    /**
    *司机接单时间
    */
    private Date recieveTime;
    /**
    *物流公司确认时间
    */
    private Date confirmTime;
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
    
    private Date generateTime;
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
    @Column(name = "logistOrderNo")
    public String getLogistOrderNo(){
        return this.logistOrderNo;
    }
    public void setLogistOrderNo(String logistOrderNo){
        this.logistOrderNo = logistOrderNo;
    }
    @Column(name = "logistCompanyName")
    public String getLogistCompanyName(){
        return this.logistCompanyName;
    }
    public void setLogistCompanyName(String logistCompanyName){
        this.logistCompanyName = logistCompanyName;
    }
    @Column(name = "driverName")
    public String getDriverName(){
        return this.driverName;
    }
    public void setDriverName(String driverName){
        this.driverName = driverName;
    }
    @Column(name = "recieveTime")
    public Date getRecieveTime(){
        return this.recieveTime;
    }
    public void setRecieveTime(Date recieveTime){
        this.recieveTime = recieveTime;
    }
    @Column(name = "confirmTime")
    public Date getConfirmTime(){
        return this.confirmTime;
    }
    public void setConfirmTime(Date confirmTime){
        this.confirmTime = confirmTime;
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
    @Column(name = "generateTime")
    public Date getGenerateTime(){

        return this.generateTime;
    }
    public void setGenerateTime(Date generateTime){

        this.generateTime = generateTime;
    }
    
}
