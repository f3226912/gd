package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.Date;

import com.gudeng.commerce.gd.order.entity.SubAuditEntity;

/**
 * 实体: 补贴订单基本信息
 * @author lmzhang@gdeng.cn
 *
 */
public class SubAuditDTO extends SubAuditEntity implements Serializable{

	private static final long serialVersionUID = -9206108329856220490L;
	
	private String buyerAccount;	// 买家账号
	
	private Double orderAmount; 	//订单金额
	
	private Double discountAmount;	//抵扣金额
	
	private Double payAmount;		//付款金额
	
	private String payType; 		//支付方式 1钱包余额 2线下刷卡 3现金 12钱包余额+线下刷卡 13钱包余额+现金
	@SuppressWarnings("unused")
	private String payTypeStr;		//付款方式字符
	
	@SuppressWarnings("unused")
	private String subStatusStr;	//补贴状态字符
	
	private Date orderTime;			// 订单时间(是指下订单的时间 order_baseinfo.createTime)
	
	private Long businessId;  		//店铺id
	
	private Double sellSubAmount;   //卖家补贴金额
	
//	
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer();
//		sb.append("\r\n订单编号：").append(this.getOrderNo());
//		sb.append("\r\n订单金额：").append(this.getOrderAmount());
//		sb.append("\r\n抵扣金额：").append(this.getDiscountAmount());
//		sb.append("\r\n付款金额：").append(this.getPayAmount());
//		sb.append("\r\n付款方式：").append(this.getPayType());
//		sb.append("\r\n买家账号(di)：").append(this.getMemberID());
//		sb.append("\r\n买家账号：").append(this.getBuyerAccount());
//		sb.append("\r\n买家姓名：").append(this.getBuyerName());
//		sb.append("\r\n订单时间：").append(this.getOrderTime());
//		sb.append("\r\n卖家店铺：").append(this.getBuyerShop());
//		sb.append("\r\n补贴状态：").append(this.getSubStatus());
//		//sb.append("\r\n驳回原因：").append(this.getRejectReason());
//		sb.append("\r\n补贴状态：").append(this.getSubStatus());
//		return sb.toString();
//	}
	
	
	public String getPayTypeStr() {
		if(null!=payType && !payType.isEmpty()){
			if("1".equals(payType)){
				return "钱包余额";
			}else if("2".equals(payType)){
				return "线下刷卡";
			}else if("3".equals(payType)){
				return "现金";
			}else if("12".equals(payType)){	
				//产品要求：这个状态 如果 只抵扣了 钱包余额，还没上传pos刷卡  订单状态放到 待付款里面， 代付款状态的名称改成  付款未完成 .
				//但对于补贴审核表，所有待审核的单都是"已完成状态"，不存在以上判断的情况
				return "钱包余额+下线刷卡";
			}else if("13".equals(payType)){
				return "钱包余额+现金";
			}else{
				return "";
			}
		}else{
			return null;
		}
	}

	public String getSubStatusStr() {
		//补贴状态: 1-待补贴 2-系统驳回 3-已补贴 4-不予补贴
		String f = this.getSubStatus();
		if(null == f){
			return null;
		}else if("1".equals(f)){
			return "待补贴";
		}else if("2".equals(f)){
			return "系统驳回";
		}else if("3".equals(f)){
			return "已补贴";
		}else if("4".equals(f)){
			return "不予补贴";
		}
		else{
			return "";
		}
	}


	public String getBuyerAccount() {
		return buyerAccount;
	}
	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}


	public Long getBusinessId() {
		return businessId;
	}


	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}


	public Double getSellSubAmount() {
		return sellSubAmount;
	}


	public void setSellSubAmount(Double sellSubAmount) {
		this.sellSubAmount = sellSubAmount;
	}
	
}
