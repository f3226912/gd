package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="act_re_user_activity")
public class ActReUserActivityEntity  implements java.io.Serializable{

	public static final String IS_FIRST_JOIN_NO = "0";
	public static final String IS_FIRST_JOIN_YES = "1";

	/**
	 *
	 */
	private static final long serialVersionUID = 7718253034076100800L;

	private Integer id;

    private Long userid;

    private Integer activityId;

    private Integer score;

    private Integer joinTimesLeft;

    private String firstJoin;

    private String account;

    private String mobile;

    @Id
    @Column(name="id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }

    @Column(name="userid")
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Column(name="activity_id")
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	@Column(name="score")
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name="join_times_left")
	public Integer getJoinTimesLeft() {
		return joinTimesLeft;
	}
	public void setJoinTimesLeft(Integer joinTimesLeft) {
		this.joinTimesLeft = joinTimesLeft;
	}

	@Column(name="firstJoin")
	public String getFirstJoin() {
		return firstJoin;
	}
	public void setFirstJoin(String firstJoin) {
		this.firstJoin = firstJoin;
	}

	@Column(name="account")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name="mobile")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "ActReUserActivityEntity [id=" + id + ", userid=" + userid + ", activityId=" + activityId + ", score="
				+ score + ", joinTimesLeft=" + joinTimesLeft + ", firstJoin=" + firstJoin + ", account=" + account
				+ ", mobile=" + mobile + "]";
	}

}

