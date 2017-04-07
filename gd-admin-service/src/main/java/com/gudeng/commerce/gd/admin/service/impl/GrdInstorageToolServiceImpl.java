package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GrdInstorageToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.GrdInstorageDTO;
import com.gudeng.commerce.gd.promotion.dto.GrdInstoragedetailDTO;

import com.gudeng.commerce.gd.promotion.service.GrdInstorageService;

public class GrdInstorageToolServiceImpl implements GrdInstorageToolService {
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	private static GrdInstorageService grdInstorageService;

	protected GrdInstorageService getHessianGrdInstorageService() throws MalformedURLException {
		String url = gdProperties.getGrdInstorageServiceUrl();
		if (grdInstorageService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			grdInstorageService = (GrdInstorageService) factory.create(GrdInstorageService.class, url);
		}
		return grdInstorageService;
	}

	@Override
	public int getTodayTotal(Map<String, Object> map) throws Exception {
		return getHessianGrdInstorageService().getTodayTotal(map);
	}

	@Override
	public Integer insert(GrdInstorageDTO dto,
			List<GrdInstoragedetailDTO> batchDetailDTO) throws Exception {
		return getHessianGrdInstorageService().insert(dto,batchDetailDTO);
	}

	@Override
	public GrdInstoragedetailDTO queryGrdGiftStockInfo(
			Map<String, Object> map) throws Exception {
		return getHessianGrdInstorageService().queryGrdGiftStockInfo(map);
	}

	

}
