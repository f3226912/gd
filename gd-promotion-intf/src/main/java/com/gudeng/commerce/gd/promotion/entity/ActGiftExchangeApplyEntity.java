package com.gudeng.commerce.gd.promotion.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "act_gift_exchange_apply")
public class ActGiftExchangeApplyEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3865156496940176268L;

	private Integer id;

    private Integer activity_id;

    private Long userid;

    private Integer gift_id;

    private String status;

    private Date send_time;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;
    
    private Integer score;
    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "activity_id")
    public Integer getActivity_id(){

        return this.activity_id;
    }
    public void setActivity_id(Integer activity_id){

        this.activity_id = activity_id;
    }
    @Column(name = "userid")
    public Long getUserid(){

        return this.userid;
    }
    public void setUserid(Long userid){

        this.userid = userid;
    }
    @Column(name = "gift_id")
    public Integer getGift_id(){

        return this.gift_id;
    }
    public void setGift_id(Integer gift_id){

        this.gift_id = gift_id;
    }
    @Column(name = "STATUS")
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    @Column(name = "send_time")
    public Date getSend_time(){

        return this.send_time;
    }
   
	public void setSend_time(Date send_time){

        this.send_time = send_time;
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
    @Column(name = "score")
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
    
}

