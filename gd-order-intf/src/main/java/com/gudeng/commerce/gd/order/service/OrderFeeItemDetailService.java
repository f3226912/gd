package com.gudeng.commerce.gd.order.service;

import java.util.List;
import java.util.Map;

import com.gudeng.commerce.gd.order.dto.OrderFeeItemDetailDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;

/**
 * 谷登科技代码生成器出品,模板不代表正确，请酌情修改
 * 
 * @author lidong
 *
 */
public interface OrderFeeItemDetailService extends BaseService<OrderFeeItemDetailDTO> {
	public Long insert(OrderFeeItemDetailEntity entity) throws Exception;

	public int batchInsertEntity(List<OrderFeeItemDetailDTO> orderActFeeList) throws Exception;

	public int batchUpdate(List<OrderFeeItemDetailDTO> orderActFeeList,
			Long orderNo) throws Exception;

	public int deleteByOrderNo(String orderNo) throws Exception;

	public abstract int deleteByParam(Map<String, Object> param) throws Exception;

	public int batchInsert(List<OrderFeeItemDetailEntity> feelList)throws Exception;

}