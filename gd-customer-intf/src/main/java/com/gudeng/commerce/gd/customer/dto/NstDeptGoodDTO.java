package com.gudeng.commerce.gd.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 农速通信息部货源
 * @author dengjianfeng
 *
 */
public class NstDeptGoodDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5766497541400739285L;

	private Long id;
	
	private Long memberId;
	
	private String account;
	
	private String realName;
	
	private Date createTime;
	
	private String s_address;
	
	private String f_address;
	
	private Integer nstRule;
	
	private Integer clients;
	
	private String commonCityName;
	
	private Integer sourceType;
	
	private Integer orderStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getS_address() {
		return s_address;
	}

	public void setS_address(String s_address) {
		this.s_address = s_address;
	}

	public String getF_address() {
		return f_address;
	}

	public void setF_address(String f_address) {
		this.f_address = f_address;
	}

	public Integer getNstRule() {
		return nstRule;
	}

	public void setNstRule(Integer nstRule) {
		this.nstRule = nstRule;
	}

	public Integer getClients() {
		return clients;
	}

	public void setClients(Integer clients) {
		this.clients = clients;
	}

	public String getCommonCityName() {
		return commonCityName;
	}

	public void setCommonCityName(String commonCityName) {
		this.commonCityName = commonCityName;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
