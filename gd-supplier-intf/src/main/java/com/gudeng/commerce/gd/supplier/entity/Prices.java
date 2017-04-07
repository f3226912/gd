package com.gudeng.commerce.gd.supplier.entity;

import java.util.Date;

import javax.persistence.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.gudeng.commerce.gd.supplier.annotation.ExcelConf;

/**
 * 市场价格表 实体类
 * 
 * @author 李冬
 * @time 2015年10月12日 下午5:38:14
 */
@Entity(name = "prices")
@JsonIgnoreProperties({ "id" })
public class Prices implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8590958731769864163L;

    private Long id;// 主键，自增长

    @ExcelConf(excelHeader = "商品名称", sort = 0)
    private String productName;// 商品名称

    private Long productTypeId;// 产品分类ID

    private Long maketId;// 所属街市ID
    @ExcelConf(excelHeader = "平均报价（元/公斤）", sort = 3)
    private Double averagePrice;// 平均报价
    @ExcelConf(excelHeader = "最低报价（元/公斤）", sort = 2)
    private Double minPrice;// 最低报价
    @ExcelConf(excelHeader = "最高报价（元/公斤）", sort = 1)
    private Double maxPrice;// 最高报价
    @ExcelConf(excelHeader = "采集时间", sort = 4)
    private Date date;// 报价时间
    @ExcelConf(excelHeader = "发布时间", sort = 5)
    private Date publishTime;// 发布时间

    private String status;// 状态

    private String description;// 说明
    private String locale;// 采集点
    private String createUserId;// 创建人员ID

    private Date createTime;// 创建时间

    private String updateUserId;// 修改人员ID

    private Date updateTime;// 修改时间
    @ExcelConf(excelHeader = "跳转地址", sort = 6)
    private String targetUrl;// 跳转地址

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getProductName() {

        return this.productName;
    }

    public void setProductName(String productName) {

        this.productName = productName;
    }

    public Long getProductTypeId() {

        return this.productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {

        this.productTypeId = productTypeId;
    }

    public Long getMaketId() {

        return this.maketId;
    }

    public void setMaketId(Long maketId) {

        this.maketId = maketId;
    }

    public Double getAveragePrice() {

        return this.averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {

        this.averagePrice = averagePrice;
    }

    public Double getMinPrice() {

        return this.minPrice;
    }

    public void setMinPrice(Double minPrice) {

        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {

        return this.maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {

        this.maxPrice = maxPrice;
    }

    public Date getDate() {

        return this.date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getStatus() {

        return this.status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getDescription() {

        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCreateUserId() {

        return this.createUserId;
    }

    public void setCreateUserId(String createUserId) {

        this.createUserId = createUserId;
    }

    public Date getCreateTime() {

        return this.createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public String getUpdateUserId() {

        return this.updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {

        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {

        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public String toString() {
        return "Prices [id=" + id + ", productName=" + productName + ", productTypeId=" + productTypeId + ", maketId=" + maketId + ", averagePrice=" + averagePrice + ", minPrice="
                + minPrice + ", maxPrice=" + maxPrice + ", date=" + date + ", status=" + status + ", description=" + description + ", createUserId=" + createUserId
                + ", createTime=" + createTime + ", updateUserId=" + updateUserId + ", updateTime=" + updateTime + "]";
    }

}
