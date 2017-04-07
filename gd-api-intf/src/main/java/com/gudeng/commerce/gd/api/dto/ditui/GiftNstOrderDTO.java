package com.gudeng.commerce.gd.api.dto.ditui;

public class GiftNstOrderDTO {

	private String type;//  4表示农速通注册邀请，   *农速通业务类型。5表示发布货源，6表示货运订单,7表示信息订单',
	
	private String description ;
	
	private Integer code;
	
	private Integer id;//农速通业务的id

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
