package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "appshare")
public class AppshareEntity  implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6382723633734560108L;
	
	private Integer shareId;

    private Integer memberId;

    private String realName;

    private String mobile;

    private String account;

    private Integer marketId;

    private String shareClient;

    private String shareAction;

    private Date shareDate;

    private String shareWhere;

    private String giftStatu;

    private String shareUrl;

    private Integer viewCount;

    private String updateUserId;

    private Date updateTime;

    @Column(name = "shareId")
    public Integer getShareId(){

        return this.shareId;
    }
    public void setShareId(Integer shareId){

        this.shareId = shareId;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Integer memberId){

        this.memberId = memberId;
    }
    @Column(name = "realName")
    public String getRealName(){

        return this.realName;
    }
    public void setRealName(String realName){

        this.realName = realName;
    }
    @Column(name = "mobile")
    public String getMobile(){

        return this.mobile;
    }
    public void setMobile(String mobile){

        this.mobile = mobile;
    }
    @Column(name = "account")
    public String getAccount(){

        return this.account;
    }
    public void setAccount(String account){

        this.account = account;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
    }
    @Column(name = "shareClient")
    public String getShareClient(){

        return this.shareClient;
    }
    public void setShareClient(String shareClient){

        this.shareClient = shareClient;
    }
    @Column(name = "shareAction")
    public String getShareAction(){

        return this.shareAction;
    }
    public void setShareAction(String shareAction){

        this.shareAction = shareAction;
    }
    @Column(name = "shareDate")
    public Date getShareDate(){

        return this.shareDate;
    }
    public void setShareDate(Date shareDate){

        this.shareDate = shareDate;
    }
    @Column(name = "shareWhere")
    public String getShareWhere(){

        return this.shareWhere;
    }
    public void setShareWhere(String shareWhere){

        this.shareWhere = shareWhere;
    }
    @Column(name = "giftStatu")
    public String getGiftStatu(){

        return this.giftStatu;
    }
    public void setGiftStatu(String giftStatu){

        this.giftStatu = giftStatu;
    }
    @Column(name = "shareUrl")
    public String getShareUrl(){

        return this.shareUrl;
    }
    public void setShareUrl(String shareUrl){

        this.shareUrl = shareUrl;
    }
    @Column(name = "viewCount")
    public Integer getViewCount(){

        return this.viewCount;
    }
    public void setViewCount(Integer viewCount){

        this.viewCount = viewCount;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
	
    
}

