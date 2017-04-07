package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 农速通专线信息表
 * @author xiaojun
 */
@Entity(name = "nst_special_line")
public class NstSpecialLineEntity  implements java.io.Serializable{
	/**
	 * 专线，自动增长主键id
	 */
    private Long id;
    /**
     * 专线起始地
     */
    private String begin_areaId;
    /**
     * 专线目的地
     */
    private String end_areaId;

    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "begin_areaId")
    public String getBegin_areaId(){

        return this.begin_areaId;
    }
    public void setBegin_areaId(String begin_areaId){

        this.begin_areaId = begin_areaId;
    }
    @Column(name = "end_areaId")
    public String getEnd_areaId(){

        return this.end_areaId;
    }
    public void setEnd_areaId(String end_areaId){

        this.end_areaId = end_areaId;
    }
}

