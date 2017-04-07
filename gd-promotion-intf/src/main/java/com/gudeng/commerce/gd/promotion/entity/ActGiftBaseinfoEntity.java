package com.gudeng.commerce.gd.promotion.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "act_gift_baseinfo")
public class ActGiftBaseinfoEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private Integer stockTotal;

    private Integer stockAvailable;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    @Id
    @Column(name = "id")
    public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
    @Column(name = "NAME")
    public String getName() {
 		return name;
 	}
 	public void setName(String name) {
 		this.name = name;
 	}
    @Column(name = "stock_total")
    public Integer getStockTotal() {
 		return stockTotal;
 	}
 	public void setStockTotal(Integer stockTotal) {
 		this.stockTotal = stockTotal;
 	}
    
    @Column(name = "stock_available")
    public Integer getStockAvailable() {
		return stockAvailable;
	}
	public void setStockAvailable(Integer stockAvailable) {
		this.stockAvailable = stockAvailable;
	}
    
    @Column(name = "createUserId")
    public String getCreateUserId(){

        return this.createUserId;
    }
	public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "updateUserId")
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    @Column(name = "updateTime")
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
}

