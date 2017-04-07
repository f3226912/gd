package com.gudeng.commerce.info.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Reports entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "reports")
public class Reports implements java.io.Serializable {

	// Fields

	/** 
    * @Fields serialVersionUID TODO
    * @since Ver 2.0
    */   
    
    private static final long serialVersionUID = 4241014455963396818L;
    private Long id;
	private Long dataSourceId;
	private String name;
	private String menuId;
	private String type;
	private String state;
	private String parameter1;
	private String parameter2;
	private String parameter3;
	private String parameter4;
	private String parameter5;
	private String parameter6;
	private String parameter7;
	private String parameter8;
	private String parameter9;
	private String parameter10;
	private String createUserId;
	private Date createTime;
	private String updateUserId;
	private Date updateTime;
	private String show;

	// Constructors

	/** default constructor */
	public Reports() {
	}

	/** minimal constructor */
	public Reports(Long id, String name, Date createTime) {
		this.id = id;
		this.name = name;
		this.createTime = createTime;
	}

	/** full constructor */
	public Reports(Long id, Long dataSourceId, String name, String menuId,
			String type, String state, String parameter1, String parameter2,
			String parameter3, String parameter4, String parameter5,
			String parameter6, String parameter7, String parameter8,
			String parameter9, String parameter10, String createUserId,
			Date createTime, String updateUserId, Date updateTime) {
		this.id = id;
		this.dataSourceId = dataSourceId;
		this.name = name;
		this.menuId = menuId;
		this.type = type;
		this.state = state;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
		this.parameter4 = parameter4;
		this.parameter5 = parameter5;
		this.parameter6 = parameter6;
		this.parameter7 = parameter7;
		this.parameter8 = parameter8;
		this.parameter9 = parameter9;
		this.parameter10 = parameter10;
		this.createUserId = createUserId;
		this.createTime = createTime;
		this.updateUserId = updateUserId;
		this.updateTime = updateTime;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "dataSourceID")
	public Long getDataSourceId() {
		return this.dataSourceId;
	}

	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	@Column(name = "name", nullable = false, length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "menuID", length = 32)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "type", length = 50)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "state", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "parameter1", length = 50)
	public String getParameter1() {
		return this.parameter1;
	}

	public void setParameter1(String parameter1) {
		this.parameter1 = parameter1;
	}

	@Column(name = "parameter2", length = 50)
	public String getParameter2() {
		return this.parameter2;
	}

	public void setParameter2(String parameter2) {
		this.parameter2 = parameter2;
	}

	@Column(name = "parameter3", length = 50)
	public String getParameter3() {
		return this.parameter3;
	}

	public void setParameter3(String parameter3) {
		this.parameter3 = parameter3;
	}

	@Column(name = "parameter4", length = 50)
	public String getParameter4() {
		return this.parameter4;
	}

	public void setParameter4(String parameter4) {
		this.parameter4 = parameter4;
	}

	@Column(name = "parameter5", length = 50)
	public String getParameter5() {
		return this.parameter5;
	}

	public void setParameter5(String parameter5) {
		this.parameter5 = parameter5;
	}

	@Column(name = "parameter6", length = 50)
	public String getParameter6() {
		return this.parameter6;
	}

	public void setParameter6(String parameter6) {
		this.parameter6 = parameter6;
	}

	@Column(name = "parameter7", length = 50)
	public String getParameter7() {
		return this.parameter7;
	}

	public void setParameter7(String parameter7) {
		this.parameter7 = parameter7;
	}

	@Column(name = "parameter8", length = 50)
	public String getParameter8() {
		return this.parameter8;
	}

	public void setParameter8(String parameter8) {
		this.parameter8 = parameter8;
	}

	@Column(name = "parameter9", length = 50)
	public String getParameter9() {
		return this.parameter9;
	}

	public void setParameter9(String parameter9) {
		this.parameter9 = parameter9;
	}

	@Column(name = "parameter10", length = 50)
	public String getParameter10() {
		return this.parameter10;
	}

	public void setParameter10(String parameter10) {
		this.parameter10 = parameter10;
	}

	@Column(name = "createUserID", length = 32)
	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserID", length = 32)
	public String getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "show", length = 1)
	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}
	
}