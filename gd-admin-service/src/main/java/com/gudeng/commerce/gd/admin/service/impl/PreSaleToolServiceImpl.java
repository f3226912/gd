package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.PreSaleToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PreSaleDTO;
import com.gudeng.commerce.gd.order.entity.PreSaleEntity;
import com.gudeng.commerce.gd.order.service.PreSaleService;

@Service
public class PreSaleToolServiceImpl implements PreSaleToolService {
	
	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;
	
	private static PreSaleService preSaleService;
	
	private PreSaleService gethessianPreSaleService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getPreSaleUrl();
		if (preSaleService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preSaleService = (PreSaleService) factory.create(PreSaleService.class, hessianUrl);
		}
		return preSaleService;
	}

	@Override
	public Long insertEntity(PreSaleEntity obj) throws Exception {
		return gethessianPreSaleService().insertEntity(obj);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return gethessianPreSaleService().deleteById(id);
	}

	@Override
	public int batchDeleteById(List<Long> idList) throws Exception {
		return gethessianPreSaleService().batchDeleteById(idList);
	}

	@Override
	public int updateDTO(PreSaleDTO obj) throws Exception {
		return gethessianPreSaleService().updateDTO(obj);
	}

	@Override
	public int batchUpdateDTO(List<PreSaleDTO> objList) throws Exception {
		return gethessianPreSaleService().batchUpdateDTO(objList);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return gethessianPreSaleService().getTotal(map);
	}

	@Override
	public PreSaleDTO getById(Long id) throws Exception {
		return gethessianPreSaleService().getById(id);
	}

	@Override
	public List<PreSaleDTO> getListByConditionPage(Map<String, Object> map) throws Exception {
		return gethessianPreSaleService().getListByConditionPage(map);
	}

	@Override
	public List<PreSaleDTO> getListByCondition(Map<String, Object> map) throws Exception {
		return gethessianPreSaleService().getListByCondition(map);
	}

	
}
