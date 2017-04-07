package com.gudeng.commerce.gd.order.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderBillImportLlogEntity implements Serializable {
	private static final long serialVersionUID = 3443049543225063022L;
	private int id;
	private String fileName;
	private String fileUrl;
	private String remoteServer;
	private Date createTime;
	private String createUserId;
	private Date updateTime;
	private String updateUserId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getRemoteServer() {
		return remoteServer;
	}

	public void setRemoteServer(String remoteServer) {
		this.remoteServer = remoteServer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

}
