package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.InStoreDetailToolService;
import com.gudeng.commerce.gd.api.service.PreWeighCarDetailToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.InStoreDetailDTO;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.entity.InStoreDetailEntity;
import com.gudeng.commerce.gd.order.service.InStoreDetailService;

@Service
public class InStoreDetailToolServiceImpl implements InStoreDetailToolService{

	@Autowired
	private GdProperties gdProperties;
	
	public static InStoreDetailService inStoreDetailService;
	
	public InStoreDetailService getHessianInStoreDetailService() throws MalformedURLException{
		if(inStoreDetailService == null){
			String url = gdProperties.getInStoreDetailServiceUrl();
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			inStoreDetailService = (InStoreDetailService) factory.create(InStoreDetailService.class, url);
		}
		return inStoreDetailService;
	}

	@Override
	public InStoreDetailDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return getHessianInStoreDetailService().getById(id);
	}

	@Override
	public int deleteById(Long id) throws Exception {
		return getHessianInStoreDetailService().deleteById(id);
	}

	@Override
	public int updateByDto(InStoreDetailDTO inStoreDetailDTO) throws Exception {
		// TODO Auto-generated method stub
		return getHessianInStoreDetailService().updateByDto(inStoreDetailDTO);
	}

	@Override
	public Long addByEntity(InStoreDetailEntity inStoreDetailEntity)
			throws Exception {
		return getHessianInStoreDetailService().addByEntity(inStoreDetailEntity);
	}

	@Override
	public List<InStoreDetailDTO> getBySearch(Map map) throws Exception {
		return getHessianInStoreDetailService().getBySearch(map);
	}

	@Override
	public List<InStoreDetailDTO> getPageBySearch(Map map) throws Exception {
		return getHessianInStoreDetailService().getPageBySearch(map);
	}
	@Override
	public List<InStoreDetailDTO> getByBusinessId(Map map) throws Exception {
		return getHessianInStoreDetailService().getByBusinessId(map);
	}
	@Override
	public int getCountByBusinessId(Map map) throws Exception {
		return getHessianInStoreDetailService().getCountByBusinessId(map);
	}

}
