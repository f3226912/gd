package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "promotion_source")
public class PromotionSourceEntity  implements java.io.Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -1018415662045153561L;

	private Long id;

    private String name;

	@Id
    @Column(name = "id")
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
}

