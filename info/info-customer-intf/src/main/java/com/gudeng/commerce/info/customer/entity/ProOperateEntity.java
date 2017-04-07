package com.gudeng.commerce.info.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pro_operate")
public class ProOperateEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3914978975452398048L;

	private Long id;

    private Long reportsID;

    private String frequency;

    private Date datatimes;

    private Long webRegCount;

    private Long sumWebRegCount;
    
    private Double growthRate;

    private Long shopRegCount;

    private Long sumShopRegCount;

    private Long supplierRegCount;

    private Long sumSupplierRegCount;

    private Double supplierConShop;
    
    private Double growNsyRegCount;
    @Column(name = "growNsyRegCount")
    public Double getGrowNsyRegCount() {
		return growNsyRegCount;
	}
	public void setGrowNsyRegCount(Double growNsyRegCount) {
		this.growNsyRegCount = growNsyRegCount;
	}
	private Long nsyRegCount;

    private Long sumNsyRegCount;

    private Double nsyConShop;

    private Long nsyPhoneCount;

    private Long nsyPhoneConUser;

    private Long comRegCount;

    private Long sumComRegCount;

    private Long sumRealCount;

    private Long productCount;

    private Double shopAvProduct;

    private Long sumProductCount;

    private Long nstPeRegCount;

    private Long sumNstPeRegCount;

    private Long nstCpRegCount;

    private Long sumNstCpRegCount;

    private Double nstConCp;

    private Long sumNstRegCount;

    private Double nstPhoneConReg;

    private Long supplyCount;

    private Long sumSupplyCount;

    private Long lineCount;

    private Long sumLineCount;

    private Double supplyConLine;

    private Double sumSupplyConLine;

    private String state;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;

    @Id
   	@Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "reportsID")
    public Long getReportsID(){

        return this.reportsID;
    }
    @Column(name = "growthRate")
    public Double getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(Double growthRate) {
		this.growthRate = growthRate;
	}
	public void setReportsID(Long reportsID){
        this.reportsID = reportsID;
    }
    @Column(name = "frequency")
    public String getFrequency(){

        return this.frequency;
    }
    public void setFrequency(String frequency){

        this.frequency = frequency;
    }
    @Column(name = "datatimes")
    public Date getDatatimes(){

        return this.datatimes;
    }
    public void setDatatimes(Date datatimes){

        this.datatimes = datatimes;
    }
    @Column(name = "webRegCount")
    public Long getWebRegCount(){

        return this.webRegCount;
    }
    public void setWebRegCount(Long webRegCount){

        this.webRegCount = webRegCount;
    }
    @Column(name = "sumWebRegCount")
    public Long getSumWebRegCount(){

        return this.sumWebRegCount;
    }
    public void setSumWebRegCount(Long sumWebRegCount){

        this.sumWebRegCount = sumWebRegCount;
    }
    @Column(name = "shopRegCount")
    public Long getShopRegCount(){

        return this.shopRegCount;
    }
    public void setShopRegCount(Long shopRegCount){

        this.shopRegCount = shopRegCount;
    }
    @Column(name = "sumShopRegCount")
    public Long getSumShopRegCount(){

        return this.sumShopRegCount;
    }
    public void setSumShopRegCount(Long sumShopRegCount){

        this.sumShopRegCount = sumShopRegCount;
    }
    @Column(name = "supplierRegCount")
    public Long getSupplierRegCount(){

        return this.supplierRegCount;
    }
    public void setSupplierRegCount(Long supplierRegCount){

        this.supplierRegCount = supplierRegCount;
    }
    @Column(name = "sumSupplierRegCount")
    public Long getSumSupplierRegCount(){

        return this.sumSupplierRegCount;
    }
    public void setSumSupplierRegCount(Long sumSupplierRegCount){

        this.sumSupplierRegCount = sumSupplierRegCount;
    }
    @Column(name = "supplierConShop")
    public Double getSupplierConShop(){

        return this.supplierConShop;
    }
    public void setSupplierConShop(Double supplierConShop){

        this.supplierConShop = supplierConShop;
    }
    @Column(name = "nsyRegCount")
    public Long getNsyRegCount(){

        return this.nsyRegCount;
    }
    public void setNsyRegCount(Long nsyRegCount){

        this.nsyRegCount = nsyRegCount;
    }
    @Column(name = "sumNsyRegCount")
    public Long getSumNsyRegCount(){

        return this.sumNsyRegCount;
    }
    public void setSumNsyRegCount(Long sumNsyRegCount){

        this.sumNsyRegCount = sumNsyRegCount;
    }
    @Column(name = "nsyConShop")
    public Double getNsyConShop(){

        return this.nsyConShop;
    }
    public void setNsyConShop(Double nsyConShop){

        this.nsyConShop = nsyConShop;
    }
    @Column(name = "nsyPhoneCount")
    public Long getNsyPhoneCount(){

        return this.nsyPhoneCount;
    }
    public void setNsyPhoneCount(Long nsyPhoneCount){

        this.nsyPhoneCount = nsyPhoneCount;
    }
    @Column(name = "nsyPhoneConUser")
    public Long getNsyPhoneConUser(){

        return this.nsyPhoneConUser;
    }
    public void setNsyPhoneConUser(Long nsyPhoneConUser){

        this.nsyPhoneConUser = nsyPhoneConUser;
    }
    @Column(name = "comRegCount")
    public Long getComRegCount(){

        return this.comRegCount;
    }
    public void setComRegCount(Long comRegCount){

        this.comRegCount = comRegCount;
    }
    @Column(name = "sumComRegCount")
    public Long getSumComRegCount(){

        return this.sumComRegCount;
    }
    public void setSumComRegCount(Long sumComRegCount){

        this.sumComRegCount = sumComRegCount;
    }
    @Column(name = "sumRealCount")
    public Long getSumRealCount(){

        return this.sumRealCount;
    }
    public void setSumRealCount(Long sumRealCount){

        this.sumRealCount = sumRealCount;
    }
    @Column(name = "productCount")
    public Long getProductCount(){

        return this.productCount;
    }
    public void setProductCount(Long productCount){

        this.productCount = productCount;
    }
    @Column(name = "shopAvProduct")
    public Double getShopAvProduct(){

        return this.shopAvProduct;
    }
    public void setShopAvProduct(Double shopAvProduct){

        this.shopAvProduct = shopAvProduct;
    }
    @Column(name = "sumProductCount")
    public Long getSumProductCount(){

        return this.sumProductCount;
    }
    public void setSumProductCount(Long sumProductCount){

        this.sumProductCount = sumProductCount;
    }
    @Column(name = "nstPeRegCount")
    public Long getNstPeRegCount(){

        return this.nstPeRegCount;
    }
    public void setNstPeRegCount(Long nstPeRegCount){

        this.nstPeRegCount = nstPeRegCount;
    }
    @Column(name = "sumNstPeRegCount")
    public Long getSumNstPeRegCount(){

        return this.sumNstPeRegCount;
    }
    public void setSumNstPeRegCount(Long sumNstPeRegCount){

        this.sumNstPeRegCount = sumNstPeRegCount;
    }
    @Column(name = "nstCpRegCount")
    public Long getNstCpRegCount(){

        return this.nstCpRegCount;
    }
    public void setNstCpRegCount(Long nstCpRegCount){

        this.nstCpRegCount = nstCpRegCount;
    }
    @Column(name = "sumNstCpRegCount")
    public Long getSumNstCpRegCount(){

        return this.sumNstCpRegCount;
    }
    public void setSumNstCpRegCount(Long sumNstCpRegCount){

        this.sumNstCpRegCount = sumNstCpRegCount;
    }
    @Column(name = "nstConCp")
    public Double getNstConCp(){

        return this.nstConCp;
    }
    public void setNstConCp(Double nstConCp){

        this.nstConCp = nstConCp;
    }
    @Column(name = "sumNstRegCount")
    public Long getSumNstRegCount(){

        return this.sumNstRegCount;
    }
    public void setSumNstRegCount(Long sumNstRegCount){

        this.sumNstRegCount = sumNstRegCount;
    }
    @Column(name = "nstPhoneConReg")
    public Double getNstPhoneConReg(){

        return this.nstPhoneConReg;
    }
    public void setNstPhoneConReg(Double nstPhoneConReg){

        this.nstPhoneConReg = nstPhoneConReg;
    }
    @Column(name = "supplyCount")
    public Long getSupplyCount(){

        return this.supplyCount;
    }
    public void setSupplyCount(Long supplyCount){

        this.supplyCount = supplyCount;
    }
    @Column(name = "sumSupplyCount")
    public Long getSumSupplyCount(){

        return this.sumSupplyCount;
    }
    public void setSumSupplyCount(Long sumSupplyCount){

        this.sumSupplyCount = sumSupplyCount;
    }
    @Column(name = "lineCount")
    public Long getLineCount(){

        return this.lineCount;
    }
    public void setLineCount(Long lineCount){

        this.lineCount = lineCount;
    }
    @Column(name = "sumLineCount")
    public Long getSumLineCount(){

        return this.sumLineCount;
    }
    public void setSumLineCount(Long sumLineCount){

        this.sumLineCount = sumLineCount;
    }
    @Column(name = "supplyConLine")
    public Double getSupplyConLine(){

        return this.supplyConLine;
    }
    public void setSupplyConLine(Double supplyConLine){

        this.supplyConLine = supplyConLine;
    }
    @Column(name = "sumSupplyConLine")
    public Double getSumSupplyConLine(){

        return this.sumSupplyConLine;
    }
    public void setSumSupplyConLine(Double sumSupplyConLine){

        this.sumSupplyConLine = sumSupplyConLine;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "createUserID")
    public String getCreateUserID(){

        return this.createUserID;
    }
    public void setCreateUserID(String createUserID){

        this.createUserID = createUserID;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserID")
    public String getUpdateUserID(){

        return this.updateUserID;
    }
    public void setUpdateUserID(String updateUserID){

        this.updateUserID = updateUserID;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}
