package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;

import javax.xml.crypto.Data;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;

/**
 * 
 * @author gcwu
 *
 */
public class GrdGdGiftOutStorageDataEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5315780184228795865L;

	private Integer id;
	
	/**
	 * 所属市场
	 */
	@ExcelConf(excelHeader="所属市场",sort=1)
	private String marketName;
	
	
	
	/**
	 * 仓库名称
	 */
	@ExcelConf(excelHeader="仓库名称",sort=2)
	private String giftstoreName;
	
	/**
	 *  出库时间
	 */
	@ExcelConf(excelHeader="出库时间",sort=3)
	private String createTime;
	
	/**
	 * 礼品编码
	 */
	@ExcelConf(excelHeader="礼品编码",sort=4)
	private String giftNo;
	

	/**
	 * 礼品名称
	 */
	@ExcelConf(excelHeader="礼品名称",sort=5)
	private String giftName;
	/**
	 * 礼品单位
	 */
	@ExcelConf(excelHeader="礼品单位",sort=6)
	private String unit; 
	/**
	 * 入库数量
	 */
	@ExcelConf(excelHeader="出库数量",sort=7)
	private Integer outCount;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	

	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getGiftNo() {
		return giftNo;
	}
	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}
	public String getGiftName() {
		return giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	public String getGiftstoreName() {
		return giftstoreName;
	}
	public void setGiftstoreName(String giftstoreName) {
		this.giftstoreName = giftstoreName;
	}

	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getOutCount() {
		return outCount;
	}
	public void setOutCount(Integer outCount) {
		this.outCount = outCount;
	}
	
	
	
}
