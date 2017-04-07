package com.gudeng.commerce.gd.search.dto;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ProductQueryBean implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368674802786519308L;

	/**
	 * 产品id
	 */
	private Long id;

	/**
	 * 产品名称
	 */
	private String name;

	/**
	 * 产品发布时间
	 */
	private String createTime;

	/**
	 * 产品价格更新时间
	 */
	private String updatePriceTime;

	/**
	 * 产品发布时间范围
	 * 
	 * 如：7，15，30
	 */
	private Integer rangeDays;

	/**
	 * 产品分类id
	 */
	private Long cateId;

	/**
	 * 产品类型
	 * 
	 * 和 会员类别对应
	 * 
	 * 1谷登农批，创建的产品 roleType 为1，供普通买家购买时搜索使用
	 * 
	 * 4产地供应商，创建的产品，roleType 为4 ，供谷登农批商购买时搜索使用（在会员中心进行检索使用）
	 * 
	 */
	private String roleType;

	/**
	 * 产品分类名称
	 */
	private String cateName;

	/**
	 * 产品所在商铺id
	 */
	private Long businessId;

	/**
	 * 产品所在商铺名称
	 */
	private String shopsName;

	/**
	 * 查询产品价格范围（最低价）
	 * 
	 */
	private Double priceLowest;

	/**
	 * 查询产品价格范围（最高价）
	 * 
	 */
	private Double priceHighest;

	/**
	 * 价格区间 起始个数 buyCountStart - 终止个数buyCountEnd 使用价格 rangePrice
	 *
	 * buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。 如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 * 
	 */
	private List<Double> buyCountStart;

	/**
	 * 价格区间 起始个数 buyCountStart - 终止个数buyCountEnd 使用价格 rangePrice
	 *
	 * buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。 如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 * 
	 */
	private List<Double> buyCountEnd;

	/**
	 * 价格区间 起始个数 buyCountStart - 终止个数buyCountEnd 使用价格 rangePrice
	 *
	 * buyCountStart, buyCountEnd, rangePrice 三个价格相互对应。 如：buyCountStart.get[0],buyCountEnd.get[0],rangePrice.get[0],
	 * 
	 */
	private List<Double> rangePrice;

	/**
	 * 店铺经营模式，
	 * 
	 * 0 个人经营 1 企业经营
	 * 
	 */
	private Integer businessModel;

	/**
	 * 所在店铺的所在市场ID
	 * 
	 */
	private Long marketId;

	/**
	 * 所在店铺的所在市场名称
	 * 
	 */
	private String marketName;

	/**
	 * 排序栏位名称,和排序方式
	 * 
	 * 如：order.put("price","desc");
	 * 
	 */
	private Map<String, String> order;

	/**
	 * 分页返回结果，起始
	 * 
	 */
	private Integer startRow;

	/**
	 * 分页返回结果，每页个数
	 * 
	 */
	private Integer pageSize;
	/**
	 * 当前页
	 */
	private Integer pageNum;

	private Long provinceId;

	private Long cityId;

	private Long areaId;

	private Integer managementType;// 经营类型,1表示种养大户，2表示合作社，3表示基地

	private String productSign;// 商品标签

	private String facetField;// 智能提示的字段

	private String facetWord;// 智能提示的源词

	private String keyWord;// 智能提示的源词

	/**
	 * 产品分类ids
	 */
	private String cateIds;

	public ProductQueryBean() {
	}

	public ProductQueryBean(ServletRequest request) {
		this.id = StringUtils.isNotEmpty(request.getParameter("id")) ? Long.valueOf(request.getParameter("id")) : null;
		this.name = request.getParameter("name");
		this.createTime = request.getParameter("createTime");
		this.updatePriceTime = request.getParameter("updatePriceTime");
		this.rangeDays = StringUtils.isNotEmpty(request.getParameter("rangeDays")) ? Integer.valueOf(request.getParameter("rangeDays")) : null;
		this.cateId = StringUtils.isNotEmpty(request.getParameter("cateId")) ? Long.valueOf(request.getParameter("cateId")) : null;
		this.roleType = request.getParameter("roleType");
		this.cateName = request.getParameter("cateName");
		this.businessId = StringUtils.isNotEmpty(request.getParameter("businessId")) ? Long.valueOf(request.getParameter("businessId")) : null;
		this.shopsName = request.getParameter("shopsName");
		this.priceLowest = StringUtils.isNotEmpty(request.getParameter("priceLowest")) ? Double.valueOf(request.getParameter("priceLowest")) : null;
		this.priceHighest = StringUtils.isNotEmpty(request.getParameter("priceHighest")) ? Double.valueOf(request.getParameter("priceHighest")) : null;
		this.businessModel = StringUtils.isNotEmpty(request.getParameter("businessModel")) ? Integer.valueOf(request.getParameter("businessModel")) : null;
		this.marketId = StringUtils.isNotEmpty(request.getParameter("marketId")) ? Long.valueOf(request.getParameter("marketId")) : null;
		this.marketName = request.getParameter("marketName");
		this.startRow = StringUtils.isNotEmpty(request.getParameter("startRow")) ? Integer.valueOf(request.getParameter("startRow")) : null;
		this.pageSize = StringUtils.isNotEmpty(request.getParameter("pageSize")) ? Integer.valueOf(request.getParameter("pageSize")) : null;
		this.pageNum = StringUtils.isNotEmpty(request.getParameter("pageNum")) ? Integer.valueOf(request.getParameter("pageNum")) : null;
		this.provinceId = StringUtils.isNotEmpty(request.getParameter("provinceId")) ? Long.valueOf(request.getParameter("provinceId")) : null;
		this.cityId = StringUtils.isNotEmpty(request.getParameter("cityId")) ? Long.valueOf(request.getParameter("cityId")) : null;
		this.areaId = StringUtils.isNotEmpty(request.getParameter("areaId")) ? Long.valueOf(request.getParameter("areaId")) : null;
		this.managementType = StringUtils.isNotEmpty(request.getParameter("managementType")) ? Integer.valueOf(request.getParameter("managementType")) : null;
		this.productSign = request.getParameter("productSign");
		this.facetField = request.getParameter("facetField");
		this.facetWord = request.getParameter("facetWord");
		this.keyWord = request.getParameter("keyWord");
		this.cateIds = request.getParameter("cateIds");
	}

	public String getCateIds() {
		return cateIds;
	}

	public void setCateIds(String cateIds) {
		this.cateIds = cateIds;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getFacetWord() {
		return facetWord;
	}

	public void setFacetWord(String facetWord) {
		this.facetWord = facetWord;
	}

	public String getFacetField() {
		return facetField;
	}

	public void setFacetField(String facetField) {
		this.facetField = facetField;
	}

	public String getProductSign() {
		return productSign;
	}

	public void setProductSign(String productSign) {
		this.productSign = productSign;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getRangeDays() {
		return rangeDays;
	}

	public void setRangeDays(Integer rangeDays) {
		this.rangeDays = rangeDays;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public Double getPriceLowest() {
		return priceLowest;
	}

	public void setPriceLowest(Double priceLowest) {
		this.priceLowest = priceLowest;
	}

	public Double getPriceHighest() {
		return priceHighest;
	}

	public void setPriceHighest(Double priceHighest) {
		this.priceHighest = priceHighest;
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

	public Integer getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Map<String, String> getOrder() {
		return order;
	}

	public void setOrder(Map<String, String> order) {
		this.order = order;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getUpdatePriceTime() {
		return updatePriceTime;
	}

	public void setUpdatePriceTime(String updatePriceTime) {
		this.updatePriceTime = updatePriceTime;
	}
}
