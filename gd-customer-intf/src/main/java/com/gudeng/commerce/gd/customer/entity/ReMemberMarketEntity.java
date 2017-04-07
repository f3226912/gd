package com.gudeng.commerce.gd.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "re_member_market")
public class ReMemberMarketEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8367641598493991007L;

	private Long memberId;

    private Long marketId;

    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "marketId")
    public Long getMarketId(){

        return this.marketId;
    }
    public void setMarketId(Long marketId){

        this.marketId = marketId;
    }
}

