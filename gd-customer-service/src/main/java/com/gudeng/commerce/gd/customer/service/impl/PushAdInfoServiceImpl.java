package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.entity.PushAdInfoEntity;
import com.gudeng.commerce.gd.customer.service.PushAdInfoService;

@Service
public class PushAdInfoServiceImpl implements PushAdInfoService {

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
	public Long insertEntity(PushAdInfoEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PushAdInfo.deleteById", map);
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
	public int updateDTO(PushAdInfoDTO obj) throws Exception {
		int count = baseDao.execute("PushAdInfo.updateDTO", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@Override
	public int batchUpdateDTO(List<PushAdInfoDTO> objList) throws Exception {
		int count=0;
		for(PushAdInfoDTO dto:objList){
			count = baseDao.execute("PushAdInfo.deleteById", dto);
			if(count<=0) {
				throw new Exception("批量更新记录失败！dto:" + dto);
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PushAdInfo.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PushAdInfoDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (PushAdInfoDTO)this.baseDao.queryForObject("PushAdInfo.getById", map, PushAdInfoDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushAdInfoDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<PushAdInfoDTO> list= baseDao.queryForList("PushAdInfo.getListByConditionPage", map, PushAdInfoDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PushAdInfoDTO> getListByShowPage(Map<String, Object> map) throws Exception {
		List<PushAdInfoDTO> list= baseDao.queryForList("PushAdInfo.getListByShow", map, PushAdInfoDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushAdInfoDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<PushAdInfoDTO> list= baseDao.queryForList("PushAdInfo.getListByCondition", map, PushAdInfoDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PushProductDTO> getPushProductList(Map<String, Object> map)
			throws Exception {
		List<PushProductDTO> list= baseDao.queryForList("PushAdInfo.getPushProductList", map, PushProductDTO.class);
		 return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getProductTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PushAdInfo.getProductTotal", map, Integer.class);
	}

	@Override
	public List<NstNoticeEntityDTO> getNoticeList() throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		// TODO Auto-generated method stub
		return  baseDao.queryForList("PushAdInfo.getNoticeList", map,NstNoticeEntityDTO.class);
	}
}
