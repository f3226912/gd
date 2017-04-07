package com.gudeng.commerce.gd.customer.enums;

public enum CertifTypeEnum {

	CERTIF_PERSONAL(1,"个人认证"),CERTIF_COMPANY(2,"企业认证"), CERTIF_SHOP(3, "实体商铺认证"),
	CERTIF_CORP(4, "合作社认证"), CERTIF_BASE(5,"基地认证"), CERTIF_SP_PRODUCT(6, "地理标志产品认证"),
	CERTIF_COMMODITY_MASTER(7,"货主认证"),CERTIF_TRUCK_MASTER(8,"车主认证"), CERTIF_INFODEPT(9, "信息部认证"),;

	private final Integer key;
	private final String value;

	CertifTypeEnum(Integer key, String value){
		this.key = key;
		this.value = value;
	}

	public Integer getKey(){
		return this.key;
	}
	public String getValue(){
		return this.value;
	}
	public static String getValue(Integer key){
		for (CertifTypeEnum item :CertifTypeEnum.values()){
			if (item.getKey().intValue()==key){
				return item.getValue();
			}
		}
		return "";
	}


}
