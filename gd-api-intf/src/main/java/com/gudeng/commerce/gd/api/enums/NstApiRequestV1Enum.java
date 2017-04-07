package com.gudeng.commerce.gd.api.enums;

/**
 * 农速通请求接口 v1
 * 枚举类
 * @author TerryZhang
 */
public enum NstApiRequestV1Enum {

	ADD_GOODS_TRANSFER(1000, "v1/goodsUpdate/insertForShipper", "货主发布货源"),
	
	DELETE_GOODS_TRANSFER(1001, "v1/goodsUpdate/deleteById", "删除货源"),

	QUERY_ORDER_TRANSFER(1002, "OrderInfoTrans/queryOrderInfoTransAndSourceExamineByOrderNo", "查询订单物流详情信息"),

	GET_NST_TOKEN(0001, "v1/member/getToken", "获取农速通token"),
	
	GET_NST_VALIDATEASSIGN(2000, "v1/assign/validateAssign", "验证农商友是否指派物流公司"),
	
	PAY_PREPAY_SUCC(2001,"v1/pay/payPrePaymenSucc","支付预付款成功后，通知农速通"),
	
	PAY_RESTPAY_SUCC(2002,"v1/pay/payFinalPaymenSucc","支付尾款成功后，通知农速通"),
	
	CALLSTATISTICS_NST_SUCC(2004,"v1/ad/saveCallResultMap","农速通电话统计"),
	
	REFUND(2003,"v1/platform/goodsClose","卖家取消或买家取消，退款时，通知农速通");
	

	private NstApiRequestV1Enum(int code, String uri, String desc) {
		this.code = code;
		this.uri = uri;
		this.desc = desc;
	}

	private final int code;
	
	private final String uri;
	
	private final String desc;

	public int getCode() {
		return code;
	}

	public String getUri() {
		return uri;
	}

	public String getDesc() {
		return desc;
	}
	
	public static String getUriByCode(int code){
		for (NstApiRequestV1Enum item :NstApiRequestV1Enum.values()){
			if (item.getCode() == code){
				return item.getUri();
			}
		}
		return "";
	}
	
	public static NstApiRequestV1Enum getByCode(int code){
		for (NstApiRequestV1Enum item :NstApiRequestV1Enum.values()){
			if (item.getCode() == code){
				return item;
			}
		}
		return null;
	}
}
