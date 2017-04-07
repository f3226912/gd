package com.gudeng.commerce.gd.order.enm;

/**
 * 农速通请求接口 v1
 * 枚举类
 * @author TerryZhang
 */
public enum NstApiRequestV1Enum {

	ADD_GOODS_TRANSFER(1000, "v1/goodsUpdate/insertForShipper", "货主发布货源"),
	
	QUERY_PUBLISH_TRANSFER(1011, "v1/goodsQuery/queryMyPublishedForShipper", "查询发布货源"),
	
	QUERY_WAIT_CONFIRM_TRANSFER(1012, "v1/goodsQuery/queryMyUnconfirmedForShipper", "查询待确认货源"),
	
	QUERY_WAIT_ACCEPT_TRANSFER(1013, "v1/orderInfo/queryOrderInfoPage", "查询待收货货源"),
	
	QUERY_FINISH_TRANSFER(1014, "v1/orderInfo/queryOrderInfoPage", "查询已完成货源"),
	
	QUERY_OVERDUE_TRANSFER(1015, "v1/goodsQuery/queryMyOverdueForShipper", "查询过期货源"),
	
	QUERY_PUBLISHING_TRANSFER(1016, "v1/goodsQuery/queryMyPublishedForShipper", "查询分配中货源"),
	
	DELETE_GOODS_TRANSFER(1001, "v1/goodsUpdate/deleteById", "删除货源"),
	
	REFUSE_GOODS_TRANSFER(1002, "v1/orderBefore/rejectOrderBefore", "拒绝货源"),
	
	ACCEPT_GOODS_TRANSFER(1003, "v1/orderInfo/createOrderInfo", "接受货源"),
	
	CONFIRM_GOODS_TRANSFER(1004, "v1/orderInfo/confirmOrderInfo", "确认收货"),
	
	PAY_GOODS_TRANSFER(1005, "v1/orderBefore/rejectOrderBefore", "支付运费"),
	
	QUERY_TRANSFER_DETAIL(1020, "v1/goodsQuery/queryDetailAndLogForShipper", "查询货源信息"),
	
	QUERY_ORDER_DETAIL(1021, "v1/orderInfo/queryOrderInfo", "查询货源订单信息"),
	
	GET_NST_TOKEN(0001, "v1/member/getToken", "获取农速通token");

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
	
	public static String getDescByCode(int code){
		for (NstApiRequestV1Enum item :NstApiRequestV1Enum.values()){
			if (item.getCode() == code){
				return item.getDesc();
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
