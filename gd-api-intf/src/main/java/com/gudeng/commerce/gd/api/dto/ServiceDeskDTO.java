package com.gudeng.commerce.gd.api.dto;
/** 
* @author  bdhuang 
* @date 创建时间：2016年4月18日 上午10:56:40 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class ServiceDeskDTO {
	
	//认证类型（1个人2企业）
    private String type;
    
    //认证状态(0:待认证 1:已认证;2:已驳回)
    private String status;

    //认证状态中文描述(待认证 ，已认证，已驳回)
    private String statusStr;
    
    //未读消息条数
	Integer msgCount ;
	
	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    


}
