package com.gudeng.commerce.gd.m.service.impl.act;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.m.service.act.ActivityPromotionToolService;
import com.gudeng.commerce.gd.m.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PictureRefDTO;
import com.gudeng.commerce.gd.promotion.dto.ProductBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromBaseinfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromMarketDTO;
import com.gudeng.commerce.gd.promotion.service.ProductBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.PromBaseinfoService;
import com.gudeng.commerce.gd.promotion.service.PromProdinfoService;

public class ActivityPromotionToolServiceImpl implements ActivityPromotionToolService {

	/** 记录日志 */
	private static Logger logger = LoggerFactory.getLogger(ActivityPromotionToolServiceImpl.class);
	
	@Autowired
	public GdProperties gdProperties;
	
	//促销活动
	private PromBaseinfoService promBaseinfoService;
	//活动促销商品
	private PromProdinfoService promProdinfoService;
	//商品库
	private ProductBaseinfoService productBaseinfoService;

	private PromBaseinfoService getHessianbaseService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promBaseinfo.url");
		if (promBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promBaseinfoService = (PromBaseinfoService) factory.create(PromBaseinfoService.class, hessianUrl);
		}
		return promBaseinfoService;
	}
	
	@SuppressWarnings("unused")
	private PromProdinfoService getHessianbaseService1() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promProdinfo.url");
		if (promProdinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promProdinfoService = (PromProdinfoService) factory.create(PromProdinfoService.class, hessianUrl);
		}
		return promProdinfoService;
	}
	
	@SuppressWarnings("unused")
	private ProductBaseinfoService getHessianbaseService2() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.productBaseinfo.url");
		if (productBaseinfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			productBaseinfoService = (ProductBaseinfoService) factory.create(ProductBaseinfoService.class, hessianUrl);
		}
		return productBaseinfoService;
	}

	@Override
	public List<PromBaseinfoDTO> queryPromoteActivitys(Map<String,Object> map) throws Exception {
		return getHessianbaseService().queryPromoteActivitys(map);
	}
	@Override
	public int getTotal(Map<String,Object> map) throws Exception {
		return getHessianbaseService().getTotal(map);
	}

	@Override
	public List<PictureRefDTO> getPictures(int promBaseinfoId) throws Exception {
		return getHessianbaseService().getPictures(promBaseinfoId);
	}

	@Override
	public PromBaseinfoDTO getDetailActivity(Map<String,Object> map) throws Exception {
		return getHessianbaseService().getDetail(map);
	}

	@Override
	public List<ProductBaseinfoDTO> getUserProducts(Map<String, Object> paramMap) throws Exception {
		return getHessianbaseService2().getProductsByUser(paramMap);
	}

	@Override
	public int getProductTotalByUser(Map<String, Object> paramMap) throws Exception {
		return getHessianbaseService2().getProductTotalByUser(paramMap);
	}

	@Override
	public int addActivityProducts(Map<String, Object> paramMap) throws Exception {
		return getHessianbaseService1().addProducts(paramMap);
	}

	@Override
	public int getProductTotalByParticipants(Map<String, Object> paramMap) throws Exception {
		return getHessianbaseService2().getProductTotalByParticipants(paramMap);
	}

	@Override
	public List<ProductBaseinfoDTO> getUserParticipantsProducts(Map<String, Object> paramMap) throws Exception {
		return getHessianbaseService2().getUserParticipantsProducts(paramMap);
	}

	@Override
	public List<PromMarketDTO> getMarkets(Integer actId) throws Exception {
		// TODO Auto-generated method stub
		return getHessianbaseService().queryPromMarketByActId(actId+"");
	}

	@Override
	public int cancelPromotionActivity(Map<String, Object> paramMap) throws Exception {
		// TODO Auto-generated method stub
		
		return getHessianbaseService1().cancelPromotionActivity(paramMap);
	}

}
