package com.gudeng.commerce.gd.order.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "car_weigh_pro")
public class CarWeighProEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7964666413858905219L;

	private Long cwpid;

    private String type;
    
    private Double tareWeigh; //默认皮重

    private Double zeroperWeigh;

    private Double thirtyperWeigh;

    private Double halfperWeigh;

    private Double allWeigh;

    private String createUserId;

    private Date updateTime;

    private String updateUserId;

    private Date createTime;

    private String status; //1启用 0不启用
    
    @Id
    @Column(name = "cwpid")
    public Long getCwpid(){

        return this.cwpid;
    }
    public void setCwpid(Long cwpid){

        this.cwpid = cwpid;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name="tareWeigh")
    public Double getTareWeigh() {
		return tareWeigh;
	}
	public void setTareWeigh(Double tareWeigh) {
		this.tareWeigh = tareWeigh;
	}
	@Column(name = "zeroperWeigh")
    public Double getZeroperWeigh(){

        return this.zeroperWeigh;
    }
    public void setZeroperWeigh(Double zeroperWeigh){

        this.zeroperWeigh = zeroperWeigh;
    }
    @Column(name = "thirtyperWeigh")
    public Double getThirtyperWeigh(){

        return this.thirtyperWeigh;
    }
    public void setThirtyperWeigh(Double thirtyperWeigh){

        this.thirtyperWeigh = thirtyperWeigh;
    }
    @Column(name = "halfperWeigh")
    public Double getHalfperWeigh(){

        return this.halfperWeigh;
    }
    public void setHalfperWeigh(Double halfperWeigh){

        this.halfperWeigh = halfperWeigh;
    }
    @Column(name = "allWeigh")
    public Double getAllWeigh(){

        return this.allWeigh;
    }
    public void setAllWeigh(Double allWeigh){

        this.allWeigh = allWeigh;
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
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "status")
    public String getStatus(){

        return this.status;
    }
    public void setStatus(String status){

        this.status = status;
    }
}

