package com.gudeng.commerce.gd.promotion.dto;

import com.gudeng.commerce.gd.promotion.entity.ActReUserActivityEntity;

public class ActReUserActivityDto extends ActReUserActivityEntity implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -3287305559370772063L;
	private Integer rownum;
	
	private String nickname; 
	
	private String headimgurl; 
	
	private String rownumStr;
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getRownumStr() {
		return rownumStr;
	}
	public void setRownumStr(String rownumStr) {
		this.rownumStr = rownumStr;
	}
	
}
