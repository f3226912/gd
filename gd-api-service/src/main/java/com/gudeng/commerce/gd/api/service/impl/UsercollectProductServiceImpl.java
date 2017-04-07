package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.PushProductAppDTO;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.UsercollectProductToolService;
import com.gudeng.commerce.gd.api.util.FormatUtils;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductCategoryDTO;
import com.gudeng.commerce.gd.customer.dto.UsercollectProductDTO;
import com.gudeng.commerce.gd.customer.service.UsercollectProductCategoryService;
import com.gudeng.commerce.gd.customer.service.UsercollectProductService;


@Service
public class UsercollectProductServiceImpl implements UsercollectProductToolService{

	private static UsercollectProductCategoryService usercollectProductCategoryService;

	private static Logger logger = LoggerFactory.getLogger(UsercollectProductServiceImpl.class);

	/**
	 * 属性文件，用于获得gd-setting.properties配置文件的值
	 */
	@Autowired
	public GdProperties gdProperties;

	private UsercollectProductService usercollectProductService;

	@Autowired
	private  ProductToolServiceImpl productToolServiceImpl;

	@Autowired
	public OrderSubToolService orderSubToolService;

	@Override
	public List<PushProductAppDTO> getList(Long userId,Long businessId,Long marketId,Long cateId,int startRow,int endRow) throws MalformedURLException {
		List<PushProductDTO> retList=getUserProductService().getList(userId,businessId,marketId,cateId,startRow,endRow);

		try {
			retList = orderSubToolService.addActivityDetail(retList);
		} catch (Exception e) {
			logger.warn("添加活动信息异常[ERROR]", e);
		}

		List<PushProductAppDTO> appList = new ArrayList<PushProductAppDTO>();
		String imageHost = gdProperties.getProperties().getProperty("gd.image.server");
		for (PushProductDTO pushProductDTO : retList) {
			PushProductAppDTO appDTO = new PushProductAppDTO();
			Double oringialPrice=pushProductDTO.getPrice();
			String formatedPrice=MoneyUtil.format(oringialPrice);
			appDTO.setBusinessId(pushProductDTO.getBusinessId());
			appDTO.setExpirationDate(pushProductDTO.getExpirationDate());
			appDTO.setFormattedPrice(formatedPrice);
			appDTO.setFormattedStock(FormatUtils.formattedStock(pushProductDTO.getStockCount(), pushProductDTO.getUnitName()));
			appDTO.setImageUrl(imageHost + pushProductDTO.getImageUrl());
			appDTO.setMarketId(pushProductDTO.getMarketId());
			appDTO.setMarketName(pushProductDTO.getMarketName());
			appDTO.setMobile(pushProductDTO.getMobile());
			appDTO.setPrice(pushProductDTO.getPrice());
			appDTO.setProductId(pushProductDTO.getProductId());
			appDTO.setProductName(pushProductDTO.getProductName());
			appDTO.setShopsName(pushProductDTO.getShopsName());
			appDTO.setStartTime(pushProductDTO.getStartTime());
			appDTO.setState(pushProductDTO.getState());
			appDTO.setStockCount(pushProductDTO.getStockCount());
			appDTO.setUnitName(pushProductDTO.getUnitName());
			appDTO.setHasActivity(pushProductDTO.getHasActivity());
			appList.add(appDTO);
		}

		return appList;
	}

	@Override
	public int getTotal(Long userId, Long businessId, Long marketId, Long cateId) throws MalformedURLException {
			return getUserProductService().getTotal(userId, businessId, marketId, cateId);
	}

	private UsercollectProductService getUserProductService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getUserProduct();
		if (usercollectProductService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectProductService = (UsercollectProductService) factory.create(
					UsercollectProductService.class, hessianUrl);
		}
		return usercollectProductService;
	}

	/**
	 * 获取用户关注的所有单品
	 * @throws MalformedURLException
	 */
	@Override
	public List<UsercollectProductDTO> getProductList(Long memberId,Long marketId) throws MalformedURLException {
			List<UsercollectProductDTO> pList = getUserProductService().getProductList(memberId,marketId);
			return pList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Long  addFocus(Long userId, Long productId) throws Exception {

			List<UsercollectProductDTO> focusedList= getProductList(userId,null);
			List listFocusProId=new ArrayList<>();
			for (UsercollectProductDTO usercollectProductDTO : focusedList) {
				listFocusProId.add(usercollectProductDTO.getProductId());
			}

			if (listFocusProId.contains(productId)) {
				//已经关注了该单品
				logger.error("该单品已经关注过  productId",productId);
				return productId;
			}
			else {
				Long categoryId=productToolServiceImpl.getByProductId(productId.toString()).getCateId();
				//关注单品
				getUserProductService().addFocus( userId,  productId,categoryId);
				List<UsercollectProductCategoryDTO> focusedCateList = getUserProductCategoryService()
						.getFocusCategory(userId);
				List<Long> allFocusCateId = new ArrayList<>();
				for (UsercollectProductCategoryDTO singleFoucsCate : focusedCateList) {
					allFocusCateId.add(singleFoucsCate.getProductCategoryId());
				}

				//已经关注了该二级分类
				if (allFocusCateId.contains(categoryId)) {
					logger.error("已经关注了该二级分类");
					return null;
				}
				else {
					//关注该单品对于的二级类目
					getUserProductCategoryService().focus(categoryId, userId);
				}
			}

		return null;

	}
	public void  batAddFocus(Long userId, String productIds ) {
		List<String> productIdLst=Arrays.asList(productIds.split(","));

		try {
			for (String productId : productIdLst) {
				addFocus(userId, Long.valueOf(productId));
			}
		} catch (Exception e) {
			logger.error("添加关注单品异常   ",e);
		}

	}

	@Override
	public void cancelFocus(Long userId, Long productId) throws MalformedURLException {
			getUserProductService().cancelFocus( userId,  productId);
	}

	private UsercollectProductCategoryService getUserProductCategoryService()
			throws MalformedURLException {
		String hessianUrl = gdProperties.getUserProductCategoryUrl();
		if (usercollectProductCategoryService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			usercollectProductCategoryService = (UsercollectProductCategoryService) factory.create(
					UsercollectProductCategoryService.class, hessianUrl);
		}
		return usercollectProductCategoryService;
	}

	@Override
	public List<UsercollectProductDTO> getProductsOfConcerned(Map<String, Object> params) throws Exception {
		List<UsercollectProductDTO> list = getUserProductService().getProductsOfConcerned(params);
		UsercollectProductDTO current = null ;
		for(Iterator<UsercollectProductDTO> it = list.iterator(); it.hasNext();){
			current = it.next();
			//设置展示价格
			if (current.getPrice().doubleValue() == 0){
				current.setShowedPrice("面议");
			}else{
				current.setShowedPrice(String.valueOf(current.getPrice()));
			}
			current.setAppListImg(getImgHost() + current.getAppListImg());
		}
		return list ;
	}

	@Override
	public int getProductsCountOfConcerned(Map<String, Object> params) throws Exception {
		return getUserProductService().getProductsCountOfConcerned(params);
	}

	private String getImgHost(){
		return gdProperties.getProperties().getProperty("gd.image.server", "");

	}

}
