package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;

/**
 * GrdGiftDetail entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "grd_gift_detail")
public class GrdGiftDetailEntity implements java.io.Serializable {
//	{"businessId":3862,"mobile":"13800138000","orderNo":"612016000012559","orderPrice":112,"orderTime":"2016-06-15 15:56:30","realName":"星安果","shopName":"景乐干鲜调料","type":"2"}
	// Fields

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 585003307979253089L;
	private Integer id;
	private String recordId;
	private String giftName;
	private String giftId;
	private Integer countNo;
	//@ExcelConf(excelHeader="订单号", sort = 4)
	private String orderNo;
	//@ExcelConf(excelHeader="交易时间", sort = 5)
	private Date orderTime;
	//@ExcelConf(excelHeader="实付金额", sort = 9)
	private Double orderPrice;
	private String type;
	private Date createTime;
	private Date updateTime;
	private String createUserId;
	private String updateUserId;
	private Integer businessId;
	//@ExcelConf(excelHeader="商铺", sort = 8)
    private String shopsName;
    /**
	 * 编码,由年+月+ x组合产生 如2016071 表示2016年7月上旬
	 */
	private Integer code;
    
    
    /**购买者手机号码
	 * 
	 */
	//@ExcelConf(excelHeader="买家手机", sort = 6)
	private String buyerMobile;
	/**购买者姓名
	 * 
	 */
	//@ExcelConf(excelHeader="买家姓名", sort = 7)
	private String buyerName;
	
	
    
	// Constructors
	@Column(name = "buyerMobile")
	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}
	@Column(name = "buyerName")
	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/** default constructor */
	public GrdGiftDetailEntity() {
	}

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "recordId")
	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	@Column(name = "giftName")
	public String getGiftName() {
		return this.giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	@Column(name = "giftId")
	public String getGiftId() {
		return this.giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	@Column(name = "countNo")
	public Integer getCountNo() {
		return this.countNo;
	}

	public void setCountNo(Integer countNo) {
		this.countNo = countNo;
	}

	@Column(name = "orderNo")
	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "orderTime", length = 10)
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name = "orderPrice", precision = 18)
	public Double getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Column(name = "type", length = 2)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime", length = 10)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateTime", length = 10)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "createUserId", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "updateUserId", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "businessId")
	public Integer getBusinessId() {

		return this.businessId;
	}

	public void setBusinessId(Integer businessId) {

		this.businessId = businessId;
	}

	@Column(name = "shopsName")
	public String getShopsName() {

		return this.shopsName;
	}

	public void setShopsName(String shopsName) {

		this.shopsName = shopsName;
	}
	
	@Column(name = "code")
	public Integer getCode() {
		
		return this.code;
	}
	
	public void setCode(Integer code) {
		
		this.code = code;
	}

}