package com.gudeng.commerce.gd.customer.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.DictDTO;
import com.gudeng.commerce.gd.customer.entity.DictEntity;
import com.gudeng.commerce.gd.customer.service.DemoService;
import com.gudeng.framework.dba.transaction.annotation.Transactional;


/**
 *功能描述：
 */
@Service
public class DemoServiceImpl implements DemoService{
	@Autowired
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public DictDTO getById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (DictDTO)this.baseDao.queryForObject("Demo.getDic", p, DictDTO.class);
	}

	@Override
	public List<DictDTO> getByCondition(Map<String, Object> map)
			throws Exception {
		List<DictDTO> list= baseDao.queryForList("Demo.getByCondition", map, DictDTO.class);
		return list;
	}
	
	@Override
	public List<DictDTO> getByBirthday(Map<String, Object> map)
			throws Exception {
		List<DictDTO> list= baseDao.queryForList("Demo.getByBirthday", map, DictDTO.class);
		return list;
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("Demo.getTotal", map, Integer.class);
	}

	@Override
	public int deleteById(String id) throws Exception {
		Map <String, String> p = new HashMap<String, String>();
		p.put("id", id);
		return (int) baseDao.execute("Demo.deleteDictDTO", p);
		
	}

	@Override
	public int addDictDTO(Map<String, Object> map) throws Exception {
		return (int) baseDao.execute("Demo.addDictDTO", map);
	}
	
	@Override
	public int addDictDTO(DictDTO dic) throws Exception {
//		Map map=new HashMap();
//		for(Field f : DictEntity.class.getDeclaredFields()){
//			f.setAccessible(true);
//			map.put(f.getName(), f.get(dic));
//		}
//		return (int) baseDao.execute("Demo.addDictDTO", map);
		return (int) baseDao.execute("Demo.addDictDTO", dic);

	}

	@Override
	@Transactional
	public int updateDictDTO(DictDTO dic) throws Exception {
//		Map map=new HashMap();
//		for(Field f : DictEntity.class.getDeclaredFields()){
//			f.setAccessible(true);
//			map.put(f.getName(), f.get(dic));
//		}
//		return (int) baseDao.execute("Demo.updateDictDTO", map);
		return (int) baseDao.execute("Demo.updateDictDTO", dic);

	}

	@Override
	public List<DictDTO> getByName(Map<String, Object> map) {
		List<DictDTO> list= baseDao.queryForList("Demo.getDicDTO", map, DictDTO.class);
		return list;
	}
	
	
	
}
