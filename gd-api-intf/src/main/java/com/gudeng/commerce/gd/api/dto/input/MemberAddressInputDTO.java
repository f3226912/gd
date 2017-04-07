package com.gudeng.commerce.gd.api.dto.input;

import java.io.Serializable;

public class MemberAddressInputDTO implements Serializable{

	private static final long serialVersionUID = -1524368559179756478L;
	
	private Long memberAddressId; //货源订单id 

	private Long memberId;      //下单用户id
	
	private Long s_provinceId; //发货省市区ID;
	
	private Long s_cityId;
	
	private Long s_areaId;
	
	private Long f_provinceId;  //收货省市区ID;
	
	private Long f_cityId;
	
	private Long f_areaId;
	
	private String goodsType;    //货物类型
	
	private String totalWeight;  //重量
	
	private String totalSize;    //体积
	
	private String sendDate;    //发货日期 年-月-日
	
	private String sendDateType; //上下午
	
	private String carType;      //车型
	
	private String price;        //价格
	
	private String remark;       //备注
	
	private String contactName;          //联系人
	
	private String s_detailed_address;   //详细地址
	
	private String f_detailed_address;   //详细地址
	
	private String selectedList;         //选中订单/商品
	
	//1谷登农批,2农速通,3农商友,4产地供应商,5门岗, 6农批商
	private String clients;
	
	/**
	 * 区别 同城 和干线 sourceType 0干线，1同城
	 */
	private String sourceType;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getS_provinceId() {
		return s_provinceId;
	}

	public void setS_provinceId(Long s_provinceId) {
		this.s_provinceId = s_provinceId;
	}

	public Long getS_cityId() {
		return s_cityId;
	}

	public void setS_cityId(Long s_cityId) {
		this.s_cityId = s_cityId;
	}

	public Long getS_areaId() {
		return s_areaId;
	}

	public void setS_areaId(Long s_areaId) {
		this.s_areaId = s_areaId;
	}

	public Long getF_provinceId() {
		return f_provinceId;
	}

	public void setF_provinceId(Long f_provinceId) {
		this.f_provinceId = f_provinceId;
	}

	public Long getF_cityId() {
		return f_cityId;
	}

	public void setF_cityId(Long f_cityId) {
		this.f_cityId = f_cityId;
	}

	public Long getF_areaId() {
		return f_areaId;
	}

	public void setF_areaId(Long f_areaId) {
		this.f_areaId = f_areaId;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getS_detailed_address() {
		return s_detailed_address;
	}

	public void setS_detailed_address(String s_detailed_address) {
		this.s_detailed_address = s_detailed_address;
	}

	public String getF_detailed_address() {
		return f_detailed_address;
	}

	public void setF_detailed_address(String f_detailed_address) {
		this.f_detailed_address = f_detailed_address;
	}

	public String getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(String selectedList) {
		this.selectedList = selectedList;
	}

	public Long getMemberAddressId() {
		return memberAddressId;
	}

	public void setMemberAddressId(Long memberAddressId) {
		this.memberAddressId = memberAddressId;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getSendDateType() {
		return sendDateType;
	}

	public void setSendDateType(String sendDateType) {
		this.sendDateType = sendDateType;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getClients() {
		return clients;
	}

	public void setClients(String clients) {
		this.clients = clients;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MemberAddressInputDTO [memberAddressId=" + memberAddressId + ", memberId=" + memberId
				+ ", s_provinceId=" + s_provinceId + ", s_cityId=" + s_cityId + ", s_areaId=" + s_areaId
				+ ", f_provinceId=" + f_provinceId + ", f_cityId=" + f_cityId + ", f_areaId=" + f_areaId
				+ ", goodsType=" + goodsType + ", totalWeight=" + totalWeight + ", totalSize=" + totalSize
				+ ", sendDate=" + sendDate + ", sendDateType=" + sendDateType + ", carType=" + carType + ", price="
				+ price + ", remark=" + remark + ", contactName=" + contactName + ", s_detailed_address="
				+ s_detailed_address + ", f_detailed_address=" + f_detailed_address + ", selectedList=" + selectedList
				+ ", clients=" + clients + ", sourceType=" + sourceType + "]";
	}
	
}
