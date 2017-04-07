package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.bi.annotation.ExcelConf;

@Entity(name = "grd_pro_supplyofgood_handout")
public class GrdProSupplyofgoodHandoutEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5447457233101730244L;

	/**
    *
    */
    private Long id;

    *
    */
    private Long marketId;

    *
    */
    //@ExcelConf(excelHeader="所属市场", sort=1)
    private String marketName;

    *团队id
    */
    private Integer teamId;

    *团队名称
    */
    //@ExcelConf(excelHeader="团队名称", sort=2)
    private String teamName;

    *地推人员信息Id
    */
    private Integer grdId;

    *地推姓名
    */
    //@ExcelConf(excelHeader="地推姓名", sort=3)
    private String grdUserName;

    *地推人员手机号
    */
    //@ExcelConf(excelHeader="地推手机", sort=4)
    private String grdMobile;

    *货源ID
    */
    //@ExcelConf(excelHeader="所货源ID", sort=5)
    private String goodsId;

    *发布人姓名
    */
    //@ExcelConf(excelHeader="发布人姓名", sort=6)
    private String publisher;

    *发布人手机
    */
    //@ExcelConf(excelHeader="发布人手机", sort=7)
    private String mobile;

    *发布时间
    */
    private Date publisherTime;

    *货源状态
    */
    private String status;
    
    //@ExcelConf(excelHeader="货源状态", sort=8)
    private String statusText ;

    *
    */
    private String createUserId;

    *
    */
    private Date createTime;

    *
    */
    private String updateUserId;

    *
    */
    private Date updateTime;
    
    private Integer isDeleted;
    
    /**
     *线路类型(1干线 2同城)
     */
     private Integer sourceType;

    
    @Column(name = "isDeleted")
    public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getStatusText() {
    	if (status != null){
    		if (status.equalsIgnoreCase("1")){
    			statusText = "已发布";
    		}else if (status.equalsIgnoreCase("2")){
    			statusText = "待确认" ;
    		}else if (status.equalsIgnoreCase("3")){
    			statusText = "已接受" ;
    		}else if (status.equalsIgnoreCase("4")){
    			statusText = "已过期" ;
    		}else{
    			statusText = "" ;
    		}
    	}
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	@Id
    @Column(name = "id")
    public Long getId(){

    }
    public void setId(Long id){

    }
    @Column(name = "marketId")
    public Long getMarketId(){

    }
    public void setMarketId(Long marketId){

    }
    @Column(name = "marketName")
    public String getMarketName(){

    }
    public void setMarketName(String marketName){

    }
    @Column(name = "teamId")
    public Integer getTeamId(){

    }
    public void setTeamId(Integer teamId){

    }
    @Column(name = "teamName")
    public String getTeamName(){

    }
    public void setTeamName(String teamName){

    }
    @Column(name = "grdId")
    public Integer getGrdId(){

    }
    public void setGrdId(Integer grdId){

    }
    @Column(name = "grdUserName")
    public String getGrdUserName(){

    }
    public void setGrdUserName(String grdUserName){

    }
    @Column(name = "grdMobile")
    public String getGrdMobile(){

    }
    public void setGrdMobile(String grdMobile){

    }
    @Column(name = "goodsId")
    public String getGoodsId(){

    }
    public void setGoodsId(String goodsId){

    }
    @Column(name = "publisher")
    public String getPublisher(){

    }
    public void setPublisher(String publisher){

    }
    @Column(name = "mobile")
    public String getMobile(){

    }
    public void setMobile(String mobile){

    }
    @Column(name = "publisherTime")
    public Date getPublisherTime(){

    }
    public void setPublisherTime(Date publisherTime){

    }
    @Column(name = "status")
    public String getStatus(){

    }
    public void setStatus(String status){

    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
    
    @Column(name = "sourceType")
    public Integer getSourceType(){

        return this.sourceType;
    }
    public void setSourceType(Integer sourceType){

        this.sourceType = sourceType;
    }
}