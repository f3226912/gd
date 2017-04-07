package com.gudeng.commerce.info.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "datasource")
public class DatasourceEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8169544052990127107L;

	private Long id;

    private String name;

    private String tableName;

    private String frequency;

    private Date lastUpdateTime;

    private String mode;

    private String state;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;

    @Id
	@Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "name")
    public String getName(){

        return this.name;
    }
    public void setName(String name){

        this.name = name;
    }
    @Column(name = "tableName")
    public String getTableName(){

        return this.tableName;
    }
    public void setTableName(String tableName){

        this.tableName = tableName;
    }
    @Column(name = "frequency")
    public String getFrequency(){

        return this.frequency;
    }
    public void setFrequency(String frequency){

        this.frequency = frequency;
    }
    @Column(name = "lastUpdateTime")
    public Date getLastUpdateTime(){

        return this.lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime){

        this.lastUpdateTime = lastUpdateTime;
    }
    @Column(name = "mode")
    public String getMode(){

        return this.mode;
    }
    public void setMode(String mode){

        this.mode = mode;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "createUserID")
    public String getCreateUserID(){

        return this.createUserID;
    }
    public void setCreateUserID(String createUserID){

        this.createUserID = createUserID;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserID")
    public String getUpdateUserID(){

        return this.updateUserID;
    }
    public void setUpdateUserID(String updateUserID){

        this.updateUserID = updateUserID;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}
