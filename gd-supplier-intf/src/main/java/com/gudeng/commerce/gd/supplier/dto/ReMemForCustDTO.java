package com.gudeng.commerce.gd.supplier.dto;

import java.io.Serializable;
import java.util.List;

import com.gudeng.commerce.gd.supplier.entity.ReMemForCustEntity;

/**
 * 客户信息DTO
 * @author Ailen
 *
 */
public class ReMemForCustDTO extends ReMemForCustEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2767411036353747272L;

	private Long businessId;
	
	private String searchName;
	
	private String realName;
	
	private String baseMobile;
	
	private String marketName;
	
	private String shopsName;
	
	private String mainProduct;
	
	private String shopsDesc;
	
	private List<ReCustInfoDTO> addresses;
	
	private List<ReCustInfoDTO> mobiles;
	
	private List<ProductCategoryDTO> categorys;
	
	private String cityName;
	
	private String provinceName;
	
	private String areaName;
	
	private Integer businessModel;	//经营模式(0个人经营，1企业经营)
	
	private String businessModelStr;
	
	private Integer managementType;
	
	private String managementTypeStr;	//经营类型，1表示种养大户，2表示合作社，3表示基地  
	
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManagementTypeStr() {
		if(managementTypeStr==null&&managementType!=null) {
			if(managementType == 1) {
				this.managementTypeStr = "种养大户";
			} else if(managementType == 2) {
				this.managementTypeStr = "合作社";
			}
			 else if(managementType == 3) {
				 this.managementTypeStr = "基地";
			}
		}
		
		return this.managementTypeStr;
	}

	public void setManagementTypeStr(String managementTypeStr) {
		this.managementTypeStr = managementTypeStr;
	}

	public void setBusinessModelStr(String businessModelStr) {
		this.businessModelStr = businessModelStr;
	}

	public Integer getManagementType() {
		return managementType;
	}

	public void setManagementType(Integer managementType) {
		this.managementType = managementType;
	}

	public Integer getBusinessModel() {
		return businessModel;
	}

	public void setBusinessModel(Integer businessModel) {
		this.businessModel = businessModel;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public List<ProductCategoryDTO> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<ProductCategoryDTO> categorys) {
		this.categorys = categorys;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBaseMobile() {
		return baseMobile;
	}

	public void setBaseMobile(String baseMobile) {
		this.baseMobile = baseMobile;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public String getShopsDesc() {
		return shopsDesc;
	}

	public void setShopsDesc(String shopsDesc) {
		this.shopsDesc = shopsDesc;
	}

	public List<ReCustInfoDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<ReCustInfoDTO> addresses) {
		this.addresses = addresses;
	}

	public List<ReCustInfoDTO> getMobiles() {
		return mobiles;
	}

	public void setMobiles(List<ReCustInfoDTO> mobiles) {
		this.mobiles = mobiles;
	}

	public String getBusinessModelStr() {
		if(businessModelStr==null&&businessModel!=null) {
			if(businessModel == 0) {
				this.businessModelStr = "个人经营";
			} else if(businessModel == 1) {
				this.businessModelStr = "企业经营";
			}
		}
		return this.businessModelStr;
	}
}
