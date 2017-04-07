package com.gudeng.commerce.gd.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.order.dao.BaseDao;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.order.entity.InStoreDetailEntity;
import com.gudeng.commerce.gd.order.service.InStoreDetailService;

@Service
public class InStoreDetailServiceImpl implements InStoreDetailService {
	
	@Autowired
    private BaseDao  baseDao ;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public InStoreDetailDTO getById(Long id) throws Exception {
		Map p = new HashMap<String, String>();
		p.put("isdId", id);
		return (InStoreDetailDTO)this.baseDao.queryForObject("InStoreDetail.queryById", p, InStoreDetailDTO.class);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("isdId", id);
		return (int) baseDao.execute("InStoreDetail.deleteById", map);
	}

	@Override
	public int updateByDto(InStoreDetailDTO inStoreDetailDTO) throws Exception {
		return (int) baseDao.execute("InStoreDetail.update", inStoreDetailDTO);
	}

	@Override
	public Long addByEntity(InStoreDetailEntity inStoreDetailEntity)
			throws Exception {
		return (Long)baseDao.persist(inStoreDetailEntity,Long.class);
	}

	@Override
	public List<InStoreDetailDTO> getBySearch(Map map) throws Exception {
		return baseDao.queryForList("InStoreDetail.queryByCondition",   map ,InStoreDetailDTO.class);
	}

	@Override
	public List<InStoreDetailDTO> getPageBySearch(Map map) throws Exception {
		return baseDao.queryForList("InStoreDetail.queryByConditionPage",   map ,InStoreDetailDTO.class);
	}
	@Override
	public List<InStoreDetailDTO> getByBusinessId(Map map) throws Exception {
		return baseDao.queryForList("InStoreDetail.getByBusinessId",   map ,InStoreDetailDTO.class);
	}
	@Override
	public int getCountByBusinessId(Map map) throws Exception {
		return (int)baseDao.queryForObject("InStoreDetail.getCountByBusinessId",   map ,Integer.class);
	}

	@Override
	public List<InStoreDetailDTO> getInstoreProductList(
			Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return baseDao.queryForList("InStoreDetail.getInstoreProductList", map, InStoreDetailDTO.class);
	}

	@Override
	public int getInstoreProductListTotal(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return (int) baseDao.queryForObject("InStoreDetail.getInstoreProductListTotal", map, Integer.class);
	}

 

}
