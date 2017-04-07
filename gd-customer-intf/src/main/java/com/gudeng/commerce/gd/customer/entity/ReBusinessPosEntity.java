package com.gudeng.commerce.gd.customer.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/** 
 * @author  bdhuang 
 * @date 创建时间：2016年3月11日 下午4:46:43 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */

@Entity(name = "re_business_pos")
public class ReBusinessPosEntity  implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5672102083109840721L;

	private Long id;

	private Long businessId;

	private String posNumber;

	private Integer type;

	private String hasClear;

	private Date createTime;

	private Date updateTime;

	private String creatUserId;

	private String updateUserId;
    
	private String state;

	@Column(name = "state")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	@Id
	@Column(name = "id")
	public Long getId(){

		return this.id;
	}
	public void setId(Long id){

		this.id = id;
	}
	@Column(name = "businessId")
	public Long getBusinessId(){

		return this.businessId;
	}
	public void setBusinessId(Long businessId){

		this.businessId = businessId;
	}
	@Column(name = "posNumber")
	public String getPosNumber(){

		return this.posNumber;
	}
	public void setPosNumber(String posNumber){

		this.posNumber = posNumber;
	}
	@Column(name = "type")
	public Integer getType(){

		return this.type;
	}
	@Column(name = "hasClear") 
	public String getHasClear() {
		return hasClear;
	}
	public void setHasClear(String hasClear) {
		this.hasClear = hasClear;
	}
	public void setType(Integer type){

		this.type = type;
	}
	@Column(name = "createTime")
	public Date getCreateTime(){

		return this.createTime;
	}
	public void setCreateTime(Date createTime){

		this.createTime = createTime;
	}
	@Column(name = "updateTime")
	public Date getUpdateTime(){

		return this.updateTime;
	}
	public void setUpdateTime(Date updateTime){

		this.updateTime = updateTime;
	}
	@Column(name = "creatUserId")
	public String getCreatUserId(){

		return this.creatUserId;
	}
	public void setCreatUserId(String creatUserId){

		this.creatUserId = creatUserId;
	}
	@Column(name = "updateUserId")
	public String getUpdateUserId(){

		return this.updateUserId;
	}
	public void setUpdateUserId(String updateUserId){

		this.updateUserId = updateUserId;
	}
}

