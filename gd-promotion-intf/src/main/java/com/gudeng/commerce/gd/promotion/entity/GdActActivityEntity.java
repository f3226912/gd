package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.gudeng.commerce.gd.promotion.annotation.ExcelConf;

@Entity(name = "gd_act_activity")
public class GdActActivityEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1234259815239277699L;

	/**
    *活动ID
    */
    private Integer id;
    /**
    *活动编号
    */
    @ExcelConf(excelHeader="活动编号", sort=0)
    private String code;
    /**
    *活动名称
    */
    @ExcelConf(excelHeader="活动名称", sort=1)
    private String name;
    /**
    *活动类型（1:6+1促销）存ID
    */
    private Integer type;
    
	@ExcelConf(excelHeader="活动类型", sort=2)
	private String typeString;
	    /**
    *活动开始时间
    */
    private String startTime;
    /**
    *活动结束时间
    */
    private String endTime;
    /**
    *活动状态（1有效 0无效）  当前6+1促销活动，填1
    */
    private Integer state;
    
    @ExcelConf(excelHeader="活动状态", sort=4)
    private String stateString ;
    /**
    *版本号
    */
    private Integer version;
    /**
    *创建时间
    */
    @ExcelConf(excelHeader="创建时间", sort=3)
    private String createTime;
    /**
    *创建人
    */
    @ExcelConf(excelHeader="创建人", sort=8)
    private String createUserId;
    /**
    *
    */
    private String updateUserId;
    /**
    *
    */
    private String updateTime;
    
    private Integer isNew; // 1：新 2：旧
    private String isReverse; // 是否支持逆向刷卡0否1可以
    
    @Column(name="isReverse")
	public String getIsReverse() {
		return isReverse;
	}
	public void setIsReverse(String isReverse) {
		this.isReverse = isReverse;
	}
	public String getStateString() {
		if (null != state){
			if (0 == state){
				stateString = "禁用";
			}else if (1 == state){
				stateString = "启用";
			}else if (2 == state){
				stateString = "结束";
			}
		}else {
			stateString =  "" ;
		}
		return stateString;
	}
	public void setStateString(String stateString) {
		this.stateString = stateString;
	}
	public String getTypeString() {
		if(type==null)
			return typeString;
		if (type==1){
			typeString = "刷卡补贴";
		} else if(type==2) {
			typeString = "市场佣金";
		} else if(type==3) {
			typeString = "平台佣金";
		} else if(type==4) {
			typeString = "预约金/违约金";
		} else if(type==5) {
			typeString = "物流配送";
		}
		return typeString;
	}
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "code")
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    @Column(name = "name")
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Column(name = "type")
    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }
    @Column(name = "startTime")
    public String getStartTime(){
        return this.startTime;
    }
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    @Column(name = "endTime")
    public String getEndTime(){
        return this.endTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    @Column(name = "state")
    public Integer getState(){
        return this.state;
    }
    public void setState(Integer state){
        this.state = state;
    }
    @Column(name = "version")
    public Integer getVersion(){
        return this.version;
    }
    public void setVersion(Integer version){
        this.version = version;
    }
    @Column(name = "createTime")
    public String getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    @Column(name = "createUserId")
    public String getCreateUserId(){
        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){
        this.createUserId = createUserId;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){
        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){
        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public String getUpdateTime(){
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime){
                this.updateTime = updateTime;
    }
    @Column(name = "isNew")
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
}
