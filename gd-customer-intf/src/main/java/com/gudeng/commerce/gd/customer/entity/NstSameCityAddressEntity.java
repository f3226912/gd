package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 农速通同城货源发布Entity
 * @author xiaojun
 */
@Entity(name = "nst_same_city_address")
public class NstSameCityAddressEntity  implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5333454940625174176L;
	/**
	 * 发布同城货源自增Id
	 */
    private Long id;
    /**
	 * 发货人Id
	 */
    private Long memberId;
    /**
	 * 所需车型(0小型面包,1金杯,2小型平板,3中型平板,4小型厢货,5大型厢货,6不限)
	 */
    private Byte needCarType;
    /**
	 * 发货地省份id
	 */
    private Integer s_provinceId;
    /**
	 * 发货地城市id
	 */
    private Integer s_cityId;
    /**
	 * 发货地区域id
	 */
    private Integer s_areaId;
    /**
	 * 发货地详情(包括省份，城市，区域 等中文)
	 */
    private String s_detail;
    /**
	 * 发货地详细地址(补充详情地址)
	 */
    private String s_detailed_address;
    /**
	 * 发货地经度
	 */
    private Double s_lng;
    /**
	 * 发货地纬度
	 */
    private Double s_lat;
    /**
	 * 收货地省份id
	 */
    private Integer f_provinceId;
    /**
	 * 收货地城市id
	 */
    private Integer f_cityId;
    /**
	 * 收货地区域id
	 */
    private Integer f_areaId;
    /**
	 * 收货地详情(包括省份，城市，区域 等中文)
	 */
    private String f_detail;
    /**
   	 * 收货地详情String(包括省份，城市，区域 等中文)
   	 */
    private String f_detailString;
    /**
	 * 收货地详细地址(补充详情地址)
	 */
    private String f_detailed_address;
    /**
	 * 收货地经度
	 */
    private Double f_lng;
    /**
	 * 发货地纬度
	 */
    private Double f_lat;
    /**
	 * 用车时间
	 */
    private String useCarTime;
    /**
	 * 意向价格
	 */
    private Double price;
    /**
	 * 货物类型 (0 普货,1 冷藏,2 生鲜水产,3 其他,4 重货,5 抛货,6 蔬菜,7 水果,8 农副产品,9 日用品,10纺织, 11 木材)
	 */
    private Byte goodsType;
    /**
	 * 总重量
	 */
    private Double totalWeight;
    /**
	 * 总量单位（0，吨） 默认吨
	 */
    private Integer hundredweight;
    /**
	 * 总体积
	 */
    private Integer totalSize;
    /**
	 * 里程（发货地和收货地经纬度计算所得）单位：km
	 */
    private Double mileage;
    /**
	 * 客户端来源 1谷登农批,2农速通,3农商友,4产地供应商,5门岗
	 */
    private Byte clients;
    /**
	 * 0,直发 1,代发
	 */
    private Byte nstRule;
    /**
	 * 分配人会员id
	 */
    private Long assignMemberId;
    /**
	 * 备注
	 */
    private String remark;
    /**
     * 货源发布时间
     */
    private Date releaseTime;
    /**
	 * 创建人员ID
	 */
    private String createUserId;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 修改人员ID
	 */
    private String updateUserId;
    /**
	 * 修改时间
	 */
    private Date updateTime;
    /**
	 * 是否删除(0:未删除;1:已删除)
	 */
    private Byte isDeleted;
    
    /**
	 * 发货省份名称
	 */
	private String s_provinceName;
	/**
	 * 发货地城市名称
	 */
	private String s_cityName;
	/**
	 * 发货地区域名称
	 */
	private String s_areaName;
	/**
	 * 收货地省份名称
	 */
	private String f_provinceName;
	/**
	 * 收货地城市名称
	 */
	private String f_cityName;
	/**
	 * 收货地区域名称
	 */
	private String f_areaName;
	
	/**
	 * 用车时间查询 字段
	 */
	private String useCarTimeString;
	/**
	 * 非农速通一手货源联系人
	 */
	private String contactName;
	
	@Column(name = "contactName")
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getF_detailString() {
		return f_detailString;
	}
	public void setF_detailString(String f_detailString) {
		this.f_detailString = f_detailString;
	}
	public String getUseCarTimeString() {
		return useCarTimeString;
	}
	public void setUseCarTimeString(String useCarTimeString) {
		this.useCarTimeString = useCarTimeString;
	}
	public String getS_provinceName() {
		return s_provinceName;
	}
	public void setS_provinceName(String s_provinceName) {
		this.s_provinceName = s_provinceName;
	}
	public String getS_cityName() {
		return s_cityName;
	}
	public void setS_cityName(String s_cityName) {
		this.s_cityName = s_cityName;
	}
	public String getS_areaName() {
		return s_areaName;
	}
	public void setS_areaName(String s_areaName) {
		this.s_areaName = s_areaName;
	}
	public String getF_provinceName() {
		return f_provinceName;
	}
	public void setF_provinceName(String f_provinceName) {
		this.f_provinceName = f_provinceName;
	}
	public String getF_cityName() {
		return f_cityName;
	}
	public void setF_cityName(String f_cityName) {
		this.f_cityName = f_cityName;
	}
	public String getF_areaName() {
		return f_areaName;
	}
	public void setF_areaName(String f_areaName) {
		this.f_areaName = f_areaName;
	}
    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "needCarType")
    public Byte getNeedCarType(){

        return this.needCarType;
    }
    public void setNeedCarType(Byte needCarType){

        this.needCarType = needCarType;
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
    @Column(name = "s_detailed_address")
    public String getS_detailed_address(){

        return this.s_detailed_address;
    }
    public void setS_detailed_address(String s_detailed_address){

        this.s_detailed_address = s_detailed_address;
    }
    @Column(name = "s_lng")
    public Double getS_lng(){

        return this.s_lng;
    }
    public void setS_lng(Double s_lng){

        this.s_lng = s_lng;
    }
    @Column(name = "s_lat")
    public Double getS_lat(){

        return this.s_lat;
    }
    public void setS_lat(Double s_lat){

        this.s_lat = s_lat;
    }
    @Column(name = "f_provinceId")
    public Integer getF_provinceId(){

        return this.f_provinceId;
    }
    public void setF_provinceId(Integer f_provinceId){

        this.f_provinceId = f_provinceId;
    }
    @Column(name = "f_cityId")
    public Integer getF_cityId(){

        return this.f_cityId;
    }
    public void setF_cityId(Integer f_cityId){

        this.f_cityId = f_cityId;
    }
    @Column(name = "f_areaId")
    public Integer getF_areaId(){

        return this.f_areaId;
    }
    public void setF_areaId(Integer f_areaId){

        this.f_areaId = f_areaId;
    }
    @Column(name = "f_detail")
    public String getF_detail(){

        return this.f_detail;
    }
    public void setF_detail(String f_detail){

        this.f_detail = f_detail;
    }
    @Column(name = "f_detailed_address")
    public String getF_detailed_address(){

        return this.f_detailed_address;
    }
    public void setF_detailed_address(String f_detailed_address){

        this.f_detailed_address = f_detailed_address;
    }
    @Column(name = "f_lng")
    public Double getF_lng(){

        return this.f_lng;
    }
    public void setF_lng(Double f_lng){

        this.f_lng = f_lng;
    }
    @Column(name = "f_lat")
    public Double getF_lat(){

        return this.f_lat;
    }
    public void setF_lat(Double f_lat){

        this.f_lat = f_lat;
    }
    @Column(name = "useCarTime")
    public String getUseCarTime(){

        return this.useCarTime;
    }
    public void setUseCarTime(String useCarTime){

        this.useCarTime = useCarTime;
    }
    @Column(name = "price")
    public Double getPrice(){

        return this.price;
    }
    public void setPrice(Double price){

        this.price = price;
    }
    @Column(name = "goodsType")
    public Byte getGoodsType(){

        return this.goodsType;
    }
    public void setGoodsType(Byte goodsType){

        this.goodsType = goodsType;
    }
    @Column(name = "totalWeight")
    public Double getTotalWeight(){

        return this.totalWeight;
    }
    public void setTotalWeight(Double totalWeight){

        this.totalWeight = totalWeight;
    }
    @Column(name = "hundredweight")
    public Integer getHundredweight(){

        return this.hundredweight;
    }
    public void setHundredweight(Integer hundredweight){

        this.hundredweight = hundredweight;
    }
    @Column(name = "totalSize")
    public Integer getTotalSize(){

        return this.totalSize;
    }
    public void setTotalSize(Integer totalSize){

        this.totalSize = totalSize;
    }
    @Column(name = "mileage")
    public Double getMileage(){

        return this.mileage;
    }
    public void setMileage(Double mileage){

        this.mileage = mileage;
    }
    @Column(name = "clients")
    public Byte getClients(){

        return this.clients;
    }
    public void setClients(Byte clients){

        this.clients = clients;
    }
    @Column(name = "nstRule")
    public Byte getNstRule(){

        return this.nstRule;
    }
    public void setNstRule(Byte nstRule){

        this.nstRule = nstRule;
    }
    @Column(name = "assignMemberId")
    public Long getAssignMemberId(){

        return this.assignMemberId;
    }
    public void setAssignMemberId(Long assignMemberId){

        this.assignMemberId = assignMemberId;
    }
    @Column(name = "releaseTime")
    public Date getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
	@Column(name = "remark")
    public String getRemark(){

        return this.remark;
    }
    public void setRemark(String remark){

        this.remark = remark;
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
    @Column(name = "isDeleted")
    public Byte getIsDeleted(){

        return this.isDeleted;
    }
    public void setIsDeleted(Byte isDeleted){

        this.isDeleted = isDeleted;
    }
}

