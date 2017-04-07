package com.gudeng.commerce.gd.order.dto;

import java.text.DecimalFormat;

import com.gudeng.commerce.gd.order.entity.SubAmountEntity;

/**
 * @Description: TODO(补贴金额DTO)
 * @author mpan
 * @date 2015年12月22日 下午10:03:32
 */
public class SubAmountDTO extends SubAmountEntity {

	private static final long serialVersionUID = 7721206113903307199L;
	
	public String getSubAmoutTotalStr(){
		if (getSubAmountTotal() == null) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置 
		return decimalFormat.format(this.getSubAmountTotal());
	}
	public String getSubAmountBalStr(){
		if (getSubAmountBal() == null) {
			return null;
		}
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置 
		return decimalFormat.format(this.getSubAmountBal());
	}

}
