package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 拨打电话记录表
 * @author Ailen
 *
 */
@Entity(name = "callstatistics")
public class CallstatiSticsEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2212813376798459860L;
	private Long id;
	private String sysCode;
	private String source;
	private String s_Mobile;
	private String s_Name;
	private String shopName;
	private String e_Mobile;
	private String e_Name;
	private Date createTime;
	private String fromCode;
	
	private Long memberId;
	private Long b_memberId;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "sysCode")
	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	@Column(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "shopName")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "s_Mobile")
	public String getS_Mobile() {
		return s_Mobile;
	}

	public void setS_Mobile(String s_Mobile) {
		this.s_Mobile = s_Mobile;
	}
	@Column(name = "s_Name")
	public String getS_Name() {
		return s_Name;
	}

	public void setS_Name(String s_Name) {
		this.s_Name = s_Name;
	}
	@Column(name = "e_Mobile")
	public String getE_Mobile() {
		return e_Mobile;
	}

	public void setE_Mobile(String e_Mobile) {
		this.e_Mobile = e_Mobile;
	}
	
	@Column(name = "e_Name")
	public String getE_Name() {
		return e_Name;
	}

	public void setE_Name(String e_Name) {
		this.e_Name = e_Name;
	}

	@Column(name = "fromCode")
	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	@Column(name = "memberId")
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Column(name = "b_memberId")
	public Long getB_memberId() {
		return b_memberId;
	}

	public void setB_memberId(Long b_memberId) {
		this.b_memberId = b_memberId;
	}
	
	
	
}
