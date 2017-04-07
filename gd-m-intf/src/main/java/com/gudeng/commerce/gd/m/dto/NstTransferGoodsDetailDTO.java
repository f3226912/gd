package com.gudeng.commerce.gd.m.dto;

import java.io.Serializable;

/**
 * 物流货物信息DTO
 * @author TerryZhang
 */
public class NstTransferGoodsDetailDTO  implements Serializable{

	private static final long serialVersionUID = 3255510117488595741L;

	/**
	 * 价格
	 */
	private String price;
	
	/**
	 * 发货地
	 */
	private String startAddr;
	
	/**
	 * 收货地
	 */
	private String endAddr;
	
	/**
	 *是否有货物详情 0否 1是 
	 */
	private Integer hasGoods;
	
	/**
	 * 发货人
	 */
	private String deliverer;
	
	/**
	 * 总重量
	 */
	private String weight;
	
	/**
	 * 总体积
	 */
	private String size;
	
	/**
	 * 装车时间
	 */
	private String goodsDate;
	
	/**
	 * 货物类型名
	 */
	private String goodsTypeName;
	
	/**
	 * 车辆类型名
	 */
	private String carTypeName;
	
	/**
	 * 留言
	 */
	private String remark;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStartAddr() {
		return startAddr;
	}

	public void setStartAddr(String startAddr) {
		this.startAddr = startAddr;
	}

	public String getEndAddr() {
		return endAddr;
	}

	public void setEndAddr(String endAddr) {
		this.endAddr = endAddr;
	}

	public Integer getHasGoods() {
		return hasGoods;
	}

	public void setHasGoods(Integer hasGoods) {
		this.hasGoods = hasGoods;
	}

	public String getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getGoodsDate() {
		return goodsDate;
	}

	public void setGoodsDate(String goodsDate) {
		this.goodsDate = goodsDate;
	}

	public String getGoodsTypeName() {
		return goodsTypeName;
	}

	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
