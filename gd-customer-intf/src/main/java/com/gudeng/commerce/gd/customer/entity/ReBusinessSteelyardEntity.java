package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "re_business_steelyard")
public class ReBusinessSteelyardEntity implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/**主键 */
	private Long id;
	
	/**商铺ID */
	private Long businessId;
	
	/**秤mac */
	private String macAddr;
	
	/**秤ID */
	private String stlydId;
	
	/**创建人ID */
	private String createUserId;
	
	/**创建时间 */
	private Date createTime;

    /**更新人ID */
    private String updateUserId;
    
	/**更新时间 */
    private Date updateTime;

    @Id
    @Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "businessId")
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	@Column(name = "macAddr")
	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	@Column(name = "stlydId")
	public String getStlydId() {
		return stlydId;
	}

	public void setStlydId(String stlydId) {
		this.stlydId = stlydId;
	}

	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "updateUserId")
	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "updateTime")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
