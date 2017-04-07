package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.BusinessAppDTO;
import com.gudeng.commerce.gd.api.dto.output.BusinessDetailAppDTO;
import com.gudeng.commerce.gd.api.dto.output.SupplierBusinessBaseinfo;
import com.gudeng.commerce.gd.api.dto.output.TradingDynamicsOutDTO;
import com.gudeng.commerce.gd.api.params.BusinessParamsBean;
import com.gudeng.commerce.gd.api.service.BusinessBaseinfoToolService;
import com.gudeng.commerce.gd.api.service.ProductCategoryToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.BusinessBaseinfoDTO;
import com.gudeng.commerce.gd.customer.dto.ProductCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.ReBusinessCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectShopDTO;
import com.gudeng.commerce.gd.customer.entity.Area;
import com.gudeng.commerce.gd.customer.entity.BusinessBaseinfoEntity;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.customer.service.BusinessBaseinfoService;
import com.gudeng.commerce.gd.customer.service.ReBusinessCategoryService;
import com.gudeng.commerce.gd.customer.service.ReBusinessPosService;
import com.gudeng.commerce.gd.customer.service.UsercollectShopService;
import com.gudeng.commerce.gd.search.dto.BusinessQueryBean;
import com.gudeng.commerce.gd.search.dto.BusinessSearchResultDTO;
import com.gudeng.commerce.gd.search.dto.BusinessSolrDTO;
import com.gudeng.commerce.gd.search.dto.ProductFacetMarketResultDTO;
import com.gudeng.commerce.gd.search.service.SearchBusinessService;

public class BusinessBaseinfoToolServiceImpl implements BusinessBaseinfoToolService {

	private static Logger logger = LoggerFactory.getLogger(BusinessBaseinfoToolServiceImpl.class);

	@Autowired
	public GdProperties gdProperties;
	private static BusinessBaseinfoService businessBaseinfoService;

	private static SearchBusinessService searchBusinessService;

	private static UsercollectShopService usercollectShopService;

	private static ReBusinessCategoryService reBusinessCategoryService;
	
	private static ReBusinessPosService reBusinessPosService;
	
	
	private static AreaService areaService;

	@Autowired
	private ProductCategoryToolService productCategoryToolService;

	protected SearchBusinessService getHessianSearchBusinessService() throws MalformedURLException {
		String url = gdProperties.getSearchBusinessServiceUrl();
		if (searchBusinessService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			searchBusinessService = (SearchBusinessService) factory.create(SearchBusinessService.class, url);
		}
		return searchBusinessService;
	}

