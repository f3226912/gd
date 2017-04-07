package com.gudeng.commerce.gd.order.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.order.entity.PreWeighCarDetailEntity;

@Entity(name = "pre_weighCar_detail")
public class PreWeighCarDetailDTO extends PreWeighCarDetailEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1795752650678886240L;

	/**
	 * 查询出热门好货中，产地供应商的联系方式
	 */
	private String mobile;

	/**
	 * 查询出热门好货中，产地供应商的真实姓名
	 */
	private String realName;
	
	/*
	 * 入场开始时间
	 */
	private String totalStartTime;
	
	/*
	 * 入场结束时间
	 */
	private String totalEndTime;
	
	/*
	 * 出场开始时间
	 */
	private String tareStartTime;
	
	/*
	 * 入场结束时间
	 */
	private String tareEndTime;
	
	/*
	 * 出场重量
	 */
	private Double outWeigh;
	
	/*
	 * 用户名
	 */
	private String account;
	
	/*
	 * 真实姓名
	 */
	private String memberName;
	
	/*
	 * 查询用入库单号
	 */
	private String inStoreNo;
	
	/*
	 * 关联入库单
	 */
	private List<InStoreDetailDTO> inStores;
	
	/*
	 * 商品类目
	 */
	private String cateName;
	
	private Date totalCreateTime;
	
	private Date tareCreateTime;
	
	public Date getTotalCreateTime() {
		return totalCreateTime;
	}

	public void setTotalCreateTime(Date totalCreateTime) {
		this.totalCreateTime = totalCreateTime;
	}

	public Date getTareCreateTime() {
		return tareCreateTime;
	}

	public void setTareCreateTime(Date tareCreateTime) {
		this.tareCreateTime = tareCreateTime;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public List<InStoreDetailDTO> getInStores() {
		return inStores;
	}

	public void setInStores(List<InStoreDetailDTO> inStores) {
		this.inStores = inStores;
	}

	public String getInStoreNo() {
		return inStoreNo;
	}

	public void setInStoreNo(String inStoreNo) {
		this.inStoreNo = inStoreNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Double getOutWeigh() {
		return outWeigh;
	}

	public void setOutWeigh(Double outWeigh) {
		this.outWeigh = outWeigh;
	}

	public String getTotalStartTime() {
		return totalStartTime;
	}

	public void setTotalStartTime(String totalStartTime) {
		this.totalStartTime = totalStartTime;
	}

	public String getTotalEndTime() {
		return totalEndTime;
	}

	public void setTotalEndTime(String totalEndTime) {
		this.totalEndTime = totalEndTime;
	}

	public String getTareStartTime() {
		return tareStartTime;
	}

	public void setTareStartTime(String tareStartTime) {
		this.tareStartTime = tareStartTime;
	}

	public String getTareEndTime() {
		return tareEndTime;
	}

	public void setTareEndTime(String tareEndTime) {
		this.tareEndTime = tareEndTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

}
