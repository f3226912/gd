package com.gudeng.commerce.gd.report.enm;

/**
 * 拨打电话页面枚举
 * @author TerryZhang
 */
public enum PhonePageCallEnum {

	NSY_INDEX_PAGE("INDEX", "农商友首页"),
	
	SHOP_LIST_PAGE("SYDP", "商铺列表页面"),
	
	SHOP_INDEX_PAGE("SPSY", "商铺首页"),
	
	SHOP_DETAIL_PAGE("DPXQ", "商铺详情页面"),
	
	ORDER_DETAIL_PAGE("DDXQ", "订单详情页面"),
	
	SHOP_FOCUS_PAGE("GZNPS", "农商友-农批商关注页面"),
	
	PRODUCT_FOCUS_PAGE("DPGZ", "商品关注页面"),
	
	CONTACT_PAGE("KHLXR", "联系人页面"),
	
	CGS_CUSTOMER_PAGE("CGSCUTM", "客户页面-采购商"),
	
	GYS_CUSTOMER_PAGE("GYSCUTM", "客户页面-供应商"),
	
	PRODUCT_SEARCH_PAGE("SSPROD", "搜索商品页面"),
	
	SHOP_SEARCH_PAGE("SSSHOP", "搜索商铺页面"),
	
	PRODUCT_DETAIL_PAGE("SPXQ", "商品详情页面"),
	
	GOODS_SUPPLIED_DETAIL_PAGE("HYXQ", "货源详情页面"),
	
	FIND_CARD_PAGE("WYZC", "我要找车页面"),
	
	FIND_GOODS_PAGE("WYZH", "我要找货页面"),
	
	GOOD_GOODS_PAGE("HHY", "好货源页面");

	PhonePageCallEnum(String shortName, String pageName) {
		this.shortName = shortName;
		this.pageName = pageName;
	}

	/**
	 * 根据页面缩写获取枚举对象
	 * @param shortName 页面缩写
	 * @return 如果存在，返回对应枚举对象，否则返回null
	 */
	public static PhonePageCallEnum getEnumByShortName(String shortName) {
		for (PhonePageCallEnum enm : PhonePageCallEnum.values()) {
			if (enm.getShortName().equals(shortName)) {
				return enm;
			}
		}
		return null;
	}
	
	/**
	 * 根据页面缩写获取页面名称
	 */
	public static String getPageNameByShortName(String shortName) {
		PhonePageCallEnum enm = getEnumByShortName(shortName);
		return enm == null ? null : enm.getPageName();
	}
	
	private String shortName;

	private String pageName;

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
