package com.gudeng.commerce.gd.order.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 出场表
 * @author Ailen
 *
 */
@Entity(name = "order_outmarketinfo")
public class OrderOutmarketinfoEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -854357338287794414L;

	private Long omId;

    //查验员ID 对应member_baseInfo表
    private Integer controllerId;

    //车牌号
    private String carNumber;

    //车牌图片，四张图片（用 | 隔开）
    private String carNumberImage;

    //车辆载重状态
    private String carWeightStatus;

    //车辆类型
    private String type;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    //车辆ID 对应car_baseinfo表
    private Long carId;
    
    //车过磅ID
    private Long weighCarId;
    
    /**
     * 订单总重
     */
    private Double orderWeigh;
    
    @Id
    @Column(name = "omId")
	public Long getOmId() {
		return omId;
	}
	public void setOmId(Long omId) {
		this.omId = omId;
	}
    
 
    @Column(name = "weighCarId")
    public Long getWeighCarId() {
		return weighCarId;
	}

	public void setWeighCarId(Long weighCarId) {
		this.weighCarId = weighCarId;
	}
	@Column(name = "controllerId")
    public Integer getControllerId(){

        return this.controllerId;
    }
    public void setControllerId(Integer controllerId){

        this.controllerId = controllerId;
    }
    @Column(name = "carNumber")
    public String getCarNumber(){

        return this.carNumber;
    }
    public void setCarNumber(String carNumber){

        this.carNumber = carNumber;
    }
    @Column(name = "carNumberImage")
    public String getCarNumberImage(){

        return this.carNumberImage;
    }
    public void setCarNumberImage(String carNumberImage){

        this.carNumberImage = carNumberImage;
    }
    @Column(name = "carWeightStatus")
    public String getCarWeightStatus(){

        return this.carWeightStatus;
    }
    public void setCarWeightStatus(String carWeightStatus){

        this.carWeightStatus = carWeightStatus;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "carId")
    public Long getCarId(){

        return this.carId;
    }
    public void setCarId(Long carId){

        this.carId = carId;
    }
    
    @Column(name = "orderWeigh")
	public Double getOrderWeigh() {
		return orderWeigh;
	}
	public void setOrderWeigh(Double orderWeigh) {
		this.orderWeigh = orderWeigh;
	}
    
    
}


