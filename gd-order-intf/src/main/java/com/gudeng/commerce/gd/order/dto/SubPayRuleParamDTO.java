package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.gudeng.commerce.gd.order.entity.SubPayRuleEntity;
import com.gudeng.commerce.gd.order.entity.SubRangePayRuleEntity;

/**
 * 
 * @author xiehui
 *
 */
public class SubPayRuleParamDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2175826558861199071L;
	
	private SubPayRuleEntity subPayRule;
	private Set<CategoryTreeDTO> cList;
	private List<SubRangePayRuleEntity> rList;
	private List<SubRangePayRuleEntity> zList;
	private List<SubRangePayRuleEntity> mList;
	private String posCoefficient;
	private String walletCoefficient;
	private String cashCoefficient;
	private String subPercent;
	
	public SubPayRuleEntity getSubPayRule() {
		return subPayRule;
	}
	public void setSubPayRule(SubPayRuleEntity subPayRule) {
		this.subPayRule = subPayRule;
	}

	public Set<CategoryTreeDTO> getcList() {
		return cList;
	}
	public void setcList(Set<CategoryTreeDTO> cList) {
		this.cList = cList;
	}
	public List<SubRangePayRuleEntity> getrList() {
		return rList;
	}
	public void setrList(List<SubRangePayRuleEntity> rList) {
		this.rList = rList;
	}
	public List<SubRangePayRuleEntity> getzList() {
		return zList;
	}
	public void setzList(List<SubRangePayRuleEntity> zList) {
		this.zList = zList;
	}
	public List<SubRangePayRuleEntity> getmList() {
		return mList;
	}
	public void setmList(List<SubRangePayRuleEntity> mList) {
		this.mList = mList;
	}
	public String getPosCoefficient() {
		return posCoefficient;
	}
	public void setPosCoefficient(String posCoefficient) {
		this.posCoefficient = posCoefficient;
	}
	public String getWalletCoefficient() {
		return walletCoefficient;
	}
	public void setWalletCoefficient(String walletCoefficient) {
		this.walletCoefficient = walletCoefficient;
	}
	public String getCashCoefficient() {
		return cashCoefficient;
	}
	public void setCashCoefficient(String cashCoefficient) {
		this.cashCoefficient = cashCoefficient;
	}
	public String getSubPercent() {
		return subPercent;
	}
	public void setSubPercent(String subPercent) {
		this.subPercent = subPercent;
	}
	
	public int getParse(String str,int n){
		if(StringUtils.isBlank(str))return 0;
		float f = Float.parseFloat(str);
		int result = (int) (f*n);
		return result;
	}
	
}
