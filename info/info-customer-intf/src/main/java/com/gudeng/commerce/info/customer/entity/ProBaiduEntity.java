package com.gudeng.commerce.info.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pro_baidu")
public class ProBaiduEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7292408708011186226L;

	private Long ID;

    private Long reportsID;

    private String frequency;

    private Date datatimes;

    private Double PVcount;

    private Double UVcount;

    private Double IPcount;

    private Double signout;

    private Double avstop;

    private Double avvisit;

    private Double newuser;

    private Double olduser;

    private Double PVsum;

    private Double UVsum;

    private Double avvisitsum;

    private String state;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;
    @Id
    @Column(name = "ID")
    public Long getID(){

        return this.ID;
    }
    public void setID(Long ID){

        this.ID = ID;
    }
    @Column(name = "reportsID")
    public Long getReportsID(){

        return this.reportsID;
    }
    public void setReportsID(Long reportsID){

        this.reportsID = reportsID;
    }
    @Column(name = "frequency")
    public String getFrequency(){

        return this.frequency;
    }
    public void setFrequency(String frequency){

        this.frequency = frequency;
    }
    @Column(name = "datatimes")
    public Date getDatatimes(){

        return this.datatimes;
    }
    public void setDatatimes(Date datatimes){

        this.datatimes = datatimes;
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
    @Column(name = "avvisit")
    public Double getAvvisit(){

        return this.avvisit;
    }
    public void setAvvisit(Double avvisit){

        this.avvisit = avvisit;
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
    @Column(name = "PVsum")
    public Double getPVsum(){

        return this.PVsum;
    }
    public void setPVsum(Double PVsum){

        this.PVsum = PVsum;
    }
    @Column(name = "UVsum")
    public Double getUVsum(){

        return this.UVsum;
    }
    public void setUVsum(Double UVsum){

        this.UVsum = UVsum;
    }
    @Column(name = "avvisitsum")
    public Double getAvvisitsum(){

        return this.avvisitsum;
    }
    public void setAvvisitsum(Double avvisitsum){

        this.avvisitsum = avvisitsum;
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
}

