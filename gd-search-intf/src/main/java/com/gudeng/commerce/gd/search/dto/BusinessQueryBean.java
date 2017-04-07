package com.gudeng.commerce.gd.search.dto;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;

public class BusinessQueryBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6548359242143924959L;

	/**
	 * 所在店铺的所在市场ID
	 * 
	 */
	private Integer marketId;

	public Integer getMarketId() {
		return marketId;
	}

	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}

	/**
	 * 店铺经营模式，
	 * 
	 * 0 个人经营 1启用经营
	 * 
	 */
	private Integer businessModel;

	/**
	 * 产地供应商经营类型
	 * 
	 * 1表示种养大户 2表示合作社 3表示基地
	 * 
	 */
	private Integer managementType;

	/**
	 * 是否验证用户
	 * 
	 * 1 已验证 0 未验证 2 已驳回（当做未验证）
	 * 
	 */
	private Integer status;

	/**
	 * 商铺名称
	 * 
	 */
	private String shopsName;

	/**
	 * 
	 * 
	 * */
	private String mainProduct;

	/**
	 * 商铺经营分类名称（分类）
	 * 
	 */
	private String cateName;

	/**
	 * 商铺经营分类ID（分类ID）
	 * 
	 */
	private Integer categoryId;

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
	 * 
	 * 会员类别（1谷登农批，2农速通，3农批宝，4产地供应商，余待补）
	 * 
	 */
	private Integer level;

	private String provinceId;

	/**
	 * 城市ID
	 * 
	 */
	private String cityId;

	/**
	 * 县ID
	 * 
	 */
	private String areaId;

	/**
	 * 是否APP的搜索
	 */
	private boolean isApp;

	public BusinessQueryBean() {
	}

	public BusinessQueryBean(ServletRequest request) {
		this.marketId = StringUtils.isNotEmpty(request.getParameter("marketId")) ? Integer.valueOf(request.getParameter("marketId")) : null;
		this.businessModel = StringUtils.isNotEmpty(request.getParameter("businessModel")) ? Integer.valueOf(request.getParameter("businessModel")) : null;
		this.managementType = StringUtils.isNotEmpty(request.getParameter("managementType")) ? Integer.valueOf(request.getParameter("managementType")) : null;
		this.status = StringUtils.isNotEmpty(request.getParameter("status")) ? Integer.valueOf(request.getParameter("status")) : null;
		this.shopsName = request.getParameter("shopsName");
		this.mainProduct = request.getParameter("mainProduct");
		this.cateName = request.getParameter("cateName");
		this.categoryId = StringUtils.isNotEmpty(request.getParameter("categoryId")) ? Integer.valueOf(request.getParameter("categoryId")) : null;
		this.startRow = StringUtils.isNotEmpty(request.getParameter("startRow")) ? Integer.valueOf(request.getParameter("startRow")) : null;
		this.pageSize = StringUtils.isNotEmpty(request.getParameter("pageSize")) ? Integer.valueOf(request.getParameter("pageSize")) : null;
		this.level = StringUtils.isNotEmpty(request.getParameter("level")) ? Integer.valueOf(request.getParameter("level")) : null;
		this.provinceId = request.getParameter("provinceId");
		this.cityId = request.getParameter("cityId");
		this.areaId = request.getParameter("areaId");
	}

	public void setIsApp(boolean isApp) {
		this.isApp = isApp;
	}

	public boolean getIsApp() {
		return isApp;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

}
