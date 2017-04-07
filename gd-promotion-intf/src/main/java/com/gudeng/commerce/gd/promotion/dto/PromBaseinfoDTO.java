package com.gudeng.commerce.gd.promotion.dto;

import java.util.List;

import javax.persistence.Entity;

import com.gudeng.commerce.gd.promotion.entity.PromBaseinfoEntity;

@Entity(name = "prom_baseinfo")
public class PromBaseinfoDTO extends PromBaseinfoEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -907829851135486665L;
	
	private String createUserName;
	
	private List<PromMarketDTO> marketList;
	
	/**
	 * 所属市场名称，多个市场以逗号分隔
	 */
	private String marketNames;

	private List<PromFeeDTO> promFees;
	
	private Integer maxProdNum;
	
	private Double prepayAmt;

	private List<PictureRefDTO> pictureRefList;
	
	private List<PromProdInfoDTO> promProdInfoList;//活动商品信息
	
	private PromRuleDTO promRule;//活动规则

    private String rstatus;//活动状态 3、2、1
    private String attendStatus; //供应商参与状态 1参加 2取消
    
	public String getAttendStatus() {
		return attendStatus;
	}

	public void setAttendStatus(String attendStatus) {
		this.attendStatus = attendStatus;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public List<PictureRefDTO> getPictureRefList() {
		return pictureRefList;
	}

	public void setPictureRefList(List<PictureRefDTO> pictureRefList) {
		this.pictureRefList = pictureRefList;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}


	public List<PromFeeDTO> getPromFees() {
		return promFees;
	}

	public void setPromFees(List<PromFeeDTO> promFees) {
		this.promFees = promFees;
	}

	public Integer getMaxProdNum() {
		return maxProdNum;
	}

	public void setMaxProdNum(Integer maxProdNum) {
		this.maxProdNum = maxProdNum;
	}

	public Double getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(Double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}


	public String getMarketNames() {
		return marketNames;
	}

	public void setMarketNames(String marketNames) {
		this.marketNames = marketNames;
	}

	public List<PromMarketDTO> getMarketList() {
		return marketList;
	}

	public void setMarketList(List<PromMarketDTO> marketList) {
		this.marketList = marketList;
	}

	public List<PromProdInfoDTO> getPromProdInfoList() {
		return promProdInfoList;
	}

	public void setPromProdInfoList(List<PromProdInfoDTO> promProdInfoList) {
		this.promProdInfoList = promProdInfoList;
	}

	public PromRuleDTO getPromRule() {
		return promRule;
	}

	public void setPromRule(PromRuleDTO promRule) {
		this.promRule = promRule;
	}
}
