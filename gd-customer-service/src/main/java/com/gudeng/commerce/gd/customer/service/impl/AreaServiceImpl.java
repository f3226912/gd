package com.gudeng.commerce.gd.customer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gudeng.commerce.gd.customer.bo.CacheBo;
import com.gudeng.commerce.gd.customer.dao.BaseDao;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessAreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private BaseDao<?> baseDao;
	@Autowired
	private CacheBo cacheBo;
	

	public void setBaseDao(BaseDao<?> baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public List<AreaDTO> listArea() {
		Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("Area.listArea", params ,AreaDTO.class);
	}

	@Override
	public List<AreaDTO> listTopArea() {
		/*Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("Area.listTopArea", params ,AreaDTO.class);*/
		return cacheBo.listTopArea(baseDao);
	}

	@Override
	public List<AreaDTO> listChildArea(String id) {
		
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("father", id);
		return this.baseDao.queryForList("Area.listChildArea", params ,AreaDTO.class);*/
		return cacheBo.listChildArea(id, baseDao);
	}
	
	@Override
	public AreaDTO getArea(String areaID) {
		/*Map <String, String> p = new HashMap<String, String>();
		p.put("areaID", areaID);
		return (AreaDTO)this.baseDao.queryForObject("Area.getArea", p, AreaDTO.class);*/
		return cacheBo.getAreaById(areaID, baseDao);
	}

	
	@Override
	public List<AreaDTO> getAreaParentTree() {
		Map<String,Object> params = new HashMap<String,Object>();
		return this.baseDao.queryForList("Area.getParentTree", params ,AreaDTO.class);
	}
	
	/**
	 * @Description getCityTree 查询2级地区 
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:17
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getCityTree() {
		return this.baseDao.queryForList("Area.getCityTree", new HashMap<>() ,AreaDTO.class);
	}
	/**
	 * @Description getAreaTree 查询3级及以上地区
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:32
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getAreaTree() {
		return this.baseDao.queryForList("Area.getAreaTree", new HashMap<>() ,AreaDTO.class);
	}
	
	@Override
	public List<AreaDTO> getAreaChildTree(String father) {
		/*Map<String,Object> params = new HashMap<String,Object>();
		params.put("father", father);
		return this.baseDao.queryForList("Area.getChildTree", params ,AreaDTO.class);*/
		return cacheBo.getAreaChildTree(father, baseDao);
	}

	@Override
	public AreaDTO getAreaByName(String area) {
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("area", area);
//		return (AreaDTO) this.baseDao.queryForObject("Area.getAreaByName", params ,AreaDTO.class);
		return cacheBo.getAreaByName(area, baseDao);
	}

	@Override
	public void setLngLat(Map<String, String> map) {
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("lng", map.get("lng"));
		map2.put("lat", map.get("lat"));
		map2.put("areaID", map.get("areaID"));
		cacheBo.deleteAreaCacheById(map.get("areaID"));
	    baseDao.execute("Area.setLngLat", map2);
	}
	
	@Override
	public List<AreaDTO> geAreaByAreaId(String areaId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaId", areaId);
		return baseDao.queryForList("Area.geAreaByAreaId", params ,AreaDTO.class);
	}
	
	@Override
	public int addArea(AreaDTO areaDTO){    
		return (int) baseDao.execute("Area.addArea", areaDTO);
	}
	
	@Override
	public int updateArea(AreaDTO areaDTO) throws Exception {
		cacheBo.deleteAreaCacheById(areaDTO.getAreaID());
		return (int) baseDao.execute("Area.updateArea", areaDTO);
	}
	
	@Override
	public String deleteArea(String areaID) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("areaID", areaID);
		this.baseDao.execute("Area.deleteArea", params);
		return null;
	}

	@Override
	public AreaDTO getLngAndLatByCityId(String cityId) throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("cityId", cityId);
		return baseDao.queryForObject("Area.getLngAndLatByCityId", paramMap, AreaDTO.class);
	}

	@Override
	public List<AreaDTO> getAllProvinceCity() throws Exception {
		return cacheBo.getAllProvinceCity(baseDao);
	}
	
	@Override
	public List<AreaDTO> getAllProvinceByType(String type) throws Exception{
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("areaType", type);
		return this.baseDao.queryForList("Area.getParentTreeByType", paramMap ,AreaDTO.class);
	}

	@Override
	public AreaDTO getAreaDetail(Long provinceId, Long cityId, Long countyId)
			throws Exception {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("provinceId", provinceId);
		paramMap.put("cityId", cityId);
		paramMap.put("countyId", countyId);
		return baseDao.queryForObject("Area.getAreaDetail", paramMap, AreaDTO.class);
	}

	@Override
	public List<BusinessAreaDTO> getByBusinessIdList(List<String> businessIdList)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("businessIdList", businessIdList);
		return baseDao.queryForList("Area.getByBusinessIdList", map, BusinessAreaDTO.class);
	}
}
