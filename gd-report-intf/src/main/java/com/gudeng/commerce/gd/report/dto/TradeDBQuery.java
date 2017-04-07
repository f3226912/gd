package com.gudeng.commerce.gd.report.dto;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author mpan
 * @date 2016年6月14日 上午10:24:57
 */
public class TradeDBQuery extends DataDBQuery {

	private static final long serialVersionUID = -5852011025687260300L;
	private String orderStatus; // 订单状态
	private List<String> orderStatusIn; // 订单状态in
	private String isNogGoods; // 是否无商品
	private String timeFlag; // 1下单时间  2支付时间  2关闭时间
	private String xaxisDataType; // X轴数据类型 1小时  2日期

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<String> getOrderStatusIn() {
		return orderStatusIn;
	}

	public void setOrderStatusIn(List<String> orderStatusIn) {
		this.orderStatusIn = orderStatusIn;
	}

	public String getIsNogGoods() {
		return isNogGoods;
	}

	public void setIsNogGoods(String isNogGoods) {
		this.isNogGoods = isNogGoods;
	}

	public String getTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	public String getXaxisDataType() {
		return xaxisDataType;
	}

	public void setXaxisDataType(String xaxisDataType) {
		this.xaxisDataType = xaxisDataType;
	}

}
