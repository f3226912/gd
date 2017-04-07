package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.home.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.service.SearchBusinessService;


/** 
 *功能描述：
 */
@Service
public class BusinessBaseinfoToolServiceImpl implements BusinessBaseinfoToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static BusinessBaseinfoService businessBaseinfoService;
	private static SearchBusinessService searchBusinessService;
	
	/*@Autowired
	private BaseDao baseDao;*/

	/**
	 * 功能描述:businessBaseinfoService接口服务
	 * 
	 * @param
	 * @return
	 */
	protected BusinessBaseinfoService getHessianBusinessBaseinfoService() throws MalformedURLException {
		String url = gdProperties.getBusinessBaseinfoServiceUrl();
		if(businessBaseinfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}
	protected SearchBusinessService getHessianSearchBusinessService() throws MalformedURLException {
		String url = gdProperties.getSearchBusinessServiceUrl();
		if(searchBusinessService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			searchBusinessService = (SearchBusinessService) factory.create(SearchBusinessService.class, url);
		}
		return searchBusinessService;
	}

	@Override
	public BusinessBaseinfoDTO getById(String id) throws Exception {
		return getHessianBusinessBaseinfoService().getById(id);
	}
	
	
	@Override
	public BusinessBaseinfoDTO getByUserId(String userId) throws Exception {
		return getHessianBusinessBaseinfoService().getByUserId(userId);
	}
	
	@Override
	public BusinessBaseinfoDTO getByUserIdAndMarketId(String userId,String marketId) throws Exception {
		return getHessianBusinessBaseinfoService().getByUserIdAndMarketId(userId,marketId);
	}
	
	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return getHessianBusinessBaseinfoService().getTotal(map);
	}

	@Override
	public int deleteById(String id) throws Exception {
		return getHessianBusinessBaseinfoService().deleteById(id);
	}

	@Override
	public int addBusinessBaseinfoByMap(Map<String, Object> map)
			throws Exception {
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoByMap(map);
	}

	@Override
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception {
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoDTO(mc);
	}


	@Override
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be)
			throws Exception {
		// TODO Auto-generated method stub
		return getHessianBusinessBaseinfoService().addBusinessBaseinfoEnt(be);
	}

	@Override
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc)
			throws Exception {
		return getHessianBusinessBaseinfoService().updateBusinessBaseinfoDTO(mc);
	}

	@Override
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception {
		return getHessianBusinessBaseinfoService().getBySearch(map);
	}

	/**
	 * 根据店铺名称搜索 
	 * 
	 * 未实现
	 * 
	 * @param string 
	 * @return BusinessSolrDTO
	 * 
	 */
	public List<BusinessSolrDTO> getBySearch(String bName)throws Exception{
		return getHessianSearchBusinessService().getBySearch(bName);
	}
	
	
	/**
	 * 根据 businessQueryBean 搜索 
	 * 
	 * 未 现
	 * 
	 * @param  businessQueryBean 
	 * @return List<BusinessSolrDTO> 
	 * 
	 */
	public List<BusinessSolrDTO> getByQueryBean(BusinessQueryBean businessQueryBean)throws Exception{
		return getHessianSearchBusinessService().getByQueryBean(businessQueryBean);
	}


	
	/**
	 * 根据 businessQueryBean 搜索 
	 * 
	 * 未 现
	 * 
	 * @param  businessQueryBean 
	 * @return BusinessSearchResultDTO
	 * 
	 */
	
	public BusinessSearchResultDTO getByBusinessQueryBean(BusinessQueryBean businessQueryBean) throws Exception{
		return getHessianSearchBusinessService().getByBusinessQueryBean(businessQueryBean);
	}
	
	@Override
	public void addBrowser(Long businessId) throws Exception {
		getHessianBusinessBaseinfoService().addBrowser(businessId);
	}
	
	@Override
	public List<ReBusinessCategoryDTO> getCategoryList(Long businessId,int start, int size)
			throws Exception {
		return getHessianBusinessBaseinfoService().getCategoryList(businessId,start,size);
	}
	 
}
