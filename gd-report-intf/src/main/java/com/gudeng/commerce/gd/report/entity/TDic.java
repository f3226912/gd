package com.gudeng.commerce.gd.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDic entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_dic")
public class TDic implements java.io.Serializable {

	// Fields

	private String seq;
	private String name;
	private String dicType;
	private String typeLabel;
	private String tableName;
	private String colName;
	private String operator;

	// Constructors

	/** default constructor */
	public TDic() {
	}

	/** minimal constructor */
	public TDic(String seq) {
		this.seq = seq;
	}

	/** full constructor */
	public TDic(String seq, String name, String dicType, String typeLabel,
			String tableName, String colName, String operator) {
		this.seq = seq;
		this.name = name;
		this.dicType = dicType;
		this.typeLabel = typeLabel;
		this.tableName = tableName;
		this.colName = colName;
		this.operator = operator;
	}

	// Property accessors
	@Id
	@Column(name = "seq", unique = true, nullable = false, length = 10)
	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "dic_type", length = 10)
	public String getDicType() {
		return this.dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	@Column(name = "type_label", length = 30)
	public String getTypeLabel() {
		return this.typeLabel;
	}

	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}

	@Column(name = "table_name", length = 30)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "col_name", length = 30)
	public String getColName() {
		return this.colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	@Column(name = "operator", length = 10)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}