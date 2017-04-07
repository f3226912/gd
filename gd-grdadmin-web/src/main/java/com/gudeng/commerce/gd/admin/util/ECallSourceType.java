package com.gudeng.commerce.gd.admin.util;

/**
 *拨打电话来源
 * @author xiaojun
 */
public enum ECallSourceType {
	INDEX("INDEX","首页"),
	HYXQ("HYXQ","货源详情"),
	WYZC("WYZC","我要找车"),
	HYFB("HYFB"," 货源发布"),
	DPGZ("DPGZ","单品关注"),
	SYDP("SYDP","所有店铺（农批商）列表"),
	DPXQ("DPXQ","店铺详情"),
	GZNPS("GZNPS","关注的农批商"),
	WYZH("WYZH"," 我要找货"),
	DDXQ("DDXQ","订单详情"),
	KHLXR("KHLXR","客户联系人"),
	TJ("TJ","推荐"),
	HHY("HHY","好货源"),
	SPSY("SPSY","商铺首页"),
	KHLB("KHLB","客户列表"),
	TCWYZH("TCWYZH","同城我要找货"),
	TCWYZC("TCWYZC","同城我要找车"),
	NSTORDER("NSTORDER","农速通订单"),
	TCNSTORDER("TCNSTORDER","同城农速通订单"),
	GZPROD("GZPROD","商品关注页面" ),
    SSPROD("SSPROD","搜索商品页面"),
	SSSHOP("SSSHOP","搜索商铺页面"),
	SPXQ("SPXQ","商品详情页面"),
	WLXQ("WLXQ","物流详情"),
	TCPHTJ("TCPHTJ", "同城配货统计"),
	GXPHTJ("GXPHTJ","干线配货统计");
	
	public static ECallSourceType getELevelTyleByCode(String code){
		for (ECallSourceType  levelTyle: ECallSourceType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(String code){
		for (ECallSourceType  levelTyle: ECallSourceType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "";
	}
	private String code;
	private String value;
	private ECallSourceType(){
	}
	private ECallSourceType(String code,String value){
		this.code=code;
		this.value=value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
