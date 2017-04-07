package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessPosDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessPosEntity;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;


/**
 *功能描述：MemberBaseinfo增删改查实现类
 *
 */
@Service
public class ReBusinessPosServiceImpl implements ReBusinessPosService{
	
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;

	public BaseDao<?> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long addReBusinessPosEntity(ReBusinessPosEntity reBusinessPosEntity) throws Exception {
		return (Long)baseDao.persist(reBusinessPosEntity, Long.class);
	}

	@Override
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("posNumber", posDevNo);
		return baseDao.queryForObject("reBusinessPos.getByPosDevNo", paramMap, BusinessBaseinfoDTO.class);
	}
	
	@Override
	public BusinessBaseinfoDTO getByPosDevNo(String posDevNo, String businessNo) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("posNumber", posDevNo);
//		paramMap.put("businessNo", businessNo);
		return baseDao.queryForObject("reBusinessPos.getByPosDevNo", paramMap, BusinessBaseinfoDTO.class);
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		return (int)baseDao.execute("reBusinessPos.deleteByBusinessId", paramMap);
	}
	
	@Override
	public int updateByOriNewPosNum(Map<String,String> paramMap) throws Exception {
		return (int)baseDao.execute("reBusinessPos.updateByOriNewPosNum", paramMap);
	}

 
	@Override
	public int deleteById(Long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		return (int)baseDao.execute("reBusinessPos.deleteById", paramMap);
	}

	@Override
	public List<ReBusinessPosDTO> getReBusinessPosByBusinessId(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		return (List<ReBusinessPosDTO>)baseDao.queryForList("reBusinessPos.queryByBusinessId", paramMap,ReBusinessPosDTO.class);
	}
	
	@Override
	public List<ReBusinessPosDTO> getNewReBusinessPosByBusinessId(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		return (List<ReBusinessPosDTO>)baseDao.queryForList("reBusinessPos.queryNewByBusinessId", paramMap,ReBusinessPosDTO.class);
	}

	@Override
	public ReBusinessPosDTO getByBusinessIdAndPosNumber(Long businessId, String posNumber) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		paramMap.put("posNumber", posNumber);
		return (ReBusinessPosDTO)baseDao.queryForList("reBusinessPos.queryByBusinessIdAndPosNumber", paramMap,ReBusinessPosDTO.class);
	}


	@Override
	public String getPosInfoByBusinessId(Long businessId) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("businessId", businessId);
		paramMap.put("isGetLatest", 1);
		List<ReBusinessPosDTO> list = getNewReBusinessPosByBusinessId(businessId);
		if(list != null && list.size() > 0){
			StringBuilder sb = new StringBuilder();
			for(ReBusinessPosDTO psoInfo : list){
				sb.append(psoInfo.getId()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
		return null;
	}
}
