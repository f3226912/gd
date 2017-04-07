package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.output.AreaListAppDTO;
import com.gudeng.commerce.gd.api.service.AreaToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.dto.BusinessAreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;


@Service
public class AreaToolServiceImpl implements AreaToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static AreaService areaService;

	protected AreaService getHessianAreaService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.area.url");
		if(areaService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}
		return areaService;
	}
	
	@Override
	public AreaDTO getByAreaName(String city) throws Exception {
		return getHessianAreaService().getAreaByName(city);
	}

	@Override
	public List<AreaDTO> getAllProvince() throws Exception {
		return getHessianAreaService().getAreaParentTree();
	}

	@Override
	public List<AreaDTO> getChildrenByParentId(String parentId) throws Exception {
		return getHessianAreaService().getAreaChildTree(parentId);
	}

	@Override
	public void setLngLat(Map<String, String> map) throws Exception {
		 getHessianAreaService().setLngLat(map);
	}

	@Override
	public AreaDTO getByAreaId(Long city) throws Exception {
	return	getHessianAreaService().getArea(city+"");
	}

	@Override
	public AreaDTO getLngAndLatByCityId(String cityId) throws Exception {
		return getHessianAreaService().getLngAndLatByCityId(cityId);
	}

	@Override
	public List<AreaListAppDTO> getAllProvinceCity() throws Exception {
		List<AreaListAppDTO> appList = new ArrayList<>();
		List<AreaDTO> provinceList = getAllProvince();
		List<AreaDTO> cityList = getHessianAreaService().getAllProvinceCity();
		for(int i=0, len=provinceList.size(); i<len; i++){
			AreaDTO provinceArea = provinceList.get(i);
			AreaListAppDTO appDTO = new AreaListAppDTO();
			String parentId = provinceArea.getAreaID();
			appDTO.setParentId(parentId);
			appDTO.setParentName(provinceArea.getArea());
			List<Map<String, String>> cityAppList = new ArrayList<>();
			for(int j=0, len2=cityList.size(); j<len2;j++){
				AreaDTO cityArea = cityList.get(j);
				if(cityArea.getFather().equals(parentId)){
					Map<String, String> cityMap = new HashMap<>();
					cityMap.put("childId", cityArea.getAreaID());
					cityMap.put("childName", cityArea.getArea());
					cityAppList.add(cityMap);
				}
			}
			if(cityAppList.size() > 0){
				appDTO.setChildAreas(cityAppList);
				appList.add(appDTO);
			}
		}
		return appList;
	}
	
	/**
	 * 根据地域类型获取所有省份信息
	 * @return
	 * @throws Exception
	 */
	public List<AreaDTO> getAllProvinceByType(String type) throws Exception{
		return getHessianAreaService().getAllProvinceByType(type);
	}

	@Override
	public AreaDTO getAreaDetail(Long provinceId, Long cityId, Long countyId)
			throws Exception {
		return getHessianAreaService().getAreaDetail(provinceId, cityId, countyId);
	}

	@Override
	public List<BusinessAreaDTO> getByBusinessIdList(List<String> businessIdList)
			throws Exception {
		return getHessianAreaService().getByBusinessIdList(businessIdList);
	}
}
