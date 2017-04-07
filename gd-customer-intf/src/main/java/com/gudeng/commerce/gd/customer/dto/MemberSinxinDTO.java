package com.gudeng.commerce.gd.customer.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.gudeng.commerce.gd.customer.entity.MemberBaseinfoEntity;


public class MemberSinxinDTO extends MemberBaseinfoEntity {

	private static final long serialVersionUID = 1228725742258230407L;
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private String macAddr; // 秤mac
	
	private String lastUpdateTime; // 最近更新时间 格式yyyy-MM-dd HH:mm:ss
	
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getUpdateTimeView() {
		if (getUpdateTime() != null) {
			return DATE_FORMAT.format(getUpdateTime());
		}
		return null;
	}

}
