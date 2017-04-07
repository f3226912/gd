package com.gudeng.commerce.info.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.info.customer.dao.BaseDao;
import com.gudeng.commerce.info.customer.dto.DatasourceBaiduDTO;
import com.gudeng.commerce.info.customer.entity.DatasourceBaiduEntity;
import com.gudeng.commerce.info.customer.service.DatasourceBaiduService;

@Service
public class DatasourceBaiduServiceImpl implements DatasourceBaiduService {

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
	public Long insertEntity(DatasourceBaiduEntity obj) throws Exception {
		return (Long) baseDao.persist(obj, Long.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("DatasourceBaidu.deleteById", map);
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
	public int updateDTO(DatasourceBaiduDTO obj) throws Exception {
		int count = baseDao.execute("DatasourceBaidu.update", obj);
		if(count<=0){
			throw new Exception("更新记录失败！");
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int batchUpdateDTO(List<DatasourceBaiduDTO> objList) throws Exception {
		int count=0;
		int len = objList.size();
		Map<String, Object>[] batchValues = new HashMap[len];
		for(int i = 0; i < len; i++){
			DatasourceBaiduDTO dto = objList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PVcount", dto.getPVcount());
			map.put("UVcount", dto.getUVcount());
			map.put("IPcount", dto.getIPcount());
			map.put("lastUpdateTimeStr", dto.getLastUpdateTimeStr());
			map.put("signout", dto.getSignout());
			map.put("avstop", dto.getAvstop());
			map.put("newuser", dto.getNewuser());
			map.put("olduser", dto.getOlduser());
			map.put("state", dto.getState());
			map.put("createUserID", dto.getCreateUserID());
			map.put("client", dto.getClient());
			batchValues[i] = map;
		}
		count = baseDao.batchUpdate("DatasourceBaidu.importDTO", batchValues).length;
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("DatasourceBaidu.getTotal", map, Integer.class);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public DatasourceBaiduDTO getById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (DatasourceBaiduDTO)this.baseDao.queryForObject("DatasourceBaidu.getById", map, DatasourceBaiduDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatasourceBaiduDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		List<DatasourceBaiduDTO> list= baseDao.queryForList("DatasourceBaidu.getListByConditionPage", map, DatasourceBaiduDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatasourceBaiduDTO> getListByCondition(Map<String, Object> map) throws Exception {
		List<DatasourceBaiduDTO> list= baseDao.queryForList("DatasourceBaidu.getListByCondition", map, DatasourceBaiduDTO.class);
		return list;
	}
}
