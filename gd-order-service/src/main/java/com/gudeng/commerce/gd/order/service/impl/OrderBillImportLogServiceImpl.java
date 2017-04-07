package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBillImportLlogDTO;
import com.gudeng.commerce.gd.order.service.OrderBillImportLogService;

/**
 * @Description 交易账单导入日志相关服务
 * @Project gd-order-intf
 * @ClassName OrderBillService.java
 * @Author lidong(dli@gdeng.cn)
 * @CreationDate 2015年12月14日 上午11:59:22
 * @Version V2.0
 * @Copyright 谷登科技 2015-2015
 * @ModificationHistory
 */
public class OrderBillImportLogServiceImpl implements OrderBillImportLogService {
	@Autowired
	private BaseDao<OrderBillImportLlogDTO> baseDao;

	/**
	 * @Description 添加导入日志记录
	 * @param dto
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月24日 下午4:01:18
	 * @Author lidong(dli@gdeng.cn)
	 */
	public int addBillLogDTO(OrderBillImportLlogDTO dto) throws Exception {
		return (int) baseDao.execute("OrderBillImportLog.addBillLogDTO", dto);
	}

	/**
	 * @Description 根据文件名获取导入日志记录
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年12月24日 下午4:01:32
	 * @Author lidong(dli@gdeng.cn)
	 */
	public OrderBillImportLlogDTO getOrderBillLogByFileName(String fileName) throws Exception {
		if (StringUtils.isEmpty(fileName)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("fileName", fileName.trim());
		return (OrderBillImportLlogDTO) this.baseDao.queryForObject("OrderBillImportLog.getOrderBillLogByFileName", map, OrderBillImportLlogDTO.class);
	}
}
