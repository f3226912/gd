package com.gudeng.commerce.promotion;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.promotion.dto.ActReUserActivityDto;
import com.gudeng.commerce.gd.promotion.service.ActReUserActivityService;

import junit.framework.TestCase;

public class ReUserActivityServiceTest extends TestCase {

	private ActReUserActivityService getService() throws MalformedURLException {
		String url = "http://localhost:8080/gd-promotion/service/actReUserActivityService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (ActReUserActivityService) factory.create(ActReUserActivityService.class, url);
	}

	public void testReUserActivityInfo() throws Exception {
		ActReUserActivityService service = this.getService();
		ActReUserActivityDto dto = service.getReUserActivityInfo("1", "1");
		System.out.println(dto);
		dto.setScore(999);
		int res = service.updateReUserActivityInfo(dto);
		System.out.println(res);
	}

}
