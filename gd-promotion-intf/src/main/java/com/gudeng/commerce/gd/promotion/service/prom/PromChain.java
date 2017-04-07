package com.gudeng.commerce.gd.promotion.service.prom;

import java.util.Map;

/**
 * 对应活动责任链对象 处理对应活动的操作信息
 * 
 * @author Ailen
 *
 */
public abstract class PromChain {
	/**
	 * 下一个需要处理的活动
	 */
	private PromChain next;

	public PromChain getNext() {
		return next;
	}

	public void setNext(PromChain next) {
		this.next = next;
	}

	/**
	 * 责任链模式 调用当前活动excute方法 abstract 判断是否具备下一个需要处理的活动 有则继续调用下一个
	 * 
	 * @return
	 */
	public void checkProms(Map<String, Object> paramsMap) {
		this.excute(paramsMap);

		/*
		 * 判断是否还有需要处理的活动
		 */
		if (next == null) {
			return;
		}

		getNext().excute(paramsMap);
	}

	/**
	 * 各自不同活动具体实现方法
	 * 根据后台添加的活动信息处理
	 * @param paramsMap
	 */
	public abstract void excute(Map<String, Object> paramsMap);

}
