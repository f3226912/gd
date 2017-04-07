package com.gudeng.commerce.promotion;

import java.net.MalformedURLException;
import java.util.List;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.promotion.dto.GrdGiftDetailDTO;
import com.gudeng.commerce.gd.promotion.service.GrdGiftDetailService;

import junit.framework.TestCase;

public class GrdGiftDetailServiceTest extends TestCase {

	private GrdGiftDetailService getService() throws MalformedURLException {
		String url = "http://10.17.1.166:8087/service/grdGiftDetailService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (GrdGiftDetailService) factory.create(GrdGiftDetailService.class, url);
	}

	public void testReUserActivityInfo() throws Exception {
		
		GrdGiftDetailService service = this.getService();
		List<GrdGiftDetailDTO> detailByMobileAndType = service.getDetailByMobileAndType("13712061300", "3");
		System.out.println(detailByMobileAndType.get(0).getId());
	}

}
