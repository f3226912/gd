package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PaySerialnumberDTO;
import com.gudeng.commerce.gd.order.entity.PaySerialnumberEntity;
import com.gudeng.commerce.gd.order.service.PaySerialnumberService;
@Service
public class PaySerialnumberServiceImpl implements PaySerialnumberService {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private BaseDao baseDao;
	
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long insertEntity(PaySerialnumberEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("payId", id);
		return (int) baseDao.execute("PaySerialnumber.deleteById", map);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		int count=0;
		for(Long id:idList){
			count = deleteById(id);
			if(count<=0) {
				throw new Exception("批量删除记录失败！id:" + id);
			}
		}
		return count;
	}

	@Override
	public int updateDTO(PaySerialnumberDTO obj) throws Exception {
		int count = baseDao.execute("PaySerialnumber.updateDTO", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<PaySerialnumberDTO> objList) throws Exception {
		int count=0;
		for(PaySerialnumberDTO dto:objList){
			count = baseDao.execute("PaySerialnumber.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PaySerialnumber.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PaySerialnumberDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("payId", id);
		return (PaySerialnumberDTO)this.baseDao.queryForObject("PaySerialnumber.getById", map, PaySerialnumberDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaySerialnumberDTO> getListByConditionPage(Map<String, Object> map)
			throws Exception {
		List<PaySerialnumberDTO> list= baseDao.queryForList("PaySerialnumber.getListByConditionPage", map, PaySerialnumberDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaySerialnumberDTO> getListByCondition(Map<String, Object> map)
			throws Exception {
		List<PaySerialnumberDTO> list= baseDao.queryForList("PaySerialnumber.getListByCondition", map, PaySerialnumberDTO.class);
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PaySerialnumberDTO getByOrderNo(Long orderNo) throws Exception {
		Map map = new HashMap();
		map.put("orderNo", orderNo);
		return (PaySerialnumberDTO)this.baseDao.queryForObject("PaySerialnumber.getByOrderNo", map, PaySerialnumberDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaySerialnumberDTO getByOrderNo(Map<String, Object> map)
			throws Exception {
		return (PaySerialnumberDTO)this.baseDao.queryForObject("PaySerialnumber.getByOrderNo", map, PaySerialnumberDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaySerialnumberDTO> getByMemberIdAndDay(Map map)
			throws Exception {
		return this.baseDao.queryForList("PaySerialnumber.getByMemberIdAndDay", map ,PaySerialnumberDTO.class);
	}

	@Override
	public PaySerialnumberDTO getByOrderNoAndPayType(Long orderNo)
			throws Exception {
		Map map = new HashMap();
		map.put("orderNo", orderNo);
		return (PaySerialnumberDTO)this.baseDao.queryForObject("PaySerialnumber.getByOrderNoAndPayType", map, PaySerialnumberDTO.class);
	}
	
	@Override
	public int getTotalByStatementId(String statementId) throws Exception {
		Map map = new HashMap();
		map.put("statementId", statementId);
		return (int) baseDao.queryForObject("PaySerialnumber.getTotalByStatementId", map, Integer.class);
	}
	
	@Override
	public int insertPayStatementId(String statementId) throws Exception {
		Map map = new HashMap();
		map.put("statementId", statementId);
		return (int) baseDao.execute("PayStatementid.insert", map);
	}
	
	@Override
	public int updateTradeAmount(Map<String, Object> paramMap) throws Exception {
		int count = baseDao.execute("PaySerialnumber.updateTradeAmount", paramMap);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

}
