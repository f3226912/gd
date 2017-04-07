package com.gudeng.commerce.gd.order.service;

import com.gudeng.commerce.gd.order.dto.OrderBillImportLlogDTO;

/**
 * @Description 交易账单导入日志相关服务
 * @Project gd-order-intf
 * @ClassName OrderBillImportLogService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public interface OrderBillImportLogService {

	/**
	 * @Description 添加导入日志记录
	 * @param dto
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月24日 下午4:01:18
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int addBillLogDTO(OrderBillImportLlogDTO dto) throws Exception;

	/**
	 * @Description 根据文件名获取导入日志记录
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月24日 下午4:01:32
	 * @Author lidong(dli@gdeng.cn)
	 */
	public OrderBillImportLlogDTO getOrderBillLogByFileName(String fileName) throws Exception;

}
