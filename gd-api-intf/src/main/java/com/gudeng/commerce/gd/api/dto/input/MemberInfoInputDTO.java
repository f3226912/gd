package com.gudeng.commerce.gd.api.dto.input;

/**
 * 用户提交信息对象
 * @author TerryZhang
 */
public class MemberInfoInputDTO {

	private String orderNo;
	
	private String businessId;
	
	private String memberId;
	
	private String mobile;
	
	private String realName;
	
	private String searchStr;
	
	private String channel;
	
	/**
	 * 产品订单信息列表,  json array字符串, 含有:
	 * productId: 商品id
	 * quantity: 购买数量
	 */
	private String jProductDetails;
	
	private String version;
	
	/**
	 * 是否已经分享		1、已分享  0、未分享
	 * 
	 */
	private int isShare;
	
	
	public int getIsShare() {
		return isShare;
	}

	public void setIsShare(int isShare) {
		this.isShare = isShare;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getjProductDetails() {
		return jProductDetails;
	}

	public void setjProductDetails(String jProductDetails) {
		this.jProductDetails = jProductDetails;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
