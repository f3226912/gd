package com.gudeng.commerce.gd.promotion.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "gd_act_activity_comm")
public class GdActActivityCommEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5266708317062708418L;

	private Integer id;

    private Integer actId;

    private String commRuleType;

    private String ruleJson;

    private String creatUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "actId")
    public Integer getActId(){

        return this.actId;
    }
    public void setActId(Integer actId){

        this.actId = actId;
    }
    @Column(name = "commRuleType")
    public String getCommRuleType(){

        return this.commRuleType;
    }
    public void setCommRuleType(String commRuleType){

        this.commRuleType = commRuleType;
    }
    @Column(name = "ruleJson")
    public String getRuleJson(){

        return this.ruleJson;
    }
    public void setRuleJson(String ruleJson){

        this.ruleJson = ruleJson;
    }
    @Column(name = "creatUserId")
    public String getCreatUserId(){

        return this.creatUserId;
    }
    public void setCreatUserId(String creatUserId){

        this.creatUserId = creatUserId;
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

