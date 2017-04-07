package com.gudeng.commerce.gd.task.service;

import java.util.List;

import com.gudeng.commerce.gd.customer.dto.OfferPriceDTO;


/**
 * 报价定时任务
 * @author kangW
 *
 */
public interface NpsOfferPriceTaskService {
	
	/**
	 * 通知采购商 报价信息
	 * @throws Exception
	 */
	public void execute() throws Exception;
	
	/**
	 * 每天最小价格信息推送列表
	 * 0：00-18：00
	 * @return
	 */
	 public List<OfferPriceDTO> everyDayMinPriceList() throws Exception;

}
