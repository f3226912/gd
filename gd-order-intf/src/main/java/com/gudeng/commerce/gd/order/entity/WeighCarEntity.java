package com.gudeng.commerce.gd.order.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 过磅表
 * @author Ailen
 *
 */
@Entity(name = "weigh_car")
public class WeighCarEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -551024315356586218L;

	//ID
	private Long weighCarId;

	//用户ID
    private Long memberId;

    //车辆ID reference carBaseInfo
    private Long carId;

    //皮重（吨）
    private Double tare;
    
    //总重（吨）
    private Double totalWeight;
    
    //净重（吨）
    private Double netWeight;
    
	//类型（1、货主商，2、采购商）
    private String type;
    
    private String tapWeight; //称重标识符 1：皮重为空、2：总重为空、3：皮重/总重为空、4：皮总全过磅
    
    private String place; //场地
    
    private String quality; //质量，品质 1:优,2:良,3:中,4:差
    
    private String allWeigh; //满载 1:是,2:否
    
    private String others; //其他物品
    
    private String status; //过磅状态 0:未进场,1:已进场,2:已出场
    
    private Date totalCreateTime; //总重称重时间

    private String tareUnit; //皮重单位（预留）
    
    private String totalUnit; //总重单位（预留）
    
    private Long tareMemberId; //皮重查验员ID
    
    private Long totalMemberId; //总重查验员ID
    
    private Date tareCreateTime; //皮重创建时间
    
    private String isFinish; //是否完成: 1:已完成, 2:未完成
    
    private Double orderWeigh; //订单总重
    
    private Long marketId; //市场ID
    
    private String carNumberImage; //车牌图片
    
    private String weighType; //系统默认称重值类型 1：0% 2:30% 3:50% 4:100%
    
    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    @Column(name="weighType")
    public String getWeighType() {
		return weighType;
	}
	public void setWeighType(String weighType) {
		this.weighType = weighType;
	}
	@Column(name="carNumberImage")
    public String getCarNumberImage() {
		return carNumberImage;
	}
	public void setCarNumberImage(String carNumberImage) {
		this.carNumberImage = carNumberImage;
	}
	@Column(name="marketId")
    public Long getMarketId() {
		return marketId;
	}
	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}
	@Column(name="isFinish")
    public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	@Column(name="tareCreateTime")
    public Date getTareCreateTime() {
		return tareCreateTime;
	}
	public void setTareCreateTime(Date tareCreateTime) {
		this.tareCreateTime = tareCreateTime;
	}
	@Column(name="status")
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="totalCreateTime")
	public Date getTotalCreateTime() {
		return totalCreateTime;
	}
	public void setTotalCreateTime(Date totalCreateTime) {
		this.totalCreateTime = totalCreateTime;
	}
	@Column(name="place")
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	@Column(name="quality")
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	@Column(name="allWeigh")
	public String getAllWeigh() {
		return allWeigh;
	}
	public void setAllWeigh(String allWeigh) {
		this.allWeigh = allWeigh;
	}
	@Column(name="others")
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	@Column(name="tapWeight")
    public String getTapWeight() {
		return tapWeight;
	}
	public void setTapWeight(String tapWeight) {
		this.tapWeight = tapWeight;
	}
	@Column(name="tareUnit")
	public String getTareUnit() {
		return tareUnit;
	}
	public void setTareUnit(String tareUnit) {
		this.tareUnit = tareUnit;
	}
	@Column(name="totalUnit")
	public String getTotalUnit() {
		return totalUnit;
	}
	public void setTotalUnit(String totalUnit) {
		this.totalUnit = totalUnit;
	}
	@Column(name="tareMemberId")
	public Long getTareMemberId() {
		return tareMemberId;
	}
	public void setTareMemberId(Long tareMemberId) {
		this.tareMemberId = tareMemberId;
	}
	@Column(name="totalMemberId")
	public Long getTotalMemberId() {
		return totalMemberId;
	}
	public void setTotalMemberId(Long totalMemberId) {
		this.totalMemberId = totalMemberId;
	}
	@Id
    @Column(name = "weighCarId")
    public Long getWeighCarId(){

        return this.weighCarId;
    }
    public void setWeighCarId(Long weighCarId){

        this.weighCarId = weighCarId;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "carId")
    public Long getCarId(){

        return this.carId;
    }
    public void setCarId(Long carId){

        this.carId = carId;
    }
    @Column(name = "tare")
    public Double getTare() {
		return tare;
	}
	public void setTare(Double tare) {
		this.tare = tare;
	}
	
	@Column(name = "totalWeight")
	public Double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}
	
	@Column(name = "netWeight")
	public Double getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
//    @Column(name="orderWeigh")
    public Double getOrderWeigh() {
		return orderWeigh;
	}
	public void setOrderWeigh(Double orderWeigh) {
		this.orderWeigh = orderWeigh;
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


