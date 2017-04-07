package com.gudeng.commerce.gd.order.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "sub_pay_category")
public class SubPayCategoryEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7258957250350077275L;

	private Integer ruleId;

    private Byte type;

    private String value;

    private String categoryCode;

    private Byte level;

    @Column(name = "ruleId")
    public Integer getRuleId(){

        return this.ruleId;
    }
    public void setRuleId(Integer ruleId){

        this.ruleId = ruleId;
    }
    @Column(name = "type")
    public Byte getType(){

        return this.type;
    }
    public void setType(Byte type){

        this.type = type;
    }
    @Column(name = "value")
    public String getValue(){

        return this.value;
    }
    public void setValue(String value){

        this.value = value;
    }
    @Column(name = "categoryCode")
    public String getCategoryCode(){

        return this.categoryCode;
    }
    public void setCategoryCode(String categoryCode){

        this.categoryCode = categoryCode;
    }
    @Column(name = "level")
    public Byte getLevel(){

        return this.level;
    }
    public void setLevel(Byte level){

        this.level = level;
    }
}