	private UsercollectShopService getUserCollectShopService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.userCollectShop.url");
		if (usercollectShopService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectShopService = (UsercollectShopService) factory.create(UsercollectShopService.class, hessianUrl);
		}
		return usercollectShopService;
	}

	/**
	 * 功能描述: reBusinessCategoryService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReBusinessCategoryService getHessianReBusinessCategoryService() throws MalformedURLException {
		String url = gdProperties.getReBusinessCategoryServiceUrl();
		if (reBusinessCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessCategoryService = (ReBusinessCategoryService) factory.create(ReBusinessCategoryService.class,
					url);
		}
		return reBusinessCategoryService;
	}

	/**
	 * 功能描述: reBusinessPosService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected ReBusinessPosService getHessianReBusinessPosService() throws MalformedURLException {
		String url = gdProperties.getReBusinessPosUrl();
		if (reBusinessPosService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			reBusinessPosService = (ReBusinessPosService) factory.create(ReBusinessPosService.class, url);
		}
		return reBusinessPosService;
	}

	protected AreaService getHessianAreaService() throws MalformedURLException {
		String url = gdProperties.getAreaUrl();
		if (areaService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}

		return areaService;
	}

	@Override
	public BusinessBaseinfoDTO getById(String id) throws Exception {
		return getHessianBussinessService().getById(id);
	}

	@Override
	public BusinessBaseinfoDTO getByUserId(String userId) throws Exception {
		return getHessianBussinessService().getByUserId(userId);
	}
	@Override
	public BusinessBaseinfoDTO getBusinessInfoByUserId(Map<String,Object> map) throws Exception {
		return getHessianBussinessService().getBusinessInfoByUserId(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) throws Exception {
		return 0;
	}

	@Override
	public int deleteById(String id) throws Exception {
		return 0;
	}

	@Override
	public int addBusinessBaseinfoByMap(Map<String, Object> map) throws Exception {
		return 0;
	}

	@Override
	public int addBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception {
		return 0;
	}

	@Override
	public Long addBusinessBaseinfoEnt(BusinessBaseinfoEntity be) throws Exception {
		return getHessianBussinessService().addBusinessBaseinfoEnt(be);
	}

	@Override
	public int updateBusinessBaseinfoDTO(BusinessBaseinfoDTO mc) throws Exception {
		return getHessianBussinessService().updateBusinessBaseinfoDTO(mc);
	}

	@Override
	public List<BusinessBaseinfoDTO> getBySearch(Map map) throws Exception {
		List<BusinessBaseinfoDTO> list = getHessianBussinessService().getBySearch(map);
		return list;
	}

	@Override
	public List<BusinessBaseinfoDTO> getAll(int startRow, int endRow) {
		try {
			List<BusinessBaseinfoDTO> listBusinessBaseinfoDTOs = getHessianBussinessService().getAll(startRow, endRow);
			return listBusinessBaseinfoDTOs;
		} catch (Exception e) {
			logger.error("获取农批商失败  ", e);
		}
		return null;

	}

	protected BusinessBaseinfoService getHessianBussinessService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.businessBaseinfo.url");
		if (businessBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			businessBaseinfoService = (BusinessBaseinfoService) factory.create(BusinessBaseinfoService.class, url);
		}
		return businessBaseinfoService;
	}

	@Override
	public List<BusinessAppDTO> getShops(Map<String, Object> map) throws Exception {
		List<BusinessBaseinfoDTO> list = getHessianBussinessService().getShops(map);
		List<BusinessAppDTO> returnList = null;
		if (list != null && list.size() > 0) {
			returnList = new ArrayList<>();
			for (int i = 0, len = list.size(); i < len; i++) {
				BusinessBaseinfoDTO dto = list.get(i);
				BusinessAppDTO appDto = new BusinessAppDTO();
				appDto.setIsFocus(dto.getIsFocus());
				appDto.setBusinessId(dto.getBusinessId());
				appDto.setMainProduct(dto.getMainProduct());
				appDto.setMarketId(Long.parseLong(dto.getMarketId()));
				appDto.setMarketName(dto.getMarketName());
				appDto.setMobile(dto.getMobile());
				appDto.setName(dto.getName());
				appDto.setShopsName(dto.getShopsName());
				appDto.setUserId(dto.getUserId());
				returnList.add(appDto);
			}
		}
		return returnList;
	}

	@Override
	public int getShopsTotal(Map<String, Object> map) throws Exception {
		return getHessianBussinessService().getShopsTotal(map);
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
	public List<BusinessSolrDTO> getBySearch(String bName) throws Exception {
		return getHessianSearchBusinessService().getBySearch(bName);
	}

	/**
	 * 根据 businessQueryBean 搜索
	 * 
	 * 未 现
	 * 
	 * @param businessQueryBean
	 * @return List<BusinessSolrDTO>
	 * 
	 */
	public List<BusinessSolrDTO> getByQueryBean(BusinessQueryBean businessQueryBean) throws Exception {
		return getHessianSearchBusinessService().getByQueryBean(businessQueryBean);
	}

	/**
	 * 根据 businessQueryBean 搜索
	 * 
	 * 未 现
	 * 
	 * @param businessQueryBean
	 * @return BusinessSearchResultDTO
	 * 
	 */

	public BusinessSearchResultDTO getByBusinessQueryBean(BusinessQueryBean businessQueryBean) throws Exception {
		businessQueryBean.setIsApp(true);
		return getHessianSearchBusinessService().getByBusinessQueryBean(businessQueryBean);
	}

	@Override
	public List<BusinessAppDTO> translate(List<BusinessSolrDTO> list, List<BusinessAppDTO> businessAppList, Long userId)
			throws Exception {
		// "isFocus": 1, //是否关注 0否, 1是
		usercollectShopService = getUserCollectShopService();
		areaService = getHessianAreaService();

		if (list != null && list.size() > 0) {
			for (BusinessSolrDTO businessSolrDTO : list) {
				BusinessAppDTO businessAppDTO = new BusinessAppDTO();
				businessAppDTO.setBusinessId(Long.valueOf(businessSolrDTO.getBusinessId()));
				businessAppDTO.setMainProduct(businessSolrDTO.getMainProduct());
				businessAppDTO.setMarketId(Long.valueOf(businessSolrDTO.getMarketId()));
				businessAppDTO.setMarketName(businessSolrDTO.getMarketName());
				businessAppDTO.setMobile(businessSolrDTO.getMobile());
				businessAppDTO.setMemberGrade(
						businessSolrDTO.getMemberGrade() == null ? "0" : businessSolrDTO.getMemberGrade().toString());
				businessAppDTO.setName(businessSolrDTO.getName());
				businessAppDTO.setShopsName(businessSolrDTO.getShopsName());
				businessAppDTO.setUserId(null);// userId未定,暂返回为空，有需要，往solr中增加再返回
				UsercollectShopDTO usercollectShopDTO = usercollectShopService.getCollectShop(userId,
						Long.valueOf(businessSolrDTO.getBusinessId()));
				if (usercollectShopDTO != null && usercollectShopDTO.getId() > 0) {
					businessAppDTO.setIsFocus(1);
				} else {
					businessAppDTO.setIsFocus(0);
				}

				String cateNames = "";
				if (businessSolrDTO.getCateName() != null && businessSolrDTO.getCateName().size() > 0) {
					for (String cateName : businessSolrDTO.getCateName()) {
						cateNames += cateName + ",";
					}
				}

				if (!cateNames.equals("")) {
					cateNames = cateNames.substring(0, cateNames.length() - 1);
				}
				businessAppDTO.setManagementType(businessSolrDTO.getManagementType());
				businessAppDTO.setBusinessModel(businessSolrDTO.getBusinessModel());
				businessAppDTO.setCateNames(cateNames);

				String originAddress = "";
				if (businessSolrDTO.getProvinceId() != null && !businessSolrDTO.getProvinceId().equals("")
						&& !businessSolrDTO.getProvinceId().equals("0")
						&& !businessSolrDTO.getProvinceId().equals("-1")) {
					Area province = areaService.getArea(String.valueOf(businessSolrDTO.getProvinceId()));
					originAddress += province == null ? "" : province.getArea();
					if (businessSolrDTO.getCityId() != null && !businessSolrDTO.getCityId().equals("")
							&& !businessSolrDTO.getCityId().equals("0") && !businessSolrDTO.getCityId().equals("-1")) {
						Area city = areaService.getArea(String.valueOf(businessSolrDTO.getCityId()));
						originAddress += city == null ? "" : city.getArea();
						if (businessSolrDTO.getAreaId() != null && !businessSolrDTO.getAreaId().equals("")
								&& !businessSolrDTO.getAreaId().equals("0")
								&& !businessSolrDTO.getAreaId().equals("-1")) {
							Area area = areaService.getArea(String.valueOf(businessSolrDTO.getAreaId()));
							originAddress += area == null ? "" : area.getArea();
						}
					}
				}
				businessAppDTO.setOriginAddress(originAddress);
				// 2016年4月28日 增加商铺产品个数的返回值
				businessAppDTO.setProductCount(
						businessSolrDTO.getProductId() == null ? 0 : businessSolrDTO.getProductId().size());

				businessAppDTO.setComstatus(businessSolrDTO.getComstatus());
				businessAppDTO.setCcstatus(businessSolrDTO.getCcstatus());
				businessAppDTO.setCbstatus(0);

				businessAppList.add(businessAppDTO);
			}
		}

		return businessAppList;
	}

	@Override
	public void copyPropeties(BusinessBaseinfoDTO dto, BusinessParamsBean params) {

		params.setBusinessId(dto.getBusinessId());
		params.setName(dto.getName());
		params.setBusinessModel(dto.getBusinessModel() == null ? -1 : dto.getBusinessModel());
		params.setShopsDesc(dto.getShopsDesc());
		params.setShopsName(dto.getShopsName());
		params.setMainProduct(dto.getMainProduct());
		params.setMarketId(dto.getMarketId());
		params.setMarketName(dto.getMarketName());
		params.setAddress(dto.getAddress());

		params.setLevel(dto.getLevel());
		params.setManagementType(dto.getManagementType());
		params.setAreaId(dto.getAreaId());
		params.setArea(dto.getArea());
		params.setProvince(dto.getProvince());
		params.setProvinceId(dto.getProvinceId());
		params.setCity(dto.getCity());
		params.setCityId(dto.getCityId());
		params.setMobile(dto.getMobile());
		// set member grade
		params.setMemberGrade(StringUtils.isBlank(dto.getMemberGrade()) ? "0" : dto.getMemberGrade());

		params.setManagementType(dto.getManagementType());// 产地供应商增加 经营类型
		String areaType = "";
		if (dto.getProvinceId() != null) {
			Area area = null;
			try {
				area = getHessianAreaService().getArea(String.valueOf(dto.getProvinceId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (area != null) {
				areaType = area.getAreaType();
			}
		}
		params.setAreaType(areaType);
		params.setShopsUrl(dto.getShopsUrl());
		params.setBrowseCount(dto.getBrowseCount());
	}

	// 商铺 经营分类 start---

	@Override
	public int addReBusinessCategoryDTO(ReBusinessCategoryDTO rb) throws Exception {
		return (int) getHessianReBusinessCategoryService().addReBusinessCategoryDTO(rb);
	}

	@Override
	public int deleteReBusinessCategoryDTO(ReBusinessCategoryDTO rb) throws Exception {
		return (int) getHessianReBusinessCategoryService().deleteReBusinessCategoryDTO(rb);
	}

	@Override
	public int deleteReBusinessCategoryByBusinessId(Long businessId) throws Exception {
		return (int) getHessianReBusinessCategoryService().deleteByBusinessId(businessId);
	}

	@Override
	public int getReBusinessCategoryTotal(Map map) throws Exception {
		return (int) getHessianReBusinessCategoryService().getTotal(map);
	}

	@Override
	public List<ReBusinessCategoryDTO> getReBusinessCategoryBySearch(Map map) throws Exception {
		return getHessianReBusinessCategoryService().getBySearch(map);
	}

	@Override
	public List<BusinessBaseinfoDTO> getShop(BusinessBaseinfoDTO businessBaseinfoDTO) throws Exception {
		return getHessianBussinessService().getShop(businessBaseinfoDTO);
	}
	// 商铺 经营分类 end---

	@Override
	public BusinessDetailAppDTO getByMap(Map<String, Object> map) throws Exception {
		BusinessBaseinfoDTO businessDTO = getHessianBussinessService().getByMap(map);
		BusinessDetailAppDTO appDTO = null;
		if (businessDTO == null) {
			return appDTO;
		}

		appDTO = new BusinessDetailAppDTO();
		appDTO.setBusinessId(businessDTO.getBusinessId());
		appDTO.setShopsName(businessDTO.getShopsName());
		appDTO.setShopsDesc(businessDTO.getShopsDesc());
		appDTO.setBusinessModelStr(businessDTO.getBusinessModelStr());
		appDTO.setMainProduct(businessDTO.getMainProduct());
		appDTO.setProvinceId(businessDTO.getProvinceId()); // 增加省份id
		appDTO.setCityId(businessDTO.getCityId()); // 增加城市id
		appDTO.setCountyId(businessDTO.getAreaId()); // 增加区县id
		appDTO.setAddress(businessDTO.getAddress());
		appDTO.setIsFocus(businessDTO.getIsFocus());
		appDTO.setMarketId(new Long(businessDTO.getMarketId()));
		appDTO.setMarketName(businessDTO.getMarketName());
		appDTO.setMobile(businessDTO.getMobile());
		appDTO.setUserId(businessDTO.getUserId());
		appDTO.setRealName(businessDTO.getRealName());
		return appDTO;
	}

	/**
	 * 查询秤mac地址
	 */
	public BusinessBaseinfoDTO getByMacAddr(String macAddr) throws Exception {
		return getHessianBussinessService().getByMacAddr(macAddr);
	}

	@Override
	public List<ProductFacetMarketResultDTO> getFacetMarket(BusinessQueryBean businessQueryBean) throws Exception {
		return getHessianSearchBusinessService().getFacetMarket(businessQueryBean);
	}

	public List<SupplierBusinessBaseinfo> getBusiness(Map param) throws Exception {

		List<BusinessBaseinfoDTO> businiessList = getHessianBussinessService().getSupplierShops(param);
		List<SupplierBusinessBaseinfo> supplierList = null;
		if (CollectionUtils.isNotEmpty(businiessList)) {
			Iterator<BusinessBaseinfoDTO> iter = businiessList.iterator();
			while (iter.hasNext()) {
				BusinessBaseinfoDTO business = iter.next();
				Map<String, Object> categoryMap = new HashMap<String, Object>();
				categoryMap.put("businessId", business.getBusinessId());
				List<ProductCategoryDTO> categoryList = productCategoryToolService.getCategoryNameList(categoryMap);
				for (int i = 0; i < categoryList.size(); i++) {
					if (business.getProductCategoryStr() == null) {
						business.setProductCategoryStr(categoryList.get(i).getCateName());
					} else {
						business.setProductCategoryStr(
								business.getProductCategoryStr() + ", " + categoryList.get(i).getCateName());
					}
				}
			}

			supplierList = new ArrayList<SupplierBusinessBaseinfo>(businiessList.size());
			setOutputDetail(businiessList, supplierList);
		}

		return supplierList;
	}

	public Integer getTotalBusiness(Map param) throws Exception {
		return getHessianBussinessService().getTotalShops(param);
	}

	private void setOutputDetail(List<BusinessBaseinfoDTO> businiessList, List<SupplierBusinessBaseinfo> supplierList) {
		for (int i = 0; i < businiessList.size(); i++) {
			BusinessBaseinfoDTO business = businiessList.get(i);
			SupplierBusinessBaseinfo supplier = new SupplierBusinessBaseinfo();
			supplier.setNickName(business.getRealName());
			supplier.setMobile(business.getMobile());
			supplier.setBusinessName(business.getShopsName());
			supplier.setBusinessId(business.getBusinessId() + "");
			supplier.setProductCategory(business.getProductCategoryStr());
			supplier.setMarketName(business.getMarketName());
			if (business.getBusinessModel() == null) {
				supplier.setBusinessMode("-1");
			} else {
				supplier.setBusinessMode(business.getBusinessModel() + "");
			}
			supplier.setTradingVolume(business.getTradingVolume());
			supplier.setComstatus(business.getComstatus());
			supplier.setCpstatus(business.getCpstatus());
			supplierList.add(supplier);
		}
	}

	@Override
	public List<TradingDynamicsOutDTO> getTradingDynamics(Map<String, Object> map) throws Exception {
		List<TradingDynamicsOutDTO> tradingList = null;
		List<BusinessBaseinfoDTO> businessList = getHessianBussinessService().getTradingDynamics(map);
		if (CollectionUtils.isNotEmpty(businessList)) {
			tradingList = new ArrayList<TradingDynamicsOutDTO>(businessList.size());
			Iterator<BusinessBaseinfoDTO> iter = businessList.iterator();
			while (iter.hasNext()) {
				BusinessBaseinfoDTO business = iter.next();
				TradingDynamicsOutDTO trading = new TradingDynamicsOutDTO();
				trading.setBusinessId(business.getBusinessId() + "");
				trading.setBusinessName(business.getShopsName());
				trading.setProductCategory(business.getProductCategoryStr());
				trading.setTradingVolume(business.getTradingVolume());
				trading.setRealName(business.getRealName());
				tradingList.add(trading);
			}
		}
		return tradingList;
	}

	@Override
	public String getPosInfoByBusinessId(Long businessId) throws Exception {
		return getHessianReBusinessPosService().getPosInfoByBusinessId(businessId);
	}
	
	
}
