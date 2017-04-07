package com.gudeng.commerce.gd.api.util;

/**
 * 区间值
 * @author wesley
 *
 */
public class RuleJsonDetail {
    private double startAmt; //区间开始值
    private double endAmt; //区间结束值
    private Integer integral; //此区间积分奖励
	public double getStartAmt() {
		return startAmt;
	}
	public void setStartAmt(double startAmt) {
		this.startAmt = startAmt;
	}
	public double getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(double endAmt) {
		this.endAmt = endAmt;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
    
}
