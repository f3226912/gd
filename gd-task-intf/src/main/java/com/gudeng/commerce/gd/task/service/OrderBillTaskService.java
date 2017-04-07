package com.gudeng.commerce.gd.task.service;

import com.gudeng.commerce.gd.order.dto.OrderBillImportLlogDTO;

/**
 * @Description 交易账单服务
 * @Project gd-admin-service
 * @ClassName OrderBillService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public interface OrderBillTaskService {

	/**
	 * @Description 导入账单文件数据到数据库中
	 * @return
	 * @CreationDate 2015年12月22日 下午2:46:06
	 * @Author lidong(dli@gdeng.cn)
	 */
	public boolean importData();

	/**
	 * @Description 人工导入文件
	 * @param fileName
	 * @return
	 * @CreationDate 2015年12月24日 下午5:46:05
	 * @Author lidong(dli@gdeng.cn)
	 */
	public String pushImportData(String fileName, OrderBillImportLlogDTO temp);

}