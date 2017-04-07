package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ad_space")
public class AdSpaceEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5223988867628066939L;

	private Long id;

    private Long menuId;

    private String adType;

    private String adName;

    private String spaceSign;

    private String state;

    private String showImg;

    private String replaceImg;

    private String adSize;
    
    private Double adPrice;
    
    private String createUserId;

    private String createUserName;

    private Date createTime;

    private String updateUserId;

    private String updateUserName;

    private Date updateTime;

    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "menuId")
    public Long getMenuId(){

        return this.menuId;
    }
    public void setMenuId(Long menuId){

        this.menuId = menuId;
    }
    @Column(name="adType")
    public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	@Column(name = "adName")
    public String getAdName(){

        return this.adName;
    }
    public void setAdName(String adName){

        this.adName = adName;
    }
    @Column(name = "spaceSign")
    public String getSpaceSign(){

        return this.spaceSign;
    }
    public void setSpaceSign(String spaceSign){

        this.spaceSign = spaceSign;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "showImg")
    public String getShowImg(){

        return this.showImg;
    }
    public void setShowImg(String showImg){

        this.showImg = showImg;
    }
    @Column(name = "replaceImg")
    public String getReplaceImg(){

        return this.replaceImg;
    }
    public void setReplaceImg(String replaceImg){

        this.replaceImg = replaceImg;
    }
    @Column(name = "adSize")
    public String getAdSize(){

        return this.adSize;
    }
    public void setAdSize(String adSize){

        this.adSize = adSize;
    }
    @Column(name="adPrice")
    public Double getAdPrice() {
		return adPrice;
	}
	public void setAdPrice(Double adPrice) {
		this.adPrice = adPrice;
	}
	@Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createUserName")
    public String getCreateUserName(){

        return this.createUserName;
    }
    public void setCreateUserName(String createUserName){

        this.createUserName = createUserName;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateUserName")
    public String getUpdateUserName(){

        return this.updateUserName;
    }
    public void setUpdateUserName(String updateUserName){

        this.updateUserName = updateUserName;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}


