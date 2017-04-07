package com.gudeng.commerce.gd.customer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/** 
* @author  bdhuang 
* @date 创建时间：2016年7月4日 上午9:14:52 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Entity(name = "specialcharacter")
public class SpecialcharacterEntity  implements java.io.Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4553130303878330340L;

	private Integer id;

    private String characters;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "characters")
    public String getCharacters(){

        return this.characters;
    }
    public void setCharacters(String characters){

        this.characters = characters;
    }

}
