package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.OrderBaseinfoDTO;
import com.gudeng.commerce.gd.order.entity.OrderBaseinfoEntity;
import com.gudeng.commerce.gd.order.entity.OrderRefundRecordEntity;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PayResultService;
import com.gudeng.framework.dba.util.DalUtils;

/**
 * 支付结果数据操作
 * @author Ailen
 *
 */
public class PayResultServiceImpl implements PayResultService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Override
	public void updateOrderStatus(String orderNo, String status) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("orderStatus", status);
		
		baseDao.execute("PayResult.updateOrderStatus", paramMap);
	}
	
	@Override
	public void updateOrderStatus(String orderNo, String status, String cancelReason) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderNo", orderNo);
		paramMap.put("orderStatus", status);
		paramMap.put("cancelReason", cancelReason);
		
		baseDao.execute("PayResult.updateOrderStatus", paramMap);
	}

	@Override
	public void addOrderRefundRecord(OrderRefundRecordEntity orderRefundRecordEntity) {
		Map<String, Object> paramMap = DalUtils.convertToMap(orderRefundRecordEntity);
		
		baseDao.execute("PayResult.addOrderRefundRecord", paramMap);
	}

	@Override
	public void addPaySerialnumber(PaySerialnumberEntity paySerialnumberEntity) throws Exception {
		baseDao.persist(paySerialnumberEntity, Long.class);
	}

	@Override
	public List<OrderBaseinfoDTO> getDinJinPayOuter() throws Exception {
		return baseDao.queryForList("PayResult.getDinJinPayOuter", null, OrderBaseinfoDTO.class);
	}

	@Override
	public void updateMemberGrade(Long memberId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("memberId", memberId);
		
		baseDao.execute("PayResult.updateMemberGrade", paramMap);
	}

	@Override
	public PaySerialnumberEntity getByStatementId(String statementId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("statementId", statementId);
		return baseDao.queryForObject("PayResult.getByStatementId", paramMap, PaySerialnumberEntity.class);

	}

	@Override
	public void updateOrderBase(OrderBaseinfoEntity entity) {
		// TODO Auto-generated method stub
		baseDao.execute("PayResult.updateOrderBase", entity);
	}

}
