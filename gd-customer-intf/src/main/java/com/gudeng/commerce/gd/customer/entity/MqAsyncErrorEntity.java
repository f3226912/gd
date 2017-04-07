package com.gudeng.commerce.gd.customer.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "mq_async_error")
public class MqAsyncErrorEntity  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7901691603660262714L;

	/**
    *主键id
    */
    private Integer id;
    /**
    *操作类型：0 创建 1 更新 2删除
    */
    private Integer crud;
    /**
    * mq 消息 Dto json
    */
    private String content;
    /**
    *创建时间
    */
    private Date createTime;
    /**
    *是否已经发送：0 未重新发送 1 已经重新发送
    */
    private Integer status;
    /**
    *标识为哪个mq：1为会员（member) 2为银行卡（bank) 其余待补
    */
    private Integer type;
    @Id
    @Column(name = "id")
    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    @Column(name = "crud")
    public Integer getCrud(){
        return this.crud;
    }
    public void setCrud(Integer crud){
        this.crud = crud;
    }
    @Column(name = "content")
    public String getContent(){
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }
    @Column(name = "createTime")
    public Date getCreateTime(){
        return this.createTime;
    }
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
    @Column(name = "status")
    public Integer getStatus(){
        return this.status;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
    @Column(name = "type")
    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }
}
