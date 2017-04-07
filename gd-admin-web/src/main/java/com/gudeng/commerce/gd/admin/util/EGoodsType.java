package com.gudeng.commerce.gd.admin.util;

/**
 *货物类型枚举 
 * @author xiaojun
 */
public enum EGoodsType {
	CWH(0,"普货"),
	LZ(1,"冷藏"),
	XHSC(2,"生鲜水产"),
	QT(3,"其他"),
	ZH(4,"重货"),
	PH(5,"抛货"),
	SC(6,"蔬菜"),
	SG(7,"水果"),
	NFZP(8,"农副产品"),
	RYP(9,"日用品"),
	FZ(10,"纺织"),
	MC(11,"木材"),
	SCSG(101,"蔬菜水果"),
	GTLY(102,"干调粮油"),
	HXSC(103,"活鲜水产"),
	SPYL(104,"食品饮料"),
	LDSP(105,"冷冻商品"),
	HFZZ(106,"化肥种子"),
	NZNJ(107,"农资农具"),
	RYPL(108,"日用品"),
	JCSB(109,"建材设备"),
	QTSP(110,"其他商品");
	
	public static EGoodsType getELevelTyleByCode(Integer code){
		for (EGoodsType  levelTyle: EGoodsType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle;
			}
		}
		return null;
	}
	public static String getValueByCode(Integer code){
		for (EGoodsType  levelTyle: EGoodsType.values()) {
			if (code.equals(levelTyle.getCode())) {
				return levelTyle.getValue();
			}
		}
		return "其他";
	}
	private Integer code;
	private String value;
	private EGoodsType(){
	}
	private EGoodsType(Integer code,String value){
		this.code=code;
		this.value=value;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
