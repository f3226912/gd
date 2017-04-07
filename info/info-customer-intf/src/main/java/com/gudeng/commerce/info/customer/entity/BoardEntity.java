package com.gudeng.commerce.info.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "board")
public class BoardEntity implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1271994505541056098L;

    private Long id;

    private String name;

    private String menuId;

    private Date valueDate;

    private Date valueTime;

    private String values;

    private String state;

    private String createUserID;

    private Date createTime;

    private String updateUserID;

    private Date updateTime;

    private String sort1;// 分类排序
    private String sort2;// 综合看板排序

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    @Column(name = "name")
    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Column(name = "menuId")
    public String getMenuId() {

        return this.menuId;
    }

    public void setMenuId(String menuId) {

        this.menuId = menuId;
    }

    @Column(name = "valueDate")
    public Date getValueDate() {

        return this.valueDate;
    }

    public void setValueDate(Date valueDate) {

        this.valueDate = valueDate;
    }

    @Column(name = "valueTime")
    public Date getValueTime() {

        return this.valueTime;
    }

    public void setValueTime(Date valueTime) {

        this.valueTime = valueTime;
    }

    @Column(name = "values")
    public String getValues() {

        return this.values;
    }

    public void setValues(String values) {

        this.values = values;
    }

    @Column(name = "state")
    public String getState() {

        return this.state;
    }

    public void setState(String state) {

        this.state = state;
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

    @Column(name = "updateUserID")
    public String getUpdateUserID() {

        return this.updateUserID;
    }

    public void setUpdateUserID(String updateUserID) {

        this.updateUserID = updateUserID;
    }

    @Column(name = "updateTime")
    public Date getUpdateTime() {

        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
    }
    @Column(name = "sort1")
    public String getSort1() {
        return sort1;
    }

    public void setSort1(String sort1) {
        this.sort1 = sort1;
    }
    @Column(name = "sort2")
    public String getSort2() {
        return sort2;
    }

    public void setSort2(String sort2) {
        this.sort2 = sort2;
    }
    
    
}