package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.dto.BusinessSteelyardInputDTO;
import com.gudeng.commerce.gd.admin.service.ReBusinessSteelyardToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ReBusinessSteelyardDTO;
import com.gudeng.commerce.gd.customer.entity.ReBusinessSteelyardEntity;
import com.gudeng.commerce.gd.customer.service.ReBusinessSteelyardService;

@Service
public class ReBusinessSteelyardToolServiceImpl implements
		ReBusinessSteelyardToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static ReBusinessSteelyardService reBusinessSteelyardService;
	
	protected ReBusinessSteelyardService getHessianReBusinessSteelyardService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.reBusinessSteelyardService.url");;
		if (reBusinessSteelyardService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessSteelyardService = (ReBusinessSteelyardService) factory.create(ReBusinessSteelyardService.class, url);
		}
		return reBusinessSteelyardService;
	}
	
	@Override
	public Long addEntities(BusinessSteelyardInputDTO inputDTO)
			throws Exception {
		Long addNum = 0L;
		Long businessId = inputDTO.getBusinessId();
		String macAddrArrStr = inputDTO.getMacAddr();
		String stlydIdArrStr = inputDTO.getStlydId();
		if(StringUtils.isNotBlank(macAddrArrStr)){
			String []stlydIdArr = stlydIdArrStr.split(",");
			String []macAddrArr = macAddrArrStr.split(",");
			for(int i=0; i < macAddrArr.length; i++){
				if(StringUtils.isNotBlank(macAddrArr[i])){
					ReBusinessSteelyardEntity entity = new ReBusinessSteelyardEntity();
					entity.setBusinessId(businessId);
					entity.setMacAddr(macAddrArr[i]);
					entity.setStlydId(stlydIdArr[i]);
					addNum = addNum + getHessianReBusinessSteelyardService().addReBusinessSteelyardEntity(entity);
				}
			}
			
		}
		return addNum;
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		return getHessianReBusinessSteelyardService().deleteByBusinessId(businessId);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return getHessianReBusinessSteelyardService().deleteById(id);
	}

	@Override
	public List<ReBusinessSteelyardDTO> getReBusinessSteelyardByBusinessId(
			Long businessId) throws Exception {
		return getHessianReBusinessSteelyardService().getReBusinessSteelyardByBusinessId(businessId);
	}

	@Override
	public Long getByMacAddr(BusinessSteelyardInputDTO inputDTO)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("macAddr", inputDTO.getMacAddr());
		paramMap.put("stlydId", inputDTO.getStlydId());
		return getHessianReBusinessSteelyardService().getByCondition(paramMap);
	}

}
