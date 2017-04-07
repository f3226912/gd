package com.gudeng.commerce.gd.search.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.solr.client.solrj.beans.Field;



/**
 * 
 * 包含 基础信息  ，包括店铺id，分类id等
 * 
 * 
 */
public class ProductSolrDTO   implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4644673032414358747L;

	/**
	 * 产品id 
	 */
	@Field("id")
	public Integer id;

	/**
	 * 产品名称 
	 */
	@Field("name")
	public String name;
	
	/**
	 * 产品发布时间
	 */
	@Field("createTime")
	public Date createTime;
	
	/**
	 * 产品价格更新时间
	 */
	@Field("updatePriceTime")
	public Date updatePriceTime;
	
	/**
	 * 产品更新时间
	 */
	@Field("updateTime")
	public Date updateTime;
	
	/**
	 * 产品分类id 
	 */
	@Field("cateId")
	public List<Integer> cateId;
	/**
	 * 产品分类名称 
	 */
	@Field("cateName")
	public String cateName;

	/**
	 * 产品所在商铺id 
	 */
	@Field("businessId")
	public Integer businessId;

	/**
	 * 产品所在商铺名称
	 */
	@Field("shopsName")
	public String shopsName;

	
	/**
	 * 产品价格，对应产品表中的最低价格
	 *  
	 */
	@Field("price")
	public Double price;
	
	
	/**
	 * 价格类型
	 *  
	 */
	@Field("priceType")
	public Integer priceType;
	
	/**
	 * 价格类型
	 *  
	 */
	@Field("unit")
	public Integer unit;
	
	

	/**
	 * 价格区间  起始个数  buyCountStart - 终止个数buyCountEnd   使用价格 rangePrice
	 *
	 *  buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。
	 *  如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 *  
	 */
	@Field("buyCountStart")
	public List<Double> buyCountStart;

	/**
	 * 价格区间  起始个数  buyCountStart - 终止个数buyCountEnd   使用价格 rangePrice
	 *
	 *  buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。
	 *  如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 *  
	 */
	@Field("buyCountEnd")
	public List<Double> buyCountEnd;

	/**
	 * 价格区间  起始个数  buyCountStart - 终止个数buyCountEnd   使用价格 rangePrice
	 *
	 *  buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。
	 *  如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 *  
	 */
	@Field("rangePrice")
	public List<Double> rangePrice;


	/**
	 * 店铺经营模式，
	 * 
	 * 0 个人经营
	 * 1启用经营
	 * 
	 */
	@Field("bmodel")
	public Integer businessModel;
	
	/**
	 * 所在店铺的所在市场ID
	 * 
	 * */
	@Field("marketId")
	public Integer marketId;
	
	/**
	 * 所在店铺的所在市场名称
	 * 
	 * */
	@Field("marketName")
	public String marketName;
	
	/**
	 * 产品原图地址
	 * 
	 * */
	@Field("urlOrg")
	public String urlOrg;
	
	
	/**
	 * 产品类型
	 * 
	 * 和 会员类别对应
	 * 
	 * 1谷登农批，创建的产品 roleType 为1，供普通买家购买时搜索使用
	 *  
	 * 4产地供应商，创建的产品，roleType 为4 ，供谷登农批商购买时搜索使用（在会员中心进行检索使用）
	 * 
	 * */
	@Field("roleType")
	public String roleType;
	
	/**
	 * 所对应会员的联系方式
	 * 
	 * */
	@Field("mobile")
	public String mobile;
	
	/**
	 * 会员等级 1黄金会员 
	 */
	@Field("memberGrade")
	public Integer memberGrade;

	/**
	 * 单位名称
	 * 
	 * */
	@Field("unitName")
	public String unitName;
	
	/**
	 * APP图 170*170规格
	 * 
	 * */
	@Field("url170")
	public String url170;
	
	
	/**
	 * 产品有广告的时候，取得广告图
	 * 
	 * */
	@Field("addPicUrl")
	public String addPicUrl;
	

	/**
	 * 取得推荐的个数，有则大于0
	 * 
	 * */
	@Field("addCount")
	public Integer addCount;
	
	@Field("provinceId")
	public Integer provinceId;
	
	@Field("cityId")
	public Integer cityId;
	
	@Field("areaId")
	public Integer areaId;
	
	@Field("managementType")
	public Integer managementType;
	
	@Field("productSign")
	public List<String> productSign;
	
	/**
	 * 销量
	 */
	@Field("sales")
	public Double sales;
	
	/**
	 * 产地标识认证
	 */
	@Field("certifstatus")
	public Integer certifstatus;
	/**
	 * 企业认证
	 */
	@Field("comstatus")
	public Integer comstatus;
	/**
	 * 合作社认证
	 */
	@Field("ccstatus")
	public Integer ccstatus;
	/**
	 * 基地认证 
	 */
	@Field("cbstatus")
	public Integer cbstatus;
	
	
	public Integer getCertifstatus() {
		return certifstatus;
	}

	public void setCertifstatus(Integer certifstatus) {
		this.certifstatus = certifstatus;
	}

	public Integer getComstatus() {
		return comstatus;
	}

	public void setComstatus(Integer comstatus) {
		this.comstatus = comstatus;
	}

	public Integer getCcstatus() {
		return ccstatus;
	}

	public void setCcstatus(Integer ccstatus) {
		this.ccstatus = ccstatus;
	}

	public Integer getCbstatus() {
		return cbstatus;
	}

	public void setCbstatus(Integer cbstatus) {
		this.cbstatus = cbstatus;
	}

	public Double getSales() {
		return sales;
	}

	public void setSales(Double sales) {
		this.sales = sales;
	}

	public Date getUpdatePriceTime() {
		return updatePriceTime;
	}

	public void setUpdatePriceTime(Date updatePriceTime) {
		this.updatePriceTime = updatePriceTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getProductSign() {
		return productSign;
	}

	public void setProductSign(List<String> productSign) {
		this.productSign = productSign;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	public Integer getAddCount() {
		return addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUrl170() {
		return url170;
	}

	public void setUrl170(String url170) {
		this.url170 = url170;
	}

	public String getAddPicUrl() {
		return addPicUrl;
	}

	public void setAddPicUrl(String addPicUrl) {
		this.addPicUrl = addPicUrl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Integer getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(Integer memberGrade) {
		this.memberGrade = memberGrade;
	}
	
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getUrlOrg() {
		return urlOrg;
	}

	public void setUrlOrg(String urlOrg) {
		this.urlOrg = urlOrg;
	}

	public Integer getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

 

	public Date getCreateTime() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");  
//	    System.out.println("format前......................................."+sdf.format(createTime));
//	    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//	    System.out.println("format后......................................."+sdf.format(createTime));
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Double> getBuyCountStart() {
		return buyCountStart;
	}

	public void setBuyCountStart(List<Double> buyCountStart) {
		this.buyCountStart = buyCountStart;
	}

	public List<Double> getBuyCountEnd() {
		return buyCountEnd;
	}

	public void setBuyCountEnd(List<Double> buyCountEnd) {
		this.buyCountEnd = buyCountEnd;
	}
 

	public List<Double> getRangePrice() {
		return rangePrice;
	}

	public void setRangePrice(List<Double> rangePrice) {
		this.rangePrice = rangePrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getCateId() {
		return cateId;
	}

	public void setCateId(List<Integer> cateId) {
		this.cateId = cateId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	
}
