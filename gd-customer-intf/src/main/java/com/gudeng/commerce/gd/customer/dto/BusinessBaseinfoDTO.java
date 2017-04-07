package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Comparator;

import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;

public class BusinessBaseinfoDTO extends BusinessBaseinfoEntity implements
		Serializable, Comparator<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 592823382848482389L;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 身份证实名制
	 */
	private String isIdCard;
	
	/**
	 * 认证状态
	 */
	private String cerifiStatus;
	
	/** 
	 * 会员状态
	 * */
	private String memberStatus;
	
	/** 
	 * 会员等级(null:普通会员，1:金牌供应商)
	 * */
	private String memberGrade;
	
	
	/** 
	 * 搜索关键字
	 * 
	 * */
	private String keyWord;
	
	/** 
	 * 服务项目
	 * 
	 * */
	private String cateNames;
	
	/**
	 * 主要分类
	 */
	private String cateName;
	
	private String memberId;
	
	/**
	 * 店铺下的产品总数
	 */
	private Integer prodCount ;
	
	private String comstatus;
	
	private String ccstatus;
	
	private String cbstatus;
	
	private String productCategoryStr;//主营、其他分类
	
	private Double tradingVolume; //商铺日交易额
	
	private String cpstatus;
	
	private String areaName;
	private String cityName;
	private String provinceName;
	
	
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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

	public Double getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(Double tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	public String getComstatus() {
		return comstatus;
	}

	public void setComstatus(String comstatus) {
		this.comstatus = comstatus;
	}

	public String getCcstatus() {
		return ccstatus;
	}

	public void setCcstatus(String ccstatus) {
		this.ccstatus = ccstatus;
	}

	public String getCbstatus() {
		return cbstatus;
	}

	public void setCbstatus(String cbstatus) {
		this.cbstatus = cbstatus;
	}

	public Integer getProdCount() {
		return prodCount;
	}

	public void setProdCount(Integer prodCount) {
		this.prodCount = prodCount;
	}

	public String getCateNames() {
		return cateNames;
	}

	public void setCateNames(String cateNames) {
		this.cateNames = cateNames;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	private Long categoryId;

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getCerifiStatus() {
		return cerifiStatus;
	}

	public void setCerifiStatus(String cerifiStatus) {
		this.cerifiStatus = cerifiStatus;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsIdCard() {
		return isIdCard;
	}

	public void setIsIdCard(String isIdCard) {
		this.isIdCard = isIdCard;
	}
	
	private String province;
	
	private String city;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	private String area;

	/**
	 * 页面显示经营模式
	 */
	private String businessModelStr;

	public String getBusinessModelStr() {
		if (this.businessModelStr == null) {
			if (getBusinessModel() != null) {
				switch (getBusinessModel()) {
				case 0:
					businessModelStr = "个人经营";
					break;
				case 1:
					businessModelStr = "企业经营";
					break;
//				case 2:
//					businessModelStr = "商业服务";
//					break;
//				case 3:
//					businessModelStr = "招商代理";
//					break;
//				case 4:
//					businessModelStr = "个体经营";
//					break;
//				case 5:
//					businessModelStr = "其他";
//					break;
				}
			}
		}
		return businessModelStr;
	}
//	经营模式(0个人经营，1企业经营)
	public void setBusinessModelStr(String businessModelStr) {
		this.businessModelStr = businessModelStr;
	}

	/**
	 * 所在街市名称
	 */
	private String marketName;
	
	/**
	 * 市场类型
	 */
	private String marketType;

	/**
	 * 所在街市ID
	 */
	private String marketId;

	/**
	 * 联系人
	 */
	private String contact;

	/**
	 * 电话
	 */
	private String telephone;

	/**
	 * email
	 */
	private String email;

	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 是否关注, 0为否(默认)，1为是
	 */
	private Integer isFocus = 0;
	
	
	/**
	 * 会员类别（1谷登农批，2农速通，3农批宝，4产地供应商，余待补）
	 * 
	 */
	private Integer level;

	
	public String updateTime_string;

	// 增加createTime的String类型，用于 mybaties操作数据库Date类型的insert和更新

	public String createTime_string;

	public String account;
	public String realName;
	public String nickName;
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}

	public String getUpdateTime_string() {
		return updateTime_string;
	}

	public void setUpdateTime_string(String updateTime_string) {
		this.updateTime_string = updateTime_string;
	}

	public String getCreateTime_string() {
		return createTime_string;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setCreateTime_string(String createTime_string) {
		this.createTime_string = createTime_string;
	}

	// 增加updateTime的String类型，用于 mybaties操作数据库Date类型的insert和更新

	public Integer getIsFocus() {
		return isFocus;
	}

	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getProductCategoryStr() {
		return productCategoryStr;
	}

	public void setProductCategoryStr(String productCategoryStr) {
		this.productCategoryStr = productCategoryStr;
	}

	public String getCpstatus() {
		return cpstatus;
	}

	public void setCpstatus(String cpstatus) {
		this.cpstatus = cpstatus;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	

}
