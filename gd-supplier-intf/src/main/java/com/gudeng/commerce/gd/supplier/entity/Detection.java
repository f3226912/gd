package com.gudeng.commerce.gd.supplier.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.gudeng.commerce.gd.supplier.annotation.ExcelConf;

/**
 * 监测信息 表 实体类
 * 
 * @author 李冬
 * @time 2015年10月12日 下午5:45:52
 */
@Entity(name = "Detection")
@JsonIgnoreProperties({ "id" })
public class Detection implements java.io.Serializable {
	private static final long serialVersionUID = -3448604655762812400L;
	private Long id;// 主键
	@ExcelConf(excelHeader="商品名称", sort = 0)
	private String productName;// 商品名称
	private Long maketId;// 所属街市ID
	private String detail;// 监测结果明细
	private String status;// 0表示正常范围，1表示偏高，-1表示偏低 注意：偏高一般指农药残留，偏低一般指营养成分
	private String description;// 说明
	@ExcelConf(excelHeader="出产地", sort = 1)
	private String origin;// 出产地
	@ExcelConf(excelHeader="被检单位或姓名", sort = 2)
	private String unitName;// 被检单位或姓名
	private String inspection;// 检测项目,目前只有一种检测项目
	private Double rate;// 抑制率
	private Integer pass;// 是否合格
	@ExcelConf(excelHeader="检测日期", sort = 6)
	private Date detectTime;//检测时间
	@ExcelConf(excelHeader="发布日期", sort = 4)
	private Date publishTime;//发布时间
	private String createUserId;// 创建人员ID
	private Date createTime;// 创建时间
	private String updateUserId;// 修改人员ID
	private Date updateTime;// 修改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getMaketId() {
		return maketId;
	}

	public void setMaketId(Long maketId) {
		this.maketId = maketId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}


	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public Date getDetectTime() {
		return detectTime;
	}

	public void setDetectTime(Date detectTime) {
		this.detectTime = detectTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	public String toString() {
		return "Detection [id=" + id + ", productName=" + productName
				+ ", maketId=" + maketId + ", detail=" + detail + ", status="
				+ status + ", description=" + description + ", origin="
				+ origin + ", unitName=" + unitName + ", inspection="
				+ inspection + ", rate=" + rate + ", pass=" + pass
				+ ", createUserId=" + createUserId + ", createTime="
				+ createTime + ", updateUserId=" + updateUserId
				+ ", updateTime=" + updateTime + "]";
	}
}
