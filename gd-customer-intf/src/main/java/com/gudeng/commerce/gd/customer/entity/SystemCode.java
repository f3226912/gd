package com.gudeng.commerce.gd.customer.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description 数据字典实体类
 * @Project gd-home-intf
 * @ClassName SystemCode.java
 * @Author lidong(dli@cnagri-products.com)
 * @CreationDate 2015年10月22日 下午2:45:44
 * @Version V1.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
@Entity
@Table(name = "system_code", catalog = "gudeng")
public class SystemCode implements java.io.Serializable {

	// Fields

	/**
	 * @Fields serialVersionUID TODO
	 * @since Ver 1.0
	 */

	private static final long serialVersionUID = -6623621188057872935L;
	private Long id;// 主键
	private String codeKey;// 键
	private String codeValue;// 值
	private String type;// 类别
	private Integer sort;// 排序
	private String state;// 状态

	// Constructors

	/** default constructor */
	public SystemCode() {
	}

	/** full constructor */
	public SystemCode(String codeKey, String codeValue, String type, Integer sort, String state) {
		this.codeKey = codeKey;
		this.codeValue = codeValue;
		this.type = type;
		this.sort = sort;
		this.state = state;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "codeKey")
	public String getCodeKey() {
		return this.codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	@Column(name = "codeValue")
	public String getCodeValue() {
		return this.codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "state")
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}