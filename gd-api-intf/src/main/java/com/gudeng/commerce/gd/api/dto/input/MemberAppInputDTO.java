package com.gudeng.commerce.gd.api.dto.input;

public class MemberAppInputDTO {

	/** 会员账号 */
    private String account;

    /** 密码 */
    private String password;
    
    /**
     * 标示位, 会员类别（1谷登农批，2农速通，3农批宝，4.产地供应商，5.门岗， 其余待补）
     */
    private String level;
    
    /** 0为未注册， 1为已注册 */
    private String type;
    
    /** 注册来源 */
    private String regetype;
    
    /**
     * 农商友用户类型
     * 1下游批发商,2生鲜超市,3学校食堂,4食品加工工厂,5社区门店,6餐厅,7垂直生鲜,8其它
     */
    private String nsyUserType;
    
    /** 农速通token */
    private String nstToken;
    
    /** 会员id */
    private Integer memberId;
    
    /**
     * 时间戳
     */
    private long timestamp;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegetype() {
		return regetype;
	}

	public void setRegetype(String regetype) {
		this.regetype = regetype;
	}

	public String getNsyUserType() {
		return nsyUserType;
	}

	public void setNsyUserType(String nsyUserType) {
		this.nsyUserType = nsyUserType;
	}
	
	@Override
	public String toString(){
		return "account: " + getAccount() + ", password: " + getPassword() + ", level: " + getLevel() 
				+ ", type: " + getType() + ", regetype: " + getRegetype() +", nsyUserType: " + getNsyUserType();
	}

	public String getNstToken() {
		return nstToken;
	}

	public void setNstToken(String nstToken) {
		this.nstToken = nstToken;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
