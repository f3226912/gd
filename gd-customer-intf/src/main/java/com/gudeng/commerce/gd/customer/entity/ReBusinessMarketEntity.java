package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "re_business_market")
public class ReBusinessMarketEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 341237000119201575L;


    private Long businessId;

    private Long marketId;
    
    private String marketName;
    
    
    
    @Column(name = "businessId")
    public Long getBusinessId(){

        return this.businessId;
    }
    public void setBusinessId(Long businessId){

        this.businessId = businessId;
    }
    @Column(name = "marketId")
    public Long getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Long marketId){

        this.marketId = marketId;
    }
    
    @Column(name = "marketName")
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
    
    
}

