package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_pro_order_recieved")
public class GrdProOrderRecievedEntity  implements java.io.Serializable{
	
   /**
	* 
	*/
	private static final long serialVersionUID = 6624758126802012568L;
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
    *货源ID
    */
    private String goodsId;
    /**
    *发布人姓名
    */
    private String publisher;
    /**
    *发布时间
    */
    private Date publisherTime;
    /**
    *接单时间
    */
    private Date recieveTime;
    /**
    *司机姓名
    */
    private String driverName;
    /**
    *确认时间
    */
    private Date confirmTime;
    /**
    *确认状态
    */
    private String confirmStatus;
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
    
    /**
    *线路类型(1干线 2同城)
    */
    private Integer sourceType;

    /**
    *发货地省份ID
    */
    private Integer s_provinceId;

    /**
    *发货地城市ID
    */
    private Integer s_cityId;

    /**
    *发货地区县ID
    */
    private Integer s_areaId;

    /**
    *发货地详细地址(省/市/区)
    */
    private String s_detail;

    /**
    *收货地省份ID
    */
    private Integer e_provinceId;

    /**
    *收货地城市ID
    */
    private Integer e_cityId;

    /**
    *收货地区县ID
    */
    private Integer e_areaId;

    /**
    *收货地详细地址(省/市/区)
    */
    private String e_detail;

    /**
    *重量
    */
    private Double totalWeight;
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
    @Column(name = "goodsId")
    public String getGoodsId(){
        return this.goodsId;
    }
    public void setGoodsId(String goodsId){
        this.goodsId = goodsId;
    }
    @Column(name = "publisher")
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    @Column(name = "publisherTime")
    public Date getPublisherTime(){
        return this.publisherTime;
    }
    public void setPublisherTime(Date publisherTime){
        this.publisherTime = publisherTime;
    }
    @Column(name = "recieveTime")
    public Date getRecieveTime(){
        return this.recieveTime;
    }
    public void setRecieveTime(Date recieveTime){
        this.recieveTime = recieveTime;
    }
    @Column(name = "driverName")
    public String getDriverName(){
        return this.driverName;
    }
    public void setDriverName(String driverName){
        this.driverName = driverName;
    }
    @Column(name = "confirmTime")
    public Date getConfirmTime(){
        return this.confirmTime;
    }
    public void setConfirmTime(Date confirmTime){
        this.confirmTime = confirmTime;
    }
    @Column(name = "confirmStatus")
    public String getConfirmStatus(){
        return this.confirmStatus;
    }
    public void setConfirmStatus(String confirmStatus){
        this.confirmStatus = confirmStatus;
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
    @Column(name = "sourceType")
    public Integer getSourceType(){

        return this.sourceType;
    }
    public void setSourceType(Integer sourceType){

        this.sourceType = sourceType;
    }
    @Column(name = "s_provinceId")
    public Integer getS_provinceId(){

        return this.s_provinceId;
    }
    public void setS_provinceId(Integer s_provinceId){

        this.s_provinceId = s_provinceId;
    }
    @Column(name = "s_cityId")
    public Integer getS_cityId(){

        return this.s_cityId;
    }
    public void setS_cityId(Integer s_cityId){

        this.s_cityId = s_cityId;
    }
    @Column(name = "s_areaId")
    public Integer getS_areaId(){

        return this.s_areaId;
    }
    public void setS_areaId(Integer s_areaId){

        this.s_areaId = s_areaId;
    }
    @Column(name = "s_detail")
    public String getS_detail(){

        return this.s_detail;
    }
    public void setS_detail(String s_detail){

        this.s_detail = s_detail;
    }
    @Column(name = "e_provinceId")
    public Integer getE_provinceId(){

        return this.e_provinceId;
    }
    public void setE_provinceId(Integer e_provinceId){

        this.e_provinceId = e_provinceId;
    }
    @Column(name = "e_cityId")
    public Integer getE_cityId(){

        return this.e_cityId;
    }
    public void setE_cityId(Integer e_cityId){

        this.e_cityId = e_cityId;
    }
    @Column(name = "e_areaId")
    public Integer getE_areaId(){

        return this.e_areaId;
    }
    public void setE_areaId(Integer e_areaId){

        this.e_areaId = e_areaId;
    }
    @Column(name = "e_detail")
    public String getE_detail(){

        return this.e_detail;
    }
    public void setE_detail(String e_detail){

        this.e_detail = e_detail;
    }
    @Column(name = "totalWeight")
    public Double getTotalWeight(){

        return this.totalWeight;
    }
    public void setTotalWeight(Double totalWeight){

        this.totalWeight = totalWeight;
    }
}
