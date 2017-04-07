package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "re_category_banel_img")
public class ReCategoryBanelImgEntity  implements java.io.Serializable{
    
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3520575787787753020L;

	private Long categoryId;
	
	private Long groupId;
	
	private String groupName;

    private String banelImgUrl;

    private Long id;

    
    
    
    
	@Column(name = "groupId")
    public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	@Column(name = "groupName")
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	@Column(name = "categoryId")
    public Long getCategoryId(){

        return this.categoryId;
    }
    public void setCategoryId(Long categoryId){

        this.categoryId = categoryId;
    }
    @Column(name = "banelImgUrl")
    public String getBanelImgUrl(){

        return this.banelImgUrl;
    }
    public void setBanelImgUrl(String banelImgUrl){

        this.banelImgUrl = banelImgUrl;
    }
    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
}

