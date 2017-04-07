package com.gudeng.commerce.customer;

import java.net.MalformedURLException;
import java.util.List;

import junit.framework.TestCase;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;

public class AreaServiceImplTest  extends TestCase{
	private static AreaService getService() throws MalformedURLException{
		String url = "http://127.0.0.1:8080/gd-customer/service/areaService.hs";
		HessianProxyFactory factory = new HessianProxyFactory();
		return (AreaService) factory.create(AreaService.class, url);				
	}
	
	public void testlistTopArea() throws Exception{
		List<AreaDTO> list = getService().listTopArea();
		if(null != list){
			for(AreaDTO dto : list){
				System.out.println(dto.getAreaID()+":"+dto.getArea());
			}
		}
	}
	
	public void testGetArea() throws Exception{
		AreaDTO dto = getService().getArea("210000");
		if(null != dto){
			System.out.println(dto.getAreaID()+":"+dto.getArea());
		}
	}
	
	public void testlistChildArea() throws Exception{
		List<AreaDTO> list = getService().listChildArea("210000");
		if(null != list){
			for(AreaDTO dto : list){
				System.out.println(dto.getAreaID()+":"+dto.getArea());
			}
		}
	}
	
	public void tesgetAreaChildTree() throws Exception{
		List<AreaDTO> list = getService().getAreaChildTree("210000");
		if(null != list){
			for(AreaDTO dto : list){
				System.out.println(dto.getAreaID()+":"+dto.getArea());
			}
		}
	}

}
