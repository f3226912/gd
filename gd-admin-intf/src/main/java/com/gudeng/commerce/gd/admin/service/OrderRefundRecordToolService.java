package com.gudeng.commerce.gd.admin.service;

import com.gudeng.commerce.gd.order.dto.OrderRefundRecordDTO;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface OrderRefundRecordToolService extends BaseToolService<OrderRefundRecordDTO> {
	public Long insert(OrderRefundRecordEntity entity) throws Exception;
}