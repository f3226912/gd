package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PreSaleDetailToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PreSaleDetailDTO;
import com.gudeng.commerce.gd.order.entity.PreSaleDetailEntity;
import com.gudeng.commerce.gd.order.service.PreSaleDetailService;

@Service
public class PreSaleDetailToolServiceImpl implements PreSaleDetailToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PreSaleDetailService preSaleDetailService;
	
	private PreSaleDetailService gethessianPreSaleDetailService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPreSaleDetailUrl();
		if (preSaleDetailService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preSaleDetailService = (PreSaleDetailService) factory.create(PreSaleDetailService.class, hessianUrl);
		}
		return preSaleDetailService;
	}

	@Override
	public Long insertEntity(PreSaleDetailEntity obj) throws Exception {
		return gethessianPreSaleDetailService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianPreSaleDetailService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianPreSaleDetailService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(PreSaleDetailDTO obj) throws Exception {
		return gethessianPreSaleDetailService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<PreSaleDetailDTO> objList) throws Exception {
		return gethessianPreSaleDetailService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianPreSaleDetailService().getTotal(map);
	}

	@Override
	public PreSaleDetailDTO getById(Long id) throws Exception {
		return gethessianPreSaleDetailService().getById(id);
	}

	@Override
	public List<PreSaleDetailDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianPreSaleDetailService().getListByConditionPage(map);
	}

	@Override
	public List<PreSaleDetailDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianPreSaleDetailService().getListByCondition(map);
	}

	
}
