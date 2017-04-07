package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Entity;

@Entity(name = "nst_same_city_carline")
public class NstSameCityCarlineEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5193672951051116965L;
	
	private Long id;//主键
	
	private Long carId;//车辆id
	
	private Long memberId;//发布线路的会员id
	
	private Integer s_provinceId;//出发地省份id
	
	private Integer s_cityId;//出发地城市id
	
	private Integer s_areaId;//出发地区域id
	
	private String s_detail;//发货地详情(包括省份，城市，区域中文)
	
	private Double s_lng;//出发地经度
	
	private Double s_lat;//出发地纬度
	
	private Integer e_provinceId;//目的地省份id
	
	private Integer e_cityId;//目的地城市id
	
	private Integer e_areaId;//目的地区域id
	
	private String e_detail;//目的地详情(包括省份，城市，区域中文)
	
	private Double e_lng;//目的地经度
	
	private Double e_lat;//目的地纬度
	
	private String sendGoodsType;//发货方式 0.零担，1.整车，2不限
	
	private Double price;//意向价格
	
	private String remark;//备注
	
	private String createUserId;//创建人员ID
	
	private Date createTime;//创建时间
	
	private String updateUserId;//修改人员ID
	
	private Date updateTime;//修改时间
	
	private String isDeleted;//是否删除(0:未删除;1:已删除)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(Integer s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public Integer getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(Integer s_cityId) {
		this.s_cityId = s_cityId;
	}

	public Integer getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(Integer s_areaId) {
		this.s_areaId = s_areaId;
	}

	public String getS_detail() {
		return s_detail;
	}

	public void setS_detail(String s_detail) {
		this.s_detail = s_detail;
	}

	public Double getS_lng() {
		return s_lng;
	}

	public void setS_lng(Double s_lng) {
		this.s_lng = s_lng;
	}

	public Double getS_lat() {
		return s_lat;
	}

	public void setS_lat(Double s_lat) {
		this.s_lat = s_lat;
	}

	public Integer getE_provinceId() {
		return e_provinceId;
	}

	public void setE_provinceId(Integer e_provinceId) {
		this.e_provinceId = e_provinceId;
	}

	public Integer getE_cityId() {
		return e_cityId;
	}

	public void setE_cityId(Integer e_cityId) {
		this.e_cityId = e_cityId;
	}

	public Integer getE_areaId() {
		return e_areaId;
	}

	public void setE_areaId(Integer e_areaId) {
		this.e_areaId = e_areaId;
	}

	public String getE_detail() {
		return e_detail;
	}

	public void setE_detail(String e_detail) {
		this.e_detail = e_detail;
	}

	public Double getE_lng() {
		return e_lng;
	}

	public void setE_lng(Double e_lng) {
		this.e_lng = e_lng;
	}

	public Double getE_lat() {
		return e_lat;
	}

	public void setE_lat(Double e_lat) {
		this.e_lat = e_lat;
	}

	public String getSendGoodsType() {
		return sendGoodsType;
	}

	public void setSendGoodsType(String sendGoodsType) {
		this.sendGoodsType = sendGoodsType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

}
