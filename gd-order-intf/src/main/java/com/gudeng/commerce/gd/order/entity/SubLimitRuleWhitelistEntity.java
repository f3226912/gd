package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sub_limit_rule_whitelist")
public class SubLimitRuleWhitelistEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4561943593606411715L;

	private Integer limitruleId;

    private Long memberId;

    private String account;

    private Date createTime;

    @Column(name = "limitruleId")
    public Integer getLimitruleId(){

        return this.limitruleId;
    }
    public void setLimitruleId(Integer limitruleId){

        this.limitruleId = limitruleId;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "account")
    public String getAccount(){

        return this.account;
    }
    public void setAccount(String account){

        this.account = account;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
}


