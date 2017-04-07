package com.gudeng.commerce.info.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "sysuserboard")
public class SysUserBoard implements java.io.Serializable {
    private static final long serialVersionUID = -2359776935295690989L;

    private String rmID;

    private String userID;

    private Long boardID;

    private String createUserID;

    private Date createTime;
    private String type;

    @Column(name = "rmID")
    public String getRmID() {

        return this.rmID;
    }

    public void setRmID(String rmID) {

        this.rmID = rmID;
    }

    @Column(name = "userID")
    public String getUserID() {

        return this.userID;
    }

    public void setUserID(String userID) {

        this.userID = userID;
    }

    @Column(name = "boardID")
    public Long getBoardID() {

        return this.boardID;
    }

    public void setBoardID(Long boardID) {

        this.boardID = boardID;
    }

    @Column(name = "createUserID")
    public String getCreateUserID() {

        return this.createUserID;
    }

    public void setCreateUserID(String createUserID) {

        this.createUserID = createUserID;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {

        return this.createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
