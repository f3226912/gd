package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;

/**
 * 批量增加订单信息输入dto
 * @author TerryZhang
 */
public class OrderBatchAddInputDTO implements Serializable {
	

	private static final long serialVersionUID = 2945505037910507891L;
	
	/**
	 * 用户id
	 */
	private String memberId;
	
	/**
	 * 订单来源 1农批商APP 2农商友APP 3POS机 4智能秤
	 */
	private String orderSource;
	
	/**
	 * 2 农速通 货主 3 农速通 物流公司 4 农商友 5 农商友 - 农批商 6 农商友-供应商
	 */
	
	private String clients;
	
	/**
	 * 渠道 1 android; 2 ios; 3 pc
	 */
	private String channel;
	
	/**
	 * 商铺商品订单信息
	 */
	private String businessDetailsJsonList;
	
	/**
	 * 收货地址信息
	 */
	private String jsonAddress;
	
	/**
	 * 是否清除购物车标志 0否 1是
	 */
	private String flag = "1";

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getJsonAddress() {
		return jsonAddress;
	}

	public void setJsonAddress(String jsonAddress) {
		this.jsonAddress = jsonAddress;
	}

	public String getBusinessDetailsJsonList() {
		return businessDetailsJsonList;
	}

	public void setBusinessDetailsJsonList(String businessDetailsJsonList) {
		this.businessDetailsJsonList = businessDetailsJsonList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
