package com.gudeng.commerce.promotion;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.promotion.service.prom.PromChainControllerInti;

import junit.framework.TestCase;

public class PromActServiceTest extends TestCase {

	private PromChainControllerInti getService() throws MalformedURLException {
		String url = "http://localhost:8080/gd-promotion/service/promChainController.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (PromChainControllerInti) factory.create(PromChainControllerInti.class, url);
	}

	public void testActInfo() throws Exception {
		PromChainControllerInti service = this.getService();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productId", 328);
		paramMap = service.execute(paramMap);
		
	}

}
