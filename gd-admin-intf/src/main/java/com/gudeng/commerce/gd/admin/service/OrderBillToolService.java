package com.gudeng.commerce.gd.admin.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderBillDTO;
import com.gudeng.commerce.gd.order.dto.PageQueryResultDTO;
import com.gudeng.commerce.gd.order.dto.PosBankCardPayRecordDTO;

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
public interface OrderBillToolService {

	/**
	 * @Description AddBillDTO 交易账单添加
	 * @param dto
	 *            交易账单实体
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月14日 下午12:03:17
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int addBillDTO(OrderBillDTO dto) throws Exception;

	/**
	 * @Description batchAddDTO 交易账单批量添加
	 * @param list
	 *            交易账单实体集合
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月14日 下午12:03:39
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int batchAddDTO(List<OrderBillDTO> list) throws Exception;
	
	/**
	 * 根据条件查找交易账单列表
	 * @param map
	 * @return
	 * @throws Exception
	 * @author xiaojun
	 */
	public List<OrderBillDTO> getOrderBillByCondition(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询出交易列表总和
	 * @param map
	 * @return
	 * @throws Exception
	 * @author xiaojun
	 */
	public Long getOrderBillTotal(Map<String, Object> map) throws Exception;
	
	/**
	 * 根据查询条件获取交易总额
	 * @param map
	 * @return
	 * @throws Exception
	 * @author xiaojun
	 */
	public Double getTradeMoneySum(Map<String, Object> map) throws Exception;

	/**
	 * 根据条件查询
	 * POS刷卡银行流水列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public PageQueryResultDTO<PosBankCardPayRecordDTO> getPageQueryResultByCondition(
			Map<String, Object> map) throws Exception;

}