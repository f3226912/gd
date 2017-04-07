package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "bank_information")
public class BankInformationEntity  implements java.io.Serializable{

	
	private static final long serialVersionUID = 7597826481828799739L;

	/**
    *主键自增长
    */
    private Long id;

    /**
    *发卡行名称
    */
    private String bankName;

    /**
    *银行机构代码
    */
    private String bankCode;

    /**
    *卡名
    */
    private String cardName;

    /**
    *卡种
    */
    private String cardType;

    /**
    *银行卡长度
    */
    private Integer cardLength;

    /**
    *发卡行标识
    */
    private String bankSignId;

    /**
    *发卡行简称
    */
    private String bankShortName;

    /**
    *银行Logo
    */
    private String bankLogo;

    /**
    *状态：包括启用1、关闭0
    */
    private String status;
    
    /**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人
	 */
	private String createUserId;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 更新人
	 */
	private String updateUserId;
	
	/**
	 * 发卡行英文简称
	 */
	private String bankEShortName;
	
	/**
	 * 发卡行标识长度
	 */
	private Integer bankSignLength;

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "bankName")
    public String getBankName(){

        return this.bankName;
    }
    public void setBankName(String bankName){

        this.bankName = bankName;
    }
    @Column(name = "bankCode")
    public String getBankCode(){

        return this.bankCode;
    }
    public void setBankCode(String bankCode){

        this.bankCode = bankCode;
    }
    @Column(name = "cardName")
    public String getCardName(){

        return this.cardName;
    }
    public void setCardName(String cardName){

        this.cardName = cardName;
    }
    @Column(name = "cardType")
    public String getCardType(){

        return this.cardType;
    }
    public void setCardType(String cardType){

        this.cardType = cardType;
    }
    @Column(name = "cardLength")
    public Integer getCardLength(){

        return this.cardLength;
    }
    public void setCardLength(Integer cardLength){

        this.cardLength = cardLength;
    }
    @Column(name = "bankSignId")
    public String getBankSignId(){

        return this.bankSignId;
    }
    public void setBankSignId(String bankSignId){

        this.bankSignId = bankSignId;
    }
    @Column(name = "bankShortName")
    public String getBankShortName(){

        return this.bankShortName;
    }
    public void setBankShortName(String bankShortName){

        this.bankShortName = bankShortName;
    }
    @Column(name = "bankLogo")
    public String getBankLogo(){

        return this.bankLogo;
    }
    public void setBankLogo(String bankLogo){

        this.bankLogo = bankLogo;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
    @Column(name = "createTime")
	public Date getCreateTime() {

		return this.createTime;
	}

	public void setCreateTime(Date createTime) {

		this.createTime = createTime;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {

		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {

		this.createUserId = createUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {

		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {

		this.updateTime = updateTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}
	
	
	@Column(name = "bankEShortName")
	public String getBankEShortName() {
		return bankEShortName;
	}
	public void setBankEShortName(String bankEShortName) {
		this.bankEShortName = bankEShortName;
	}
	
	@Column(name = "bankSignLength")
	public Integer getBankSignLength() {
		return bankSignLength;
	}
	public void setBankSignLength(Integer bankSignLength) {
		this.bankSignLength = bankSignLength;
	}
	
	
	
	
}
    

