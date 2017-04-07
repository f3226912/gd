package com.gudeng.commerce.gd.supplier.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "product_picture")
public class ProductPictureEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1167110745672329929L;

	private Long id;

    private Long productId;

    private Integer pictureType;

    private String urlOrg;

    private String url650;

    private String url400;

    private String url170;

    private String url120;

    private String url60;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

	@Id
	@Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    
    @Column(name = "productId")
    public Long getProductId(){

        return this.productId;
    }
    public void setProductId(Long productId){

        this.productId = productId;
    }
    
    @Column(name = "pictureType")
    public Integer getPictureType(){

        return this.pictureType;
    }
    public void setPictureType(Integer pictureType){

        this.pictureType = pictureType;
    }
    
    @Column(name = "urlOrg")
    public String getUrlOrg(){

        return this.urlOrg;
    }
    public void setUrlOrg(String urlOrg){

        this.urlOrg = urlOrg;
    }
    
    @Column(name = "url650")
    public String getUrl650(){

        return this.url650;
    }
    public void setUrl650(String url650){

        this.url650 = url650;
    }
    
    @Column(name = "url400")
    public String getUrl400(){

        return this.url400;
    }
    public void setUrl400(String url400){

        this.url400 = url400;
    }
    
    @Column(name = "url170")
    public String getUrl170(){

        return this.url170;
    }
    public void setUrl170(String url170){

        this.url170 = url170;
    }
    
    @Column(name = "url120")
    public String getUrl120(){

        return this.url120;
    }
    public void setUrl120(String url120){

        this.url120 = url120;
    }
    
    @Column(name = "url60")
    public String getUrl60(){

        return this.url60;
    }
    public void setUrl60(String url60){

        this.url60 = url60;
    }
    
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
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
    
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}

