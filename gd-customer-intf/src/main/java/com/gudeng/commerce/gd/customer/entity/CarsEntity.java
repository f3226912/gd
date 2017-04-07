package com.gudeng.commerce.gd.customer.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "cars")
public class  CarsEntity   implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userId;

    private String phone;

    private String carNumber;

    private String carType;

    private String category;

    private Double maxLoad;

    private Double minPrice;

    private String minDescription;

    private Double price;

    private String priceDescription;

    private String products;

    private Long provinceId;

    private Long cityId;

    private Long areaId;

    private Double carLength;
    
    
	private String createUserId;
	
	private String updateUserId;

	private Date createTime;

	private Date updateTime;
	
    private Long entUserId;
	
    //农速通行驶证图片地址
    private String nst_vehiclePhotoUrl;
    //农速通驾驶证图片地址
    private String nst_driverPhotoUrl;
    
    //运输类型(0:干线，1：同城)
	private Integer transportType;
	
	//同城运输车辆类型:0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货
	private Integer transportCarType;
	
	private Integer isDeleted;

    

    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "userId")
    public Long getUserId(){

        return this.userId;
    }
    public void setUserId(Long userId){

        this.userId = userId;
    }
	
	@Column(name = "phone")
    public String getPhone(){

        return this.phone;
    }
    public void setPhone(String phone){

        this.phone = phone;
    }
    @Column(name = "carNumber")
    public String getCarNumber(){

        return this.carNumber;
    }
    public void setCarNumber(String carNumber){

        this.carNumber = carNumber;
    }
    @Column(name = "carType")
    public String getCarType(){

        return this.carType;
    }
    public void setCarType(String carType){

        this.carType = carType;
    }
    @Column(name = "category")
    public String getCategory(){

        return this.category;
    }
    public void setCategory(String category){

        this.category = category;
    }
    @Column(name = "maxLoad")
    public Double getMaxLoad(){

        return this.maxLoad;
    }
    public void setMaxLoad(Double maxLoad){

        this.maxLoad = maxLoad;
    }
    @Column(name = "minPrice")
    public Double getMinPrice(){

        return this.minPrice;
    }
    public void setMinPrice(Double minPrice){

        this.minPrice = minPrice;
    }
    @Column(name = "minDescription")
    public String getMinDescription(){

        return this.minDescription;
    }
    public void setMinDescription(String minDescription){

        this.minDescription = minDescription;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "priceDescription")
    public String getPriceDescription(){

        return this.priceDescription;
    }
    public void setPriceDescription(String priceDescription){

        this.priceDescription = priceDescription;
    }
    @Column(name = "products")
    public String getProducts(){

        return this.products;
    }
    public void setProducts(String products){

        this.products = products;
    }
    @Column(name = "provinceId")
    public Long getProvinceId(){

        return this.provinceId;
    }
    public void setProvinceId(Long provinceId){

        this.provinceId = provinceId;
    }
    @Column(name = "cityId")
    public Long getCityId(){

        return this.cityId;
    }
    public void setCityId(Long cityId){

        this.cityId = cityId;
    }
    @Column(name = "areaId")
    public Long getAreaId(){

        return this.areaId;
    }
    public void setAreaId(Long areaId){

        this.areaId = areaId;
    }
    @Column(name = "carLength")
    public Double getCarLength(){

        return this.carLength;
    }
    public void setCarLength(Double carLength){

        this.carLength = carLength;
    }
    
    @Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	 @Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	 @Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
    @Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
    @Column(name = "entUserId")
	public Long getEntUserId() {
		return entUserId;
	}
	public void setEntUserId(Long entUserId) {
		this.entUserId = entUserId;
	}
	@Column(name = "nst_vehiclePhotoUrl")
	public String getNst_vehiclePhotoUrl() {
		return nst_vehiclePhotoUrl;
	}
	public void setNst_vehiclePhotoUrl(String nst_vehiclePhotoUrl) {
		this.nst_vehiclePhotoUrl = nst_vehiclePhotoUrl;
	}
	
	@Column(name = "nst_driverPhotoUrl")
	public String getNst_driverPhotoUrl() {
		return nst_driverPhotoUrl;
	}
	public void setNst_driverPhotoUrl(String nst_driverPhotoUrl) {
		this.nst_driverPhotoUrl = nst_driverPhotoUrl;
	}
	
	@Column(name = "transportType")
	public Integer getTransportType() {
		return transportType;
	}
	public void setTransportType(Integer transportType) {
		this.transportType = transportType;
	}
	
	@Column(name = "transportCarType")
	public Integer getTransportCarType() {
		return transportCarType;
	}
	public void setTransportCarType(Integer transportCarType) {
		this.transportCarType = transportCarType;
	}
	
	@Column(name = "isDeleted")
	public Integer  getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
    
    
}


