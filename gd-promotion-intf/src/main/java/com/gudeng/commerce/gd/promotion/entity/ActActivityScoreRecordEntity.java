package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "act_activity_score_record")
public class ActActivityScoreRecordEntity  implements java.io.Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8240327565350580004L;

	private Integer id;

    private Integer activityId;

    private Long userid;

    private Integer score;

    private Date receiveTime;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    /**
     * 被邀请人的openid
     * @Author dli@gdeng.cn
     * @date 2016/11/17 19:10
     */
    private String openId;

    /**
     * 自己或被邀请人的手机号
     * @Author dli@gdeng.cn
     * @date 2016/11/17 19:11
     */
    private String mobile;
    /**
     * 积分来源类型,1： 首登积分 2： 分享积分 3： 点赞积分 4：注册成功 5：（自己或自己邀请的人）成为金牌供应商
     * @Author dli@gdeng.cn
     * @date 2016/11/17 19:11
     */
    private String type;
    
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "openId")
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "activity_id")
    public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
    
    @Column(name = "userid")
    public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
 
    @Column(name = "score")
    public Integer getScore(){
        return this.score;
    }
	
	public void setScore(Integer score){
        this.score = score;
    }
    @Column(name = "receive_time")
    public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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

