package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sensitive_log")
public class SensitiveLogEntity  implements java.io.Serializable{
    /**
    *ID
    */
    private Integer id;
    /**
    *敏感词
    */
    private String words;
    /**
    *创建时间,过滤时间
    */
    private Date createTime;
    /**
    *过滤来源，1为web主站，2为api
    */
    private Integer type;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "words")
    public String getWords(){
        return this.words;
    }
    public void setWords(String words){
        this.words = words;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "type")
    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }
}
