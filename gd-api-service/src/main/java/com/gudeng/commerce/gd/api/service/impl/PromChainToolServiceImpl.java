package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.PromChainToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromProdInfoDTO;
import com.gudeng.commerce.gd.promotion.dto.PromProdResult;
import com.gudeng.commerce.gd.promotion.service.prom.PromChainControllerInti;


/** 
 *功能描述：
 */
@Service
public class PromChainToolServiceImpl implements PromChainToolService{
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static PromChainControllerInti promChainControllerInti;
	
	/**
	 * billDetailService接口服务
	 * @return
	 */
	protected PromChainControllerInti getHessianPromChainService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.promChainService.url");
		if(promChainControllerInti == null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promChainControllerInti = (PromChainControllerInti) factory.create(PromChainControllerInti.class, url);
		}
		return promChainControllerInti;
	}
	
	/**
	 * 获取活动产品信息
	 * 
	 * @param productId 产品id
	 * @return PromProdInfoDTO 活动产品信息 
	 */
	public PromProdInfoDTO getPromotionProduct(Long productId) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		getHessianPromChainService().execute(paramMap);
		PromProdResult promProdResult = (PromProdResult)paramMap.get("PromProdResult");
		PromProdInfoDTO promProdInfoDTO = null;
		if(promProdResult != null){
			promProdInfoDTO = promProdResult.getPromProdInfoDTO();
		}
		return promProdInfoDTO;
	}
}
