package com.gudeng.commerce.gd.customer.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.PushRecordDTO;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.PushRecordService;

public class PushRecordServiceImpl implements PushRecordService{
	@Autowired
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Long add(PushRecordEntity pushRecordEntity){    
//		baseDao.persist(pushRecordDTO);
		return (Long)baseDao.persist(pushRecordEntity, Long.class);
		

		
	}

	@SuppressWarnings("unchecked")
	@Override
	public int delete(Long id) {
		Map<String, Long> paraMap=new HashMap<>();
		paraMap.put("id", id);
		return (int) baseDao.execute("Pushrecord.deleteById", paraMap);

	}
	
	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Integer batchDel(String ids) {
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		List para=new ArrayList<>();
//		para.add(1);
//		para.add(2);	
//		String ss="1,2";
//		
//		String[] productIdsArray = ss.split(",");
//		paramMap.put("productIds", productIdsArray);
//		return (Integer)baseDao.execute("Pushrecord.deleteById", paramMap,Integer.class);
//	}
//	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<PushRecordDTO> getList() throws MalformedURLException {
		return baseDao.queryForList("Pushrecord.getList",PushRecordDTO.class);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PushRecordDTO> getList(Long memberId) throws MalformedURLException {
		Map map=new HashMap<>();
		map.put("memberId", memberId);
		return baseDao.queryForList("Pushrecord.getList",map,PushRecordDTO.class);
	}
	
	@Override
	public List<PushRecordDTO> getList(Map mapParam) throws MalformedURLException {
		return baseDao.queryForList("Pushrecord.getList",mapParam,PushRecordDTO.class);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PushRecordDTO> getList(Long memberId,Integer state) throws MalformedURLException {
		Map map=new HashMap<>();
		map.put("memberId", memberId);
		map.put("state", state);
		return baseDao.queryForList("Pushrecord.getList",map,PushRecordDTO.class);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PushRecordDTO> getList(Integer state) throws MalformedURLException {
		Map map=new HashMap<>();
		map.put("state", state);
		return baseDao.queryForList("Pushrecord.getList",map,PushRecordDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getCount(Integer state) {
		Map map=new HashMap<>();
		map.put("state", state);
		return (Integer) baseDao.queryForObject("Pushrecord.getCount", map, Integer.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getCount(Integer state,Long memberId) {
		Map map=new HashMap<>();
		map.put("state", state);
		map.put("memberId", memberId);
		return (Integer) baseDao.queryForObject("Pushrecord.getCount", map, Integer.class);
	}

	
	
	
	@Override
	public int getTotal(Map map) throws Exception {
		return (int) baseDao.queryForObject("Pushrecord.getTotal", map, Integer.class);
	}
	
	@Override
	public List<PushRecordDTO> getBySearch(Map map) throws Exception {
		return  baseDao.queryForList("Pushrecord.getBySearch", map, PushRecordDTO.class);
	}

	@Override
	public int  update(PushRecordDTO pushRecordDTO) {
		return (int) baseDao.execute("Pushrecord.updateById", pushRecordDTO);
	}
}
