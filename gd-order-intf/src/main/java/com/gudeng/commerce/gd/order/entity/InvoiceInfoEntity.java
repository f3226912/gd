package com.gudeng.commerce.gd.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "invoice_info")
public class InvoiceInfoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 5915536322457447907L;

	private Long id;
    
    /* 订单号 */
    private Long orderNo;
    
    /* 发票类型(1:纸质发票 2:电子发票 3：增值税发票) */
    private Integer type;
    
    /* 发票抬头*/
    private String title;
    
    /* 发票内容*/
    private String content;

    private String createUserId;

    private Date createTime;

    private String updateUserId;

    private Date updateTime;

    @Column(name = "createUserId")
    public String getCreateUserId() {

        return this.createUserId;
    }

    public void setCreateUserId(String createUserId) {

        this.createUserId = createUserId;
    }

    @Column(name = "createTime")
    public Date getCreateTime() {

        return this.createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    @Column(name = "updateUserId")
    public String getUpdateUserId() {

        return this.updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {

        this.updateUserId = updateUserId;
    }

    @Column(name = "updateTime")
    public Date getUpdateTime() {

        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
    }

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    @Column(name = "orderNo")
	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

    @Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    @Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}