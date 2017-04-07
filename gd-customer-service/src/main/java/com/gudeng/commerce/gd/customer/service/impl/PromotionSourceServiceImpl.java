package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.PromotionSourceDTO;
import com.gudeng.commerce.gd.customer.dto.PromotionStatisticsEntityDto;
import com.gudeng.commerce.gd.customer.dto.PromotionTypeDto;
import com.gudeng.commerce.gd.customer.dto.PromotionUrlDTO;
import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionStatisticsEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionTypeEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionUrlEntity;
import com.gudeng.commerce.gd.customer.service.PromotionSourceService;


/**
 *功能描述：MemberBaseinfo增删改查实现类
 *
 */
@Service
public class PromotionSourceServiceImpl implements PromotionSourceService{
	
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private CacheBo cacheBo;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Long addPromotionSourceEnt(
			PromotionSourceEntity promotionSourceEntity) throws Exception {
		return (Long)baseDao.persist(promotionSourceEntity, Long.class);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PromotionSource.deleteById", map);
	}

	@Override
	public int updatePromotionSourceDTO(PromotionSourceDTO promotionSourceDTO)
			throws Exception {
		return (int) baseDao.execute("PromotionSource.updateById", promotionSourceDTO);

	}

	@Override
	public PromotionSourceDTO getById(Long id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (PromotionSourceDTO) baseDao.queryForObject("PromotionSource.getById", map, PromotionSourceDTO.class);
	}

	@Override
	public List<PromotionSourceDTO> getByName(String name) throws Exception {
		Map map=new HashMap();
		map.put("name", name);
		return (List<PromotionSourceDTO>) baseDao.queryForList("PromotionSource.getByName", map, PromotionSourceDTO.class);
	}
	
	@Override
	public int getTotal(String name) throws Exception {
		Map map=new HashMap();
		map.put("name", name);
		return (int) baseDao.queryForObject("PromotionSource.getTotal", map, Integer.class);
	}

	@Override
	public int getTypeTotal(String name) throws Exception{
		Map map = new HashMap();
		map.put("name", name);
		return (int) baseDao.queryForObject("PromotionSource.getTypeTotal", map, Integer.class);
	}
	
	@Override
	public List<PromotionTypeEntity> getTypeInCondition(Map<?,?> map) throws Exception {
		return (List<PromotionTypeEntity>) baseDao.queryForList("PromotionSource.getTypeInCondition", map, PromotionTypeEntity.class);
	}
	@Override
	public PromotionTypeEntity getTypeById(Long id) throws Exception {
		Map map = new HashMap();
		map.put("id", id);
		return (PromotionTypeEntity) baseDao.queryForObject("PromotionSource.getTypeById", map, PromotionTypeEntity.class);
	}
	
	@Override
	public List<PromotionUrlDTO> getPromotionUrlBySourceId(Long sourceId)
			throws Exception {
		Map map=new HashMap();
		map.put("sourceId", sourceId);
		return (List<PromotionUrlDTO>) baseDao.queryForList("PromotionSource.getPromotionUrlBySourceId", map, PromotionSourceDTO.class);
	}

	@Override
	public Long addPromotionUrlEnt(PromotionUrlEntity promotionUrlEntity)
			throws Exception {
		return (Long)baseDao.persist(promotionUrlEntity, Long.class);
	}

	@Override
	public int deletePromotionUrlBySourceId(Long sourceId) throws Exception {
		Map map=new HashMap();
		map.put("sourceId", sourceId);
		return (int) baseDao.execute("PromotionSource.deletePromotionUrlBySourceId", map);	
	}

	@Override
	public Long addPromotionStatistics(PromotionStatisticsEntity entity) throws Exception{
		return (Long) baseDao.persist(entity, Long.class);
	}

	@Override
	public List<PromotionStatisticsEntityDto> statisticsGroupList(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("PromotionSource.statisticsGroupList", map, PromotionStatisticsEntityDto.class);
	}

	@Override
	public List<PromotionStatisticsEntityDto> statisticsListAll(Map<String, Object> map) throws Exception {
		return baseDao.queryForList("PromotionSource.statisticsListAll", map, PromotionStatisticsEntityDto.class);
	}
	
	@Override
	public int getStatisticsTotal(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PromotionSource.statisticsTotal", map, Integer.class);
	}

	@Override
	public int getStatisticsAllCount(Map<String, Object> map) throws Exception {
		return (int) baseDao.queryForObject("PromotionSource.statisticsAllCount", map, Integer.class);
	}

	@Override
	public Long persistTypeInfo(PromotionTypeEntity entity)
			throws Exception {
		return (Long) baseDao.persist(entity, PromotionTypeEntity.class);
	}

	@Override
	public int updateTypeInfo(PromotionTypeDto dto)
			throws Exception {
		return (int) baseDao.execute("PromotionSource.updateTypeById", dto);
	}

	@Override
	public int deleteTypeById(Long id) throws Exception {
		Map map=new HashMap();
		map.put("id", id);
		return (int) baseDao.execute("PromotionSource.deleteTypeById", map);
	}

	@Override
	public int initialPromotionUrl(PromotionTypeEntity entity) {
		Map map=new HashMap();
		map.put("type", entity.getId());
		map.put("prefix", entity.getUrl() +"?sourceId=");
		map.put("suffix", "&type=" + entity.getId());
		return (int) baseDao.execute("PromotionSource.addTypeForPromotionURL", map);
	}
}
