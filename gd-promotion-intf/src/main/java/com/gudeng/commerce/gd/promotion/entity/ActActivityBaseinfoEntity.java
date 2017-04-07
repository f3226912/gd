package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "act_activity_baseinfo")
public class ActActivityBaseinfoEntity  implements java.io.Serializable{

	public static final int EXCHANGE_TIME_NO_LIMIT = -1;
	//兑换次数为1次
	public static final int EXCHANGE_TIME_ONE = 1;
	
	public static final String ACTIVITY_LAUNCH_YES = "1";
	public static final String ACTIVITY_LAUNCH_NO = "0";
	public static final String ACTIVITY_LAUNCH_WAIT = "2";

	/**刮刮卡 */
	public static final int ACTIVITY_TYPE_GGK = 1 ;
	/**幸运大转盘 */
	public static final int ACTIVITY_TYPE_DZP = 2 ;
	/**摇一摇 */
	public static final int ACTIVITY_TYPE_YYY = 3 ;
	/**疯狂抢红包 */
	public static final int ACTIVITY_TYPE_QHB = 4 ;
	/**砸金蛋 */
	public static final int ACTIVITY_TYPE_ZJD = 5 ;
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Integer type;

    private Integer channel;

    private Integer userGroup;

    private Integer exchangeTime;

    private Date effectiveStartTime;

    private Date effectiveEndTime;

    private String launch;

    private Integer times;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    private Integer pv;
    
    private Integer version; //版本号

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "NAME")
    public String getName() {
  		return name;
  	}
  	public void setName(String name) {
  		this.name = name;
  	}

    @Column(name = "type")
    public Integer getType() {
  		return type;
  	}
  	public void setType(Integer type) {
  		this.type = type;
  	}

    @Column(name = "channel")
    public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}

    @Column(name = "user_group")
    public Integer getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

    @Column(name = "exchange_time")
    public Integer getExchangeTime() {
  		return exchangeTime;
  	}
  	public void setExchangeTime(Integer exchangeTime) {
  		this.exchangeTime = exchangeTime;
  	}

	@Column(name = "effective_start_time")
	public Date getEffectiveStartTime() {
		return effectiveStartTime;
	}
	public void setEffectiveStartTime(Date effectiveStartTime) {
		this.effectiveStartTime = effectiveStartTime;
	}
    @Column(name = "effective_end_time")
    public Date getEffectiveEndTime() {
		return effectiveEndTime;
	}
	public void setEffectiveEndTime(Date effectiveEndTime) {
		this.effectiveEndTime = effectiveEndTime;
	}
    @Column(name = "launch")
    public String getLaunch(){
        return this.launch;
    }
	public void setLaunch(String launch){
        this.launch = launch;
    }
    @Column(name = "times")
    public Integer getTimes() {
 		return times;
 	}
 	public void setTimes(Integer times) {
 		this.times = times;
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
    @Column(name = "pv")
	public Integer getPv() {
		return pv;
	}
	public void setPv(Integer pv) {
		this.pv = pv;
	}
	
	@Column(name = "version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}

