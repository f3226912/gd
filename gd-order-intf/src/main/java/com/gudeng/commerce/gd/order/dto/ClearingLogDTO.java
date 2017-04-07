package com.gudeng.commerce.gd.order.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 清分明细
 * @date 2016年12月20日
 */
public class ClearingLogDTO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8565772833048363033L;
	private boolean success;
    private List<OrderClearDetail> data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<OrderClearDetail> getData() {
		return data;
	}
	public void setData(List<OrderClearDetail> data) {
		this.data = data;
	}

    
}


