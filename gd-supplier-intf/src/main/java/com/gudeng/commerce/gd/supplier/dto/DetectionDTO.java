package com.gudeng.commerce.gd.supplier.dto;

import com.gudeng.commerce.gd.supplier.annotation.ExcelConf;
import com.gudeng.commerce.gd.supplier.entity.Detection;

public class DetectionDTO extends Detection {

    private static final long serialVersionUID = 941786484384286325L;
    private String createTime_str;// 创建时间
    private String updateTime_str;// 修改时间
    private String detectTime_str;// 检测时间
    private String publishTime_str;// 发布时间
    @ExcelConf(excelHeader = "检测市场", sort = 8)
    private String marketName;// 市场名称
    @ExcelConf(excelHeader = "是否合格", sort = 5)
    private String passStr;// 是否合格
    @ExcelConf(excelHeader = "检测项目", sort = 3)
    private String inspectionStr;// 检测项目
    @ExcelConf(excelHeader = "抑制率", sort = 4)
    private String rateStr;// 抑制率

    public String getRateStr() {
        rateStr = this.getRate() + "%";
        return rateStr;
    }

    public void setRateStr(String rateStr) {
        this.rateStr = rateStr;
    }

    public String getPassStr() {
        if (this.getPass() == null) {
            passStr = "否";
        } else if (this.getPass() == 1) {
            passStr = "是";
        } else {
            passStr = "否";
        }
        return passStr;
    }

    public void setPassStr(String passStr) {
        this.passStr = passStr;
    }

    public String getInspectionStr() {
        // 检测项目,目前只有一种检测项目
        if ("0".equals(this.getInspection())) {
            inspectionStr = "蔬菜农药残留检测";
        } else {
            inspectionStr = "蔬菜农药残留检测";
        }
        return inspectionStr;
    }

    public void setInspectionStr(String inspectionStr) {
        this.inspectionStr = inspectionStr;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
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

    public String getDetectTime_str() {
        return detectTime_str;
    }

    public void setDetectTime_str(String detectTime_str) {
        this.detectTime_str = detectTime_str;
    }

    public String getPublishTime_str() {
        return publishTime_str;
    }

    public void setPublishTime_str(String publishTime_str) {
        this.publishTime_str = publishTime_str;
    }

}
