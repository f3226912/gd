package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.dto.ReOrderCustomerDTO;
import com.gudeng.commerce.gd.order.entity.OrderFeeItemDetailEntity;
import com.gudeng.commerce.gd.order.entity.OrderProductDetailEntity;
import com.gudeng.commerce.gd.order.entity.ReOrderCustomerEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.OrderFeeItemDetailService;
import com.gudeng.commerce.gd.order.service.OrderProductDetailService;
import com.gudeng.commerce.gd.order.service.ReOrderCustomerService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;

@Service
public class ReOrderCustomerServiceImpl implements ReOrderCustomerService {

	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private OrderProductDetailService orderProductDetailService;
	@Autowired
	private OrderBaseinfoService orderBaseinfoService;
	@Autowired
	private OrderFeeItemDetailService orderFeeItemDetailService;
	
	@Override
	@Transactional
	public Integer addEntity(Map<String, Object> map) throws Exception {
		ReOrderCustomerEntity entity = (ReOrderCustomerEntity) map.get("reOrderCustomerEntity");
		int num = baseDao.execute("ReOrderCustomer.addEntity", entity);

		OrderBaseinfoDTO orderBaseDTO = (OrderBaseinfoDTO) map.get("orderBaseEntity");
		if(orderBaseDTO != null){
			int count = orderBaseinfoService.supplementOrderMessage(orderBaseDTO);
			if(count > 0){
				@SuppressWarnings("unchecked")
				List<OrderProductDetailEntity> entityList =  (List<OrderProductDetailEntity>) map.get("productEntityList");
				if(entityList != null && entityList.size() > 0){
					orderProductDetailService.batchInsertEntity(entityList);
				}
			}else{
				orderBaseinfoService.updateByOrderNo(orderBaseDTO);
			}
			
			OrderFeeItemDetailEntity feeEntity = (OrderFeeItemDetailEntity) map.get("actFeeEntity");
			if(feeEntity != null){
				orderFeeItemDetailService.insert(feeEntity);
			}
		}
		return num;
	}

	@Override
	public ReOrderCustomerDTO getByOrderNo(Long orderNo) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		return baseDao.queryForObject("ReOrderCustomer.getByOrderNo", paramMap, ReOrderCustomerDTO.class);
	}
}
