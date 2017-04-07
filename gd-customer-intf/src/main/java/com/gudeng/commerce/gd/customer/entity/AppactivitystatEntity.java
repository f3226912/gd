package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "appactivitystat")
public class AppactivitystatEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 843685748990156445L;

    private Integer id;

    private String appType;

    private Long memberId;

    private String mobile;

    private String account;

    private String marketName;

    private Integer marketId;

    private Integer teamId;

    private String teamName;

    private String deviceUUID;

    private String deviceMEID;

    private String deviceIMEI;

    private String deviceICCID;

    private String regetype;

    private Byte nsyUserType;

    private Byte managementtype;

    private String categoryName;

    private Integer categoryId;

    private String cellphoneModel;

    private String cellphoneRAM;

    private String cellphoneROM;

    private String system;

    private String systemVersion;

    private String appVersion;

    private String appChannel;

    private String type;

    private Boolean isLogin;

    private Date createTime;

    private Date userCreateTime;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "appType")
    public String getAppType(){

        return this.appType;
    }
    public void setAppType(String appType){

        this.appType = appType;
    }
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "mobile")
    public String getMobile(){

        return this.mobile;
    }
    public void setMobile(String mobile){

        this.mobile = mobile;
    }
    @Column(name = "account")
    public String getAccount(){

        return this.account;
    }
    public void setAccount(String account){

        this.account = account;
    }
    @Column(name = "marketName")
    public String getMarketName(){

        return this.marketName;
    }
    public void setMarketName(String marketName){

        this.marketName = marketName;
    }
    @Column(name = "marketId")
    public Integer getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Integer marketId){

        this.marketId = marketId;
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
    @Column(name = "deviceUUID")
    public String getDeviceUUID(){

        return this.deviceUUID;
    }
    public void setDeviceUUID(String deviceUUID){

        this.deviceUUID = deviceUUID;
    }
    @Column(name = "deviceMEID")
    public String getDeviceMEID(){

        return this.deviceMEID;
    }
    public void setDeviceMEID(String deviceMEID){

        this.deviceMEID = deviceMEID;
    }
    @Column(name = "deviceIMEI")
    public String getDeviceIMEI(){

        return this.deviceIMEI;
    }
    public void setDeviceIMEI(String deviceIMEI){

        this.deviceIMEI = deviceIMEI;
    }
    @Column(name = "deviceICCID")
    public String getDeviceICCID(){

        return this.deviceICCID;
    }
    public void setDeviceICCID(String deviceICCID){

        this.deviceICCID = deviceICCID;
    }
    @Column(name = "regetype")
    public String getRegetype(){

        return this.regetype;
    }
    public void setRegetype(String regetype){

        this.regetype = regetype;
    }
    @Column(name = "nsyUserType")
    public Byte getNsyUserType(){

        return this.nsyUserType;
    }
    public void setNsyUserType(Byte nsyUserType){

        this.nsyUserType = nsyUserType;
    }
    @Column(name = "managementtype")
    public Byte getManagementtype(){

        return this.managementtype;
    }
    public void setManagementtype(Byte managementtype){

        this.managementtype = managementtype;
    }
    @Column(name = "categoryName")
    public String getCategoryName(){

        return this.categoryName;
    }
    public void setCategoryName(String categoryName){

        this.categoryName = categoryName;
    }
    @Column(name = "categoryId")
    public Integer getCategoryId(){

        return this.categoryId;
    }
    public void setCategoryId(Integer categoryId){

        this.categoryId = categoryId;
    }
    @Column(name = "cellphoneModel")
    public String getCellphoneModel(){

        return this.cellphoneModel;
    }
    public void setCellphoneModel(String cellphoneModel){

        this.cellphoneModel = cellphoneModel;
    }
    @Column(name = "cellphoneRAM")
    public String getCellphoneRAM(){

        return this.cellphoneRAM;
    }
    public void setCellphoneRAM(String cellphoneRAM){

        this.cellphoneRAM = cellphoneRAM;
    }
    @Column(name = "cellphoneROM")
    public String getCellphoneROM(){

        return this.cellphoneROM;
    }
    public void setCellphoneROM(String cellphoneROM){

        this.cellphoneROM = cellphoneROM;
    }
    @Column(name = "system")
    public String getSystem(){

        return this.system;
    }
    public void setSystem(String system){

        this.system = system;
    }
    @Column(name = "systemVersion")
    public String getSystemVersion(){

        return this.systemVersion;
    }
    public void setSystemVersion(String systemVersion){

        this.systemVersion = systemVersion;
    }
    @Column(name = "appVersion")
    public String getAppVersion(){

        return this.appVersion;
    }
    public void setAppVersion(String appVersion){

        this.appVersion = appVersion;
    }
    @Column(name = "appChannel")
    public String getAppChannel(){

        return this.appChannel;
    }
    public void setAppChannel(String appChannel){

        this.appChannel = appChannel;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name = "isLogin")
    public Boolean getIsLogin(){

        return this.isLogin;
    }
    public void setIsLogin(Boolean isLogin){

        this.isLogin = isLogin;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "userCreateTime")
    public Date getUserCreateTime(){

        return this.userCreateTime;
    }
    public void setUserCreateTime(Date userCreateTime){

        this.userCreateTime = userCreateTime;
    }
}


