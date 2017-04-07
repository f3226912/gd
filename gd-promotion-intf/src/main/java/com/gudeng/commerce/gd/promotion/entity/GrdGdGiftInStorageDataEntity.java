package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;

/**
 * 
 * @author gcwu
 *
 */
public class GrdGdGiftInStorageDataEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315780184228795865L;

	private Integer id;
	
	/**
	 * 采购单号
	 */
	@ExcelConf(excelHeader="采购单号",sort=1)
	private String purchaseNO;
	
	/**
	 * 入库单号
	 */
	@ExcelConf(excelHeader="入库单号",sort=2)
	private String inStorageId;
	
	
	/**
	 *  入库时间
	 */
	@ExcelConf(excelHeader="入库时间",sort=3)
	private String createTime;
	
	/**
	 * 所属市场
	 */
	@ExcelConf(excelHeader="所属市场",sort=4)
	private String marketName;
	
	/**
	 * 仓库名称
	 */
	@ExcelConf(excelHeader="仓库名称",sort=5)
	private String giftstoreName;
	/**
	 * 礼品编码
	 */
	@ExcelConf(excelHeader="礼品编码",sort=6)
	private String giftNO;
	/**
	 * 礼品名称
	 */
	@ExcelConf(excelHeader="礼品名称",sort=7)
	private String giftname;
	/**
	 * 礼品单位
	 */
	@ExcelConf(excelHeader="礼品单位",sort=8)
	private String unit; 
	/**
	 * 入库数量
	 */
	@ExcelConf(excelHeader="入库数量",sort=9)
	private Integer inCount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPurchaseNO() {
		return purchaseNO;
	}
	public void setPurchaseNO(String purchaseNO) {
		this.purchaseNO = purchaseNO;
	}
	public String getInStorageId() {
		return inStorageId;
	}
	public void setInStorageId(String inStorageId) {
		this.inStorageId = inStorageId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGiftstoreName() {
		return giftstoreName;
	}
	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}
	public String getGiftNO() {
		return giftNO;
	}
	public void setGiftNO(String giftNO) {
		this.giftNO = giftNO;
	}
	public String getGiftname() {
		return giftname;
	}
	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getInCount() {
		return inCount;
	}
	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	
}
