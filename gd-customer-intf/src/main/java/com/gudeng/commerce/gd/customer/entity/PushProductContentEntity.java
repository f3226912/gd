package com.gudeng.commerce.gd.customer.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 推送内容表
 * @author semon
 *
 */
@SuppressWarnings("unused")
@Entity(name = "pushproductcontent")
public class PushProductContentEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8816133917729776831L;

	/**
	 * 
	 */
	private Long id;

	/**
	 * 分类id
	 */
    private Long cateId;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 创建时间
     */
    private Date createTime;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "cateId")
    public Long getCateId(){

        return this.cateId;
    }
    public void setCateId(Long cateId){

        this.cateId = cateId;
    }
    @Column(name = "productId")
    public Long getProductId(){

        return this.productId;
    }
    public void setProductId(Long productId){

        this.productId = productId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
}

