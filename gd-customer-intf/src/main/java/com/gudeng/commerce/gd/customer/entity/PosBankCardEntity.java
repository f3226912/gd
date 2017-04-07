package com.gudeng.commerce.gd.customer.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/** 
* @author  bdhuang 
* @date 创建时间：2016年3月11日 下午4:48:31 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/

@Entity(name = "pos_bank_Card")
public class PosBankCardEntity  implements java.io.Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -980646924724638893L;

	private Long memberId;

    private String bankCardNo;

    private Date createTime;

    private Date updateTime;

    private String creatUserId;

    private String updateUserId;

    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
    }
    @Column(name = "bankCardNo")
    public String getBankCardNo(){

        return this.bankCardNo;
    }
    public void setBankCardNo(String bankCardNo){

        this.bankCardNo = bankCardNo;
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

