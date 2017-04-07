package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "grd_pro_perten")
public class GrdProPertenEntity  implements java.io.Serializable{
    /**
    *主键，自增
    */
    private Integer id;
    /**
    *农速通业务类型。5表示发布货源，6表示货运订单,7表示信息订单',
    */
    private Integer type;
    /**
    *由年+月+ x组合产生，如2016071 表示2016年7月上旬
    */
    private Integer code;
    /**
    *是否已经发放礼品,0未发放，1已经发放
    */
    private Integer status;
    /**
    *用户id，取member_baseinfo表的id
    */
    private Integer memberId;
    /**
    *手机号
    */
    private String mobile;
    /**
    *姓名
    */
    private String realname;
    /**
    *数量
    */
    private Integer count;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "type")
    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }
    @Column(name = "code")
    public Integer getCode(){
        return this.code;
    }
    public void setCode(Integer code){
        this.code = code;
    }
    @Column(name = "status")
    public Integer getStatus(){
        return this.status;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }
    @Column(name = "mobile")
    public String getMobile(){
        return this.mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    @Column(name = "realname")
    public String getRealname(){
        return this.realname;
    }
    public void setRealname(String realname){
        this.realname = realname;
    }
    @Column(name = "count")
    public Integer getCount(){
        return this.count;
    }
    public void setCount(Integer count){
        this.count = count;
    }
}
