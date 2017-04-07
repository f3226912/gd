package com.gudeng.commerce.gd.supplier.dto;

import com.gudeng.commerce.gd.supplier.annotation.ExcelConf;
import com.gudeng.commerce.gd.supplier.entity.Prices;

public class PricesDTO extends Prices {

    private static final long serialVersionUID = 6782012069013773294L;

    private String date_string;// 报价时间，

    private String createTime_str;// 创建时间
    private String updateTime_str;// 修改时间
    private String publishTime_str;// 发布时间
    private String date_str; // 报价时间/采集时间
    @ExcelConf(excelHeader="采集市场", sort = 7)
    private String marketName;// 市场名称

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getDate_string() {
        return date_string;
    }

    public void setDate_string(String date_string) {
        this.date_string = date_string;
    }

    public String getCreateTime_str() {
        return createTime_str;
    }

    public void setCreateTime_str(String createTime_str) {
        this.createTime_str = createTime_str;
    }

    public String getUpdateTime_str() {
        return updateTime_str;
    }

    public void setUpdateTime_str(String updateTime_str) {
        this.updateTime_str = updateTime_str;
    }

    public String getPublishTime_str() {
        return publishTime_str;
    }

    public void setPublishTime_str(String publishTime_str) {
        this.publishTime_str = publishTime_str;
    }

    public String getDate_str() {
        return date_str;
    }

    public void setDate_str(String date_str) {
        this.date_str = date_str;
    }

}
