package com.gudeng.commerce.gd.promotion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "grd_user_team")
public class GrdUserTeamEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3870464461207262951L;

	/**
    *地推用户id
    */
    private Integer grdUserId;
    /**
    *团队id
    */
    private Integer teamId;
    @Column(name = "grdUserId")
    public Integer getGrdUserId(){
        return this.grdUserId;
    }
    public void setGrdUserId(Integer grdUserId){
        this.grdUserId = grdUserId;
    }
    @Column(name = "teamId")
    public Integer getTeamId(){
        return this.teamId;
    }
    public void setTeamId(Integer teamId){
        this.teamId = teamId;
    }
}
