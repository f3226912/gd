package com.gudeng.commerce.bi;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.bi.entity.GrdProPertenEntity;
import com.gudeng.commerce.gd.bi.service.GrdProPertenService;

import junit.framework.TestCase;

public class GrdProPertenServiceTest extends TestCase{
	private static GrdProPertenService getService() throws MalformedURLException{
		String url = "http://localhost:8080/gd-bi/service/grdProPertenService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (GrdProPertenService) factory.create(GrdProPertenService.class, url);				
	}
	
	public void testAddByEnt()throws Exception {
		GrdProPertenService service=this.getService();
		int year=2016;
		
		for(int type=5;type<8;type++){
			for(int i=1;i<3;i++){
				int month=8;
				for(;month<9;month++){
					GrdProPertenEntity entity = new GrdProPertenEntity();
					int code =Integer.valueOf( year+"0"+month+""+i); 
					entity.setCode(code);
					entity.setCount(10);
					entity.setMobile("13430135673");
					entity.setMemberId(224);
					entity.setType(type);
					entity.setStatus(0);
					service.insert(entity);
				}
			}
		}
		
	}
	
}
