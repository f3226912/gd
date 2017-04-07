package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;
import com.gudeng.commerce.gd.order.entity.PreSaleEntity;
import com.gudeng.commerce.gd.order.service.OrderBaseinfoService;
import com.gudeng.commerce.gd.order.service.PreSaleDetailService;
import com.gudeng.commerce.gd.order.service.PreSaleService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;
@Service
public class PreSaleServiceImpl implements PreSaleService {
	
	@Autowired
	private BaseDao<?> baseDao;
	
	@Autowired
	private PreSaleDetailService preSaleDetailService;
	
	@Autowired
	private OrderBaseinfoService orderBaseinfoService;
	
	@SuppressWarnings("rawtypes")
	public BaseDao getBaseDao() {
		return baseDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long insertEntity(PreSaleEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PreSale.deleteById", map);
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
	public int updateDTO(PreSaleDTO obj) throws Exception {
		int count = baseDao.execute("PreSale.updateDTO", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<PreSaleDTO> objList) throws Exception {
		int count=0;
		for(PreSaleDTO dto:objList){
			count = baseDao.execute("PreSale.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PreSale.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PreSaleDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (PreSaleDTO)this.baseDao.queryForObject("PreSale.getById", map, PreSaleDTO.class);
	}

	@Override
	public List<PreSaleDTO> getListByConditionPage(Map<String, Object> map)
			throws Exception {
		List<PreSaleDTO> list= baseDao.queryForList("PreSale.getListByConditionPage", map, PreSaleDTO.class);
		return list;
	}

	@Override
	public List<PreSaleDTO> getListByCondition(Map<String, Object> map)
			throws Exception {
		List<PreSaleDTO> list= baseDao.queryForList("PreSale.getListByCondition", map, PreSaleDTO.class);
		return list;
	}

	@Override
	public Integer updateStatusByOrderNo(PreSaleDTO dto) throws Exception {
		int count = baseDao.execute("PreSale.updateStatusByOrderNo", dto);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean addPreSale(Map<String, Object> map) throws Exception {
		//插入订单基本信息
		PreSaleEntity orderEntity = (PreSaleEntity) map.get("orderBase");
		insertEntity(orderEntity);
		
		//插入订单产品信息
		List<PreSaleDetailEntity> entityList = (List<PreSaleDetailEntity>) map.get("orderProductList");
		preSaleDetailService.batchInsertEntity(entityList);
		return true;
	}

	@Override
	public PreSaleDTO getByOrderNo(Long orderNo, String qcCode)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("qcCode", qcCode);
		return (PreSaleDTO) baseDao.queryForObject("PreSale.getByOrderNo", map, PreSaleDTO.class);
	}

	@Override
	@Transactional
	public boolean confirm(Map<String, Object> totalMap) throws Exception {
		orderBaseinfoService.addOrder(totalMap);
		return true;
	}

	@Override
	public List<PreSaleDTO> getOverPreSale(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("PreSale.getOverPreSale", map, PreSaleDTO.class);
	}

}
