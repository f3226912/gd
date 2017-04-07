package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PromotionSourceToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.PromotionSourceDTO;
import com.gudeng.commerce.gd.customer.dto.PromotionStatisticsEntityDto;
import com.gudeng.commerce.gd.customer.dto.PromotionTypeDto;
import com.gudeng.commerce.gd.customer.dto.PromotionUrlDTO;
import com.gudeng.commerce.gd.customer.entity.PromotionSourceEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionTypeEntity;
import com.gudeng.commerce.gd.customer.entity.PromotionUrlEntity;
import com.gudeng.commerce.gd.customer.service.PromotionSourceService;

/**
 * 功能描述：
 */
@Service
public class PromotionSourceToolServiceImpl implements PromotionSourceToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static PromotionSourceService promotionSourceService;

	/**
	 * 功能描述: promotionSourceService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected PromotionSourceService getHessianPromotionSourceService() throws MalformedURLException {
		String url = gdProperties.getPromotionSourceUrl();
		if (promotionSourceService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promotionSourceService = (PromotionSourceService) factory.create(PromotionSourceService.class, url);
		}
		return promotionSourceService;
	}

	@Override
	public Long addPromotionSourceEnt(
			PromotionSourceEntity promotionSourceEntity) throws Exception {
		return getHessianPromotionSourceService().addPromotionSourceEnt(promotionSourceEntity);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return getHessianPromotionSourceService().deleteById(id);
	}

	@Override
	public int updatePromotionSourceDTO(PromotionSourceDTO promotionSourceDTO)
			throws Exception {
		return getHessianPromotionSourceService().updatePromotionSourceDTO(promotionSourceDTO);
	}

	@Override
	public PromotionSourceDTO getById(Long id) throws Exception {
		return getHessianPromotionSourceService().getById(id);
	}

	@Override
	public List<PromotionSourceDTO> getByName(String name) throws Exception {
		return getHessianPromotionSourceService().getByName(name);
	}

	@Override
	public int getTotal(String name) throws Exception {
		return getHessianPromotionSourceService().getTotal(name);
	}
 
	@Override
	public List<PromotionUrlDTO> getPromotionUrlBySourceId(Long sourceId)
			throws Exception {
		return getHessianPromotionSourceService().getPromotionUrlBySourceId(sourceId);
	}

	@Override
	public Long addPromotionUrlEnt(PromotionUrlEntity promotionUrlEntity)
			throws Exception {
		return getHessianPromotionSourceService().addPromotionUrlEnt(promotionUrlEntity);
	}

	@Override
	public int deletePromotionUrlBySourceId(Long sourceId) throws Exception {
		return getHessianPromotionSourceService().deletePromotionUrlBySourceId(sourceId);
	}

	@Override
	public List<PromotionStatisticsEntityDto> statisticsGroupList(Map<String, Object> map) throws Exception {
		return getHessianPromotionSourceService().statisticsGroupList(map);
	}

	@Override
	public int getStatisticsTotal(Map<String, Object> map) throws Exception {
		return getHessianPromotionSourceService().getStatisticsTotal(map);
	}

	@Override
	public int getStatisticsAllCount(Map<String, Object> map) throws Exception {
		return getHessianPromotionSourceService().getStatisticsAllCount(map);
	}

	@Override
	public List<PromotionStatisticsEntityDto> statisticsListAll(Map<String, Object> map) throws Exception {
		return getHessianPromotionSourceService().statisticsListAll(map);
	}

	@Override
	public int getTypeTotal(String name) throws Exception {
		return getHessianPromotionSourceService().getTypeTotal(name);
	}

	@Override
	public List<PromotionTypeEntity> getTypeInCondition(Map<?,?> map) throws Exception {
		return getHessianPromotionSourceService().getTypeInCondition(map);
	}

	@Override
	public PromotionTypeEntity getTypeById(Long id) throws Exception {
		return getHessianPromotionSourceService().getTypeById(id);
	}

	@Override
	public Long saveTypeInfo(PromotionTypeEntity entity) throws Exception {
		return getHessianPromotionSourceService().persistTypeInfo(entity);
	}

	@Override
	public int saveTypeModification(PromotionTypeDto dto)
			throws Exception {
		return getHessianPromotionSourceService().updateTypeInfo(dto);
	}

	@Override
	public int deleteTypeById(Long id) throws Exception {
		return getHessianPromotionSourceService().deleteTypeById(id);
	}

	@Override
	public int initialPromotionUrl(PromotionTypeEntity entity)
			throws Exception {
		return getHessianPromotionSourceService().initialPromotionUrl(entity);
	}
}
