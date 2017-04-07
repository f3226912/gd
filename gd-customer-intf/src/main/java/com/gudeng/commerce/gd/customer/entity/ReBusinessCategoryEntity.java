package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "re_business_category")
public class ReBusinessCategoryEntity  implements java.io.Serializable{
   

    /**
	 * 
	 */    

	private static final long serialVersionUID = 8064216921838233642L;

	private Long businessId;

    private Long categoryId;
    
    private String businessType;//营业类型标识（主营：0、兼营：1）
    
    @Column(name = "businessType")
    public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    
    @Column(name = "categoryId")
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
    
    
}

