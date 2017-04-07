package com.gudeng.commerce.gd.order.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "re_car_member")
public class ReCarMemberEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long carID;

    private Long memberId;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    @Id
    @Column(name = "carID")
    public Long getCarID(){

        return this.carID;
    }
    public void setCarID(Long carID){

        this.carID = carID;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
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

