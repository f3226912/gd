package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

/**
 * app渠道链接
 * 
 * @author lidong
 * @time 2016年8月18日 下午6:41:11
 */
@Entity(name = "app_channel_link")
public class AppChannelLink implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2055805424722351958L;
	private Integer id;
	private String clientChannel;
	@ExcelConf(excelHeader="渠道名称",sort=2)
	private String channelName;
	private String platform;
	private Integer state;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	@ExcelConf(excelHeader="安装包下载链接",sort=4)
	private String downloadLink;
	@ExcelConf(excelHeader="着落页链接",sort=5)
	private String pageLink;

	// Property accessors
	@Id
	@Column(name = "id")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "clientChannel")
	public String getClientChannel() {
		return this.clientChannel;
	}

	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}

	@Column(name = "channelName")
	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	@Column(name = "platform")
	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

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

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "downloadLink")
	public String getDownloadLink() {
		return this.downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	@Column(name = "pageLink")
	public String getPageLink() {
		return this.pageLink;
	}

	public void setPageLink(String pageLink) {
		this.pageLink = pageLink;
	}

}