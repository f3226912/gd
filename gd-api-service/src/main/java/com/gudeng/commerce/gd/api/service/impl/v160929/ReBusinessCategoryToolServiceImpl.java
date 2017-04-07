package com.gudeng.commerce.gd.api.service.impl.v160929;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.v160929.ReBusinessCategoryToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.service.ReBusinessCategoryService;


/** 
 *功能描述：
 */
@Service
public class ReBusinessCategoryToolServiceImpl implements ReBusinessCategoryToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static ReBusinessCategoryService reBusinessCategoryService;

	/**
	 * 功能描述: reBusinessCategoryService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReBusinessCategoryService getHessianReBusinessCategoryService() throws MalformedURLException {
		String url = gdProperties.getReBusinessCategoryServiceUrl();
		if(reBusinessCategoryService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessCategoryService = (ReBusinessCategoryService) factory.create(ReBusinessCategoryService.class, url);
		}
		return reBusinessCategoryService;
	}

	@Override
	public int addReBusinessCategoryDTO(ReBusinessCategoryDTO rb)
			throws Exception {
		return (int)getHessianReBusinessCategoryService().addReBusinessCategoryDTO(rb);
	}

	@Override
	public int deleteReBusinessCategoryDTO(ReBusinessCategoryDTO rb)
			throws Exception {
		return (int)getHessianReBusinessCategoryService().deleteReBusinessCategoryDTO(rb);
	}

	@Override
	public int deleteByBusinessId(Long businessId) throws Exception {
		return (int)getHessianReBusinessCategoryService().deleteByBusinessId(businessId);
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return (int)getHessianReBusinessCategoryService().getTotal(map);
	}

	@Override
	public List<ReBusinessCategoryDTO> getBySearch(Map map) throws Exception {
		return getHessianReBusinessCategoryService().getBySearch(map);
	}

	/**
	 * 根据商铺ID获取主营分类ID
	 */
	@Override
	public ReBusinessCategoryDTO getCateIdByBusId(String businessId) throws Exception {
		
		return getHessianReBusinessCategoryService().getCateIdByBusId(businessId);
	}
 
	 
  
	 
}
