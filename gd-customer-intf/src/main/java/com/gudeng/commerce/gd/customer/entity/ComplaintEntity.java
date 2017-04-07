package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "complaint")
public class ComplaintEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6807620180718288712L;

	private Long id;

    private String title;

    private String content;

    private String enclosure;

    private String member;

    private String contact;

    private Date createTime;

    private String state;

    private String customer;

    private Date replyTime;

    private String replyComtent;

    private String source;
    
    @Id
    @Column(name = "id")
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "title")
    public String getTitle(){

        return this.title;
    }
    public void setTitle(String title){

        this.title = title;
    }
    @Column(name = "content")
    public String getContent(){

        return this.content;
    }
    public void setContent(String content){

        this.content = content;
    }
    @Column(name = "enclosure")
    public String getEnclosure(){

        return this.enclosure;
    }
    public void setEnclosure(String enclosure){

        this.enclosure = enclosure;
    }
    @Column(name = "member")
    public String getMember(){

        return this.member;
    }
    public void setMember(String member){

        this.member = member;
    }
    @Column(name = "contact")
    public String getContact(){

        return this.contact;
    }
    public void setContact(String contact){

        this.contact = contact;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
    @Column(name = "customer")
    public String getCustomer(){

        return this.customer;
    }
    public void setCustomer(String customer){

        this.customer = customer;
    }
    @Column(name = "replyTime")
    public Date getReplyTime(){

        return this.replyTime;
    }
    public void setReplyTime(Date replyTime){

        this.replyTime = replyTime;
    }
    @Column(name = "replyComtent")
    public String getReplyComtent(){

        return this.replyComtent;
    }
    public void setReplyComtent(String replyComtent){

        this.replyComtent = replyComtent;
    }
    @Column(name = "source")
    public String getSource(){

        return this.source;
    }
    public void setSource(String source){

        this.source = source;
    }
}


