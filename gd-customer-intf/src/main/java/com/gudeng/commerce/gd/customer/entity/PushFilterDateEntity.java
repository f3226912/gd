package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("unused")
@Entity(name = "pushfilterdate")
public class PushFilterDateEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3979590522481179294L;

	private Long id;

	/**
	 * 开始时间
	 */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "startDate")
    public Date getStartDate(){

        return this.startDate;
    }
    public void setStartDate(Date startDate){

        this.startDate = startDate;
    }
    @Column(name = "endDate")
    public Date getEndDate(){

        return this.endDate;
    }
    public void setEndDate(Date endDate){

        this.endDate = endDate;
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
}


