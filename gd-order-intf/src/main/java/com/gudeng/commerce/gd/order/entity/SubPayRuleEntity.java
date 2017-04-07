package com.gudeng.commerce.gd.order.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Semon
 *
 */
@Entity(name = "sub_pay_rule")
public class SubPayRuleEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -373576745356731835L;

    private Integer ruleId;

    private String subRuleName;

    private Integer marketId;

    private Integer memberType;

    private Date timeStart;

    private Date timeEnd;

    private String subType;

    private Integer posCoefficient;

    private Integer walletCoefficient;

    private Integer cashCoefficient;

    private Integer subPercent;

    private Double subAmount;

    private String subForTon;

    private String status;

    private String cateJson;

    private Date createTime;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    @Id
    @Column(name = "ruleId")
    public Integer getRuleId(){

        return this.ruleId;
    }
    public void setRuleId(Integer ruleId){

        this.ruleId = ruleId;
    }
    @Column(name = "subRuleName")
    public String getSubRuleName(){

        return this.subRuleName;
    }
    public void setSubRuleName(String subRuleName){

        this.subRuleName = subRuleName;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "memberType")
    public Integer getMemberType(){

        return this.memberType;
    }
    public void setMemberType(Integer memberType){

        this.memberType = memberType;
    }
    @Column(name = "timeStart")
    public Date getTimeStart(){

        return this.timeStart;
    }
    public void setTimeStart(Date timeStart){

        this.timeStart = timeStart;
    }
    @Column(name = "timeEnd")
    public Date getTimeEnd(){

        return this.timeEnd;
    }
    public void setTimeEnd(Date timeEnd){

        this.timeEnd = timeEnd;
    }
    @Column(name = "subType")
    public String getSubType(){

        return this.subType;
    }
    public void setSubType(String subType){

        this.subType = subType;
    }
    @Column(name = "posCoefficient")
    public Integer getPosCoefficient(){

        return this.posCoefficient;
    }
    public void setPosCoefficient(Integer posCoefficient){

        this.posCoefficient = posCoefficient;
    }
    @Column(name = "walletCoefficient")
    public Integer getWalletCoefficient(){

        return this.walletCoefficient;
    }
    public void setWalletCoefficient(Integer walletCoefficient){

        this.walletCoefficient = walletCoefficient;
    }
    @Column(name = "cashCoefficient")
    public Integer getCashCoefficient(){

        return this.cashCoefficient;
    }
    public void setCashCoefficient(Integer cashCoefficient){

        this.cashCoefficient = cashCoefficient;
    }
    @Column(name = "subPercent")
    public Integer getSubPercent(){

        return this.subPercent;
    }
    public void setSubPercent(Integer subPercent){

        this.subPercent = subPercent;
    }
    @Column(name = "subAmount")
    public Double getSubAmount(){

        return this.subAmount;
    }
    public void setSubAmount(Double subAmount){

        this.subAmount = subAmount;
    }
    @Column(name = "subForTon")
    public String getSubForTon(){

        return this.subForTon;
    }
    public void setSubForTon(String subForTon){

        this.subForTon = subForTon;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "cateJson")
    public String getCateJson(){

        return this.cateJson;
    }
    public void setCateJson(String cateJson){

        this.cateJson = cateJson;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
}


