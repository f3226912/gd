package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.customer.annotation.ExcelConf;

@Entity(name = "member_login_log")
public class MemberLoginLogEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2678313067926024753L;

	/**
    *ID
    */
    private Integer id;
    /**
    *用户ID
    */
    @ExcelConf(excelHeader="用户ID", sort = 1)
    private Integer memberId;
    /**
    *用户帐号
    */
    @ExcelConf(excelHeader="账号", sort = 2)
    private String account;
    /**
    *手机号码
    */
    @ExcelConf(excelHeader="手机号", sort = 3)
    private String mobile;
    /**
    *真实姓名
    */
    @ExcelConf(excelHeader="姓名", sort = 4)
    private String realName;
    /**
    *登录来源:1.web主站,2.农速通,3.农商友,4.农批商,5.供应商
    */
    private String loginType;
    /**
    *登录ip
    */
    @ExcelConf(excelHeader="ip", sort = 5)
    private String ip;
    /**
    *登录时间
    */
    @ExcelConf(excelHeader="登录时间", sort = 6)
    private Date createTime;
    /**
    *说明
    */
    private String description;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "memberId")
    public Integer getMemberId(){
        return this.memberId;
    }
    public void setMemberId(Integer memberId){
        this.memberId = memberId;
    }
    @Column(name = "account")
    public String getAccount(){
        return this.account;
    }
    public void setAccount(String account){
        this.account = account;
    }
    @Column(name = "mobile")
    public String getMobile(){
        return this.mobile;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    @Column(name = "realName")
    public String getRealName(){
        return this.realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    @Column(name = "loginType")
    public String getLoginType(){
        return this.loginType;
    }
    public void setLoginType(String loginType){
        this.loginType = loginType;
    }
    @Column(name = "ip")
    public String getIp(){
        return this.ip;
    }
    public void setIp(String ip){
        this.ip = ip;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "description")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
