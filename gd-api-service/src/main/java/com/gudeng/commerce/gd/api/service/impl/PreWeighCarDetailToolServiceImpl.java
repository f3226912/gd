package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.service.PreWeighCarDetailService;

@Service
public class PreWeighCarDetailToolServiceImpl implements PreWeighCarDetailToolService{

	@Autowired
	private GdProperties gdProperties;
	
	public static PreWeighCarDetailService preWeighCarDetailService;
	
	public PreWeighCarDetailService getHessianPreWeighCarDetailService() throws MalformedURLException{
		if(preWeighCarDetailService == null){
			String url = gdProperties.getPreweighCarDetailUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			preWeighCarDetailService = (PreWeighCarDetailService) factory.create(PreWeighCarDetailService.class, url);
		}
		return preWeighCarDetailService;
	}
	
	@Override
	public List<PreWeighCarDetailDTO> getByWeighCarId(Long weighCarId) throws Exception{
		return getHessianPreWeighCarDetailService().getByWeighCarId(weighCarId);
	}
	
	@Override
	public List<PreWeighCarDetailDTO> getByMobile(Map map) throws Exception{
		return getHessianPreWeighCarDetailService().getByMobile(map);
	}

	@Override
	public PreWeighCarDetailDTO getById(Long id) throws Exception {
		return getHessianPreWeighCarDetailService().getById(id);
	}

	@Override
	public List<PreWeighCarDetailDTO> getByBusinessUserId(Map map) throws Exception {
		return getHessianPreWeighCarDetailService().getByBusinessUserId(map);
	}
	@Override
	public int getCountByUserId(Long userId) throws Exception {
		return getHessianPreWeighCarDetailService().getCountByUserId(userId);
	}
	@Override
	public int getCountByMobile(String mobile) throws Exception {
		return getHessianPreWeighCarDetailService().getCountByMobile(mobile);
	}

//	@Override
//	public List<PreWeighCarDetailDTO> getByCategoryUserId(Long userId) throws Exception {
//		return getHessianPreWeighCarDetailService().getByCategoryUserId(userId);
//	}

	@Override
	public int update(PreWeighCarDetailDTO preWeighCarDetailDTO)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianPreWeighCarDetailService().update(preWeighCarDetailDTO);
	}

	@Override
	public int grapGoods(PreWeighCarDetailDTO pwd, Long inStoreNo,Long businessId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianPreWeighCarDetailService().grapGoods(pwd,inStoreNo,businessId);
	}

	@Override
	public int deleteBusiness(Long weighCarId, Long businessId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianPreWeighCarDetailService().deleteBusiness( weighCarId,  businessId);
	}

}
