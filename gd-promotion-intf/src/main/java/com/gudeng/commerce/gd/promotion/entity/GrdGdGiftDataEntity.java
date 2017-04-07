package com.gudeng.commerce.gd.promotion.entity;

import java.io.Serializable;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;

/**
 * 
 * @author gcwu
 *
 */
public class GrdGdGiftDataEntity implements Serializable{

	private static final long serialVersionUID = -8456401642267229570L;


	/**
	 * 仓库名称
	 */
	@ExcelConf(excelHeader="仓库名称",sort=1)
	private String giftstoreName;
	
	
	/**
	 * 市场名称
	 */
	@ExcelConf(excelHeader="所属市场",sort=2)
	private String marketName;
	
	
	/**
	 * 礼品编码
	 */
	@ExcelConf(excelHeader="礼品编码",sort=3)
	private String giftNO;
	
	/**
	 * 礼品名称
	 */
	@ExcelConf(excelHeader="礼品名称",sort=4)
	private String giftname;
	
	/**
	 * 礼品单位
	 */
	@ExcelConf(excelHeader="礼品单位",sort=5)
	private String unit; 
	/**
	 * 入库数量
	 */
	@ExcelConf(excelHeader="库存数量",sort=6)
	private Integer inCount;
	
	
	
	/**
	 * 最新单价
	 */
	@ExcelConf(excelHeader="最新单价",sort=7)
	private double newPrice;
	
	/**
	 * 货值
	 */
	@ExcelConf(excelHeader="货值",sort=8)
	private double price;
	
	
	
	
	
	
	
	
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public double getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
	
}
