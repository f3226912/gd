package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "picture_ref")
public class PictureRefEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4946248358435801991L;

	private Integer id;

	private String refId;

	/**
	 * 类型 1产销活动
	 */
	private Byte type;

	private String urlOrg;

	private String url650;

	private String url400;

	private String url160;

	private String url120;

	private String url60;

	private String createUserId;

	private Date createTime;

	private String updateUserId;

	private Date updateTime;

	@Column(name = "id")
	public Integer getId() {

		return this.id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	@Column(name = "refId")
	public String getRefId() {

		return this.refId;
	}

	public void setRefId(String refId) {

		this.refId = refId;
	}

	@Column(name = "type")
	public Byte getType() {

		return this.type;
	}

	public void setType(Byte type) {

		this.type = type;
	}

	@Column(name = "urlOrg")
	public String getUrlOrg() {

		return this.urlOrg;
	}

	public void setUrlOrg(String urlOrg) {

		this.urlOrg = urlOrg;
	}

	@Column(name = "url650")
	public String getUrl650() {

		return this.url650;
	}

	public void setUrl650(String url650) {

		this.url650 = url650;
	}

	@Column(name = "url400")
	public String getUrl400() {

		return this.url400;
	}

	public void setUrl400(String url400) {

		this.url400 = url400;
	}

	@Column(name = "url160")
	public String getUrl160() {

		return this.url160;
	}

	public void setUrl160(String url160) {

		this.url160 = url160;
	}

	@Column(name = "url120")
	public String getUrl120() {

		return this.url120;
	}

	public void setUrl120(String url120) {

		this.url120 = url120;
	}

	@Column(name = "url60")
	public String getUrl60() {

		return this.url60;
	}

	public void setUrl60(String url60) {

		this.url60 = url60;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}
}
