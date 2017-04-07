package com.gudeng.commerce.gd.customer.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Semon
 *
 */
@SuppressWarnings("unused")
@Entity(name = "pushrecord")
public class PushRecordEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7557019290680462972L;

	private Long id;

	/**
	 * 推送类型
	 * 1:广播2:单播 
	 */
    private String type;

    /**
     * 接收类型(0:全部,1:谷登农批网,2:农批宝,3:农速通)
     */
    private String receiveType;

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送内容
     */
    private String content;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 创建用户的id
     */
    private String createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

	/**
     * 状态(0:未读,1:已读)
     */
    private String state;
    
    /**
     * 跳转地址
     * 
     */
    private String redirectUrl;
    
    /**
     * 0代表定时器发送，1代表管理后台发送，其余待补
     * 
     * */
    Integer origin;
    
    
	@Column(name = "redirectUrl")
    public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	@Column(name = "origin")
    public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
    public Long getId(){

        return this.id;
    }
    public void setId(Long id){

        this.id = id;
    }
    @Column(name = "type")
    public String getType(){

        return this.type;
    }
    public void setType(String type){

        this.type = type;
    }
    @Column(name = "receiveType")
    public String getReceiveType(){

        return this.receiveType;
    }
    public void setReceiveType(String receiveType){

        this.receiveType = receiveType;
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
    @Column(name = "memberId")
    public Long getMemberId(){

        return this.memberId;
    }
    public void setMemberId(Long memberId){

        this.memberId = memberId;
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
    @Column(name = "state")
    public String getState(){

        return this.state;
    }
    public void setState(String state){

        this.state = state;
    }
}


