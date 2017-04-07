package com.gudeng.commerce.gd.customer.entity.certif;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "certif_shop")
public class CertifShopEntity  implements java.io.Serializable{
    /**
    *
    */
    private Integer id;

    *用户Id，关联去用户数据
    */
    private Integer memberId;

    *用户账号
    */
    @ExcelConf(excelHeader="账号", sort = 1)
    private String account;

    *商铺Id，关联去商铺数据
    */
    private Integer businessId;

    *线上商铺名称
    */
    @ExcelConf(excelHeader="商铺名称", sort = 2)
    private String shopName;

    *线下实体商铺名称
    */
    private String realShopName;

    *实体商铺经营者的姓名
    */
    private String operatorName;

    *主营分类
    */
    private Integer cateId;

    *线上商铺所属市场Id
    */
    private Integer marketId;

    *pos终端号
    */
    private String posNo;

    *智能秤（MAC地址）
    */
    private String macNo;

    *商铺地址
    */
	@ExcelConf(excelHeader="商铺地址", sort = 6)
    private String address;

    *提交时间
    */
	@ExcelConf(excelHeader="申请时间", sort = 3)
    private Date commitTime;

    *租赁合同照片
    */
    private String contractImg;

    *状态(0:待审核1:已认证;2:已驳回)
    */
    private String status;

    *记录创建时间
    */
    private Date createTime;

    *记录最新的更新时间
    */
    private Date updateTime;

    *
    */
    private String createUserId;

    *
    */
    private String updateUserId;

    *记录当前操作的操作员是谁，取管理后台用户的姓名
    */
	@ExcelConf(excelHeader="审核员", sort = 7)
    private String optionUser;

    @Column(name = "id")
    public Integer getId(){

    }
    public void setId(Integer id){

    }
    @Column(name = "memberId")
    public Integer getMemberId(){

    }
    public void setMemberId(Integer memberId){

    }
    @Column(name = "account")
    public String getAccount(){

    }
    public void setAccount(String account){

    }
    @Column(name = "businessId")
    public Integer getBusinessId(){

    }
    public void setBusinessId(Integer businessId){

    }
    @Column(name = "shopName")
    public String getShopName(){

    }
    public void setShopName(String shopName){

    }
    @Column(name = "realShopName")
    public String getRealShopName(){

    }
    public void setRealShopName(String realShopName){

    }
    @Column(name = "operatorName")
    public String getOperatorName(){

    }
    public void setOperatorName(String operatorName){

    }
    @Column(name = "cateId")
    public Integer getCateId(){

    }
    public void setCateId(Integer cateId){

    }
    @Column(name = "marketId")
    public Integer getMarketId(){

    }
    public void setMarketId(Integer marketId){

    }
    @Column(name = "posNo")
    public String getPosNo(){

    }
    public void setPosNo(String posNo){

    }
    @Column(name = "macNo")
    public String getMacNo(){

    }
    public void setMacNo(String macNo){

    }
    @Column(name = "address")
    public String getAddress(){

    }
    public void setAddress(String address){

    }
    @Column(name = "commitTime")
    public Date getCommitTime(){

    }
    public void setCommitTime(Date commitTime){

    }
    @Column(name = "contractImg")
    public String getContractImg(){

    }
    public void setContractImg(String contractImg){

    }
    @Column(name = "status")
    public String getStatus(){

    }
    public void setStatus(String status){

    }
    @Column(name = "createTime")
    public Date getCreateTime(){

    }
    public void setCreateTime(Date createTime){

    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

    }
    public void setUpdateTime(Date updateTime){

    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

    }
    public void setCreateUserId(String createUserId){

    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

    }
    public void setUpdateUserId(String updateUserId){

    }
    @Column(name = "optionUser")
    public String getOptionUser(){

    }
    public void setOptionUser(String optionUser){

    }
}