package com.gudeng.commerce.info.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "datasource_baidu")
public class DatasourceBaiduEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 594301552827338964L;

	private Long id;

    private Double PVcount;

    private Double UVcount;

    private Double IPcount;

    private Date lastUpdateTime;

    private Double signout;

    private Double avstop;

    private Double newuser;

    private Double olduser;

    private String state;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;
    
    private String client;

    @Id
	@Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "PVcount")
    public Double getPVcount(){

        return this.PVcount;
    }
    public void setPVcount(Double PVcount){

        this.PVcount = PVcount;
    }
    @Column(name = "UVcount")
    public Double getUVcount(){

        return this.UVcount;
    }
    public void setUVcount(Double UVcount){

        this.UVcount = UVcount;
    }
    @Column(name = "IPcount")
    public Double getIPcount(){

        return this.IPcount;
    }
    public void setIPcount(Double IPcount){

        this.IPcount = IPcount;
    }
    @Column(name = "lastUpdateTime")
    public Date getLastUpdateTime(){

        return this.lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime){

        this.lastUpdateTime = lastUpdateTime;
    }
    @Column(name = "signout")
    public Double getSignout(){

        return this.signout;
    }
    public void setSignout(Double signout){

        this.signout = signout;
    }
    @Column(name = "avstop")
    public Double getAvstop(){

        return this.avstop;
    }
    public void setAvstop(Double avstop){

        this.avstop = avstop;
    }
    @Column(name = "newuser")
    public Double getNewuser(){

        return this.newuser;
    }
    public void setNewuser(Double newuser){

        this.newuser = newuser;
    }
    @Column(name = "olduser")
    public Double getOlduser(){

        return this.olduser;
    }
    public void setOlduser(Double olduser){

        this.olduser = olduser;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "createUserID")
    public String getCreateUserID(){

        return this.createUserID;
    }
    public void setCreateUserID(String createUserID){

        this.createUserID = createUserID;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserID")
    public String getUpdateUserID(){

        return this.updateUserID;
    }
    public void setUpdateUserID(String updateUserID){

        this.updateUserID = updateUserID;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
    
    @Column(name = "client")
    public String getClient(){

        return this.client;
    }
    public void setClient(String client){

        this.client = client;
    }
}
