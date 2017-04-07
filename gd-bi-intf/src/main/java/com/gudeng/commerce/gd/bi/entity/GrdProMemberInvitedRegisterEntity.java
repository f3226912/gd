package com.gudeng.commerce.gd.bi.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.bi.annotation.ExcelConf;

@Entity(name = "grd_pro_member_invited_register")
public class GrdProMemberInvitedRegisterEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6624758126802012564L;

	/**
    *
    */
    private Long id;
    /**
    *
    */
    private Long marketId;
    
    private int type;
    /**
    *
    */
    //@ExcelConf(excelHeader="所属市场", sort=1)
    private String marketName;
    /**
    *团队id
    */
    private Integer teamId;
    /**
    *团队名称
    */
    //@ExcelConf(excelHeader="团队名称", sort=2)
    private String teamName;
    /**
    *地推人员信息Id
    */
    private Integer grdId;
    /**
    *地推姓名
    */
    //@ExcelConf(excelHeader="地推姓名", sort=3)
    private String grdUserName;
    /**
    *地推人员手机号
    */
    //@ExcelConf(excelHeader="地推手机", sort=4)
    private String grdMobile;
    /**
    *会员Id
    */
    private Integer memberId;
    /**
    *会员账号
    */
    //@ExcelConf(excelHeader="会员账号", sort=5)
    private String account;
    /**
    *注册来源code
    */
    private String regetype;
    /**
    *注册来源显示值
    */
    //@ExcelConf(excelHeader="注册来源", sort=6)
    private String regetypeName;
    /**
    *会员姓名
    */
   // @ExcelConf(excelHeader="会员姓名", sort=7)
    private String realName;
    /**
    *会员手机号
    */
    //@ExcelConf(excelHeader="会员手机号", sort=8)
    private String memberMobile;
    /**
    *注册时间
    */
   // @ExcelConf(excelHeader="注册时间", sort=9)
    private Date registerTime;
    /**
    *
    */
    private String createUserId;
    /**
    *
    */
    private Date createTime;
    /**
    *
    */
    private String updateUserId;
    /**
    *
    */
    private Date updateTime;
    @Id
    @Column(name = "id")
    public Long getId(){
        return this.id;
    }
    public void setId(Long id){
        this.id = id;
    }
    @Column(name = "marketId")
    public Long getMarketId(){
        return this.marketId;
    }
    public void setMarketId(Long marketId){
        this.marketId = marketId;
    }
    @Column(name = "marketName")
    public String getMarketName(){
        return this.marketName;
    }
    public void setMarketName(String marketName){
        this.marketName = marketName;
    }
    @Column(name = "teamId")
    public Integer getTeamId(){
        return this.teamId;
    }
    public void setTeamId(Integer teamId){
        this.teamId = teamId;
    }
    @Column(name = "teamName")
    public String getTeamName(){
        return this.teamName;
    }
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
    @Column(name = "grdId")
    public Integer getGrdId(){
        return this.grdId;
    }
    public void setGrdId(Integer grdId){
        this.grdId = grdId;
    }
    @Column(name = "grdUserName")
    public String getGrdUserName(){
        return this.grdUserName;
    }
    public void setGrdUserName(String grdUserName){
        this.grdUserName = grdUserName;
    }
    @Column(name = "grdMobile")
    public String getGrdMobile(){
        return this.grdMobile;
    }
    public void setGrdMobile(String grdMobile){
        this.grdMobile = grdMobile;
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
    @Column(name = "regetype")
    public String getRegetype(){
        return this.regetype;
    }
    public void setRegetype(String regetype){
        this.regetype = regetype;
    }
    @Column(name = "regetypeName")
    public String getRegetypeName(){
    	if (regetype != null){
    		if (regetype.equalsIgnoreCase("0")){
    			regetypeName = "管理后台";
    		}else if (regetype.equalsIgnoreCase("1")){
    			regetypeName = "谷登农批网" ;
    		}else if (regetype.equalsIgnoreCase("2")){
    			regetypeName = "农速通(旧)" ;
    		}else if (regetype.equalsIgnoreCase("3")){
    			regetypeName = "农商友" ;
    		}else if (regetype.equalsIgnoreCase("4")){
    			regetypeName = "农商友-农批商" ;
    		}else if (regetype.equalsIgnoreCase("5")){
    			regetypeName = "农批友" ;
    		}else if (regetype.equalsIgnoreCase("6")){
    			regetypeName = "供应商" ;
    		}else if (regetype.equalsIgnoreCase("7")){
    			regetypeName = "POS刷卡" ;
    		}else if (regetype.equalsIgnoreCase("8")){
    			regetypeName = "微信授权" ;
    		}else if (regetype.equalsIgnoreCase("9")){
    			regetypeName = "农速通-货主" ;
    		}else if (regetype.equalsIgnoreCase("10")){
    			regetypeName = "农速通-司机" ;
    		}else if (regetype.equalsIgnoreCase("11")){
    			regetypeName = "农速通-物流公司" ;
    		}else{
    			regetypeName = "";
    		}
    	}        return this.regetypeName;
    }
    public void setRegetypeName(String regetypeName){
        this.regetypeName = regetypeName;
    }
    @Column(name = "realName")
    public String getRealName(){
        return this.realName;
    }
    public void setRealName(String realName){
        this.realName = realName;
    }
    @Column(name = "memberMobile")
    public String getMemberMobile(){
        return this.memberMobile;
    }
    public void setMemberMobile(String memberMobile){
        this.memberMobile = memberMobile;
    }
    @Column(name = "registerTime")
    public Date getRegisterTime(){
        return this.registerTime;
    }
    public void setRegisterTime(Date registerTime){
        this.registerTime = registerTime;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
    
}
