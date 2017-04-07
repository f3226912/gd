package com.gudeng.commerce.gd.api.util;

import java.util.HashMap;
import java.util.Map;

import com.gudeng.commerce.gd.api.controller.FocusCategoryController;
import com.gudeng.commerce.gd.api.service.NstSameCityAddressToolService;
import com.gudeng.commerce.gd.customer.dto.NstSameCityAddressDTO;
import com.gudeng.framework.core2.GdLogger;
import com.gudeng.framework.core2.GdLoggerFactory;
/**
 * 同城货源分配
 * @author xiaojun
 */
public class AssignSameCityRuleThread implements Runnable {
	
	private static final GdLogger logger = GdLoggerFactory
			.getLogger(FocusCategoryController.class);
	
	private NstSameCityAddressToolService NstSameCityAddressToolService;
	
	private NstSameCityAddressDTO nstSameCityAddressDTO;

	public AssignSameCityRuleThread() {

	}
	public AssignSameCityRuleThread(NstSameCityAddressToolService nstSameCityAddressToolService,
			NstSameCityAddressDTO nstSameCityAddressDTO) {
		this.nstSameCityAddressDTO=nstSameCityAddressDTO;
		this.NstSameCityAddressToolService=nstSameCityAddressToolService;
	}

	@Override
	public void run() {
		Map<String, Object> map=new HashMap<>();
		map.put("id", nstSameCityAddressDTO.getId());
		map.put("clients", nstSameCityAddressDTO.getClients());
		map.put("s_cityId", nstSameCityAddressDTO.getS_cityId());
		try {
			NstSameCityAddressToolService.assign(map);
		} catch (Exception e) {
			logger.warn("分配失败");
			e.printStackTrace();
		}
	}

}
