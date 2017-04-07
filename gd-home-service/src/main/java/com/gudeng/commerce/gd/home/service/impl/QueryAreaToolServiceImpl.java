package com.gudeng.commerce.gd.home.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.customer.dto.AreaDTO;
import com.gudeng.commerce.gd.customer.service.AreaService;
import com.gudeng.commerce.gd.home.service.QueryAreaToolService;
import com.gudeng.commerce.gd.home.util.GdProperties;

@Service
public class QueryAreaToolServiceImpl implements QueryAreaToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;

	private static AreaService areaService;

	/**
	 * 功能描述:接口服务
	 * 
	 * @param
	 * @return
	 */
	protected AreaService getHessianareaService() throws MalformedURLException {
		String url = gdProperties.getAreaServiceUrl();
		if (areaService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			areaService = (AreaService) factory.create(AreaService.class, url);
		}
		return areaService;
	}

	@Override
	public List<AreaDTO> findProvince() throws Exception {
		return getHessianareaService().listTopArea();
	}

	@Override
	public List<AreaDTO> findCity(String code) throws Exception {
		return getHessianareaService().listChildArea(code);
	}

	@Override
	public List<AreaDTO> findCounty(String code) throws Exception {
		return getHessianareaService().listChildArea(code);
	}

	@Override
	public AreaDTO getArea(String areaID) throws Exception {
		return getHessianareaService().getArea(areaID);
	}

	/**
	 * @Description listArea 获取所有的省市区县信息
	 * @return
	 * @throws Exception
	 * @CreationDate 2015年11月2日 上午9:23:59
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> listArea() throws Exception {
		return getHessianareaService().listArea();
	}
	
	/**
	 * 获取一级地区树
	 * @return
	 */
	public List<AreaDTO> getAreaParentTree() throws Exception{
		return getHessianareaService().getAreaParentTree();
	}
	/**
	 * @Description getCityTree 查询2级地区 
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:17
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getCityTree() throws Exception{
		return getHessianareaService().getCityTree();
	}
	/**
	 * @Description getAreaTree 查询3级及以上地区
	 * @return
	 * @CreationDate 2015年11月19日 上午9:48:32
	 * @Author lidong(dli@cnagri-products.com)
	 */
	public List<AreaDTO> getAreaTree() throws Exception{
		return getHessianareaService().getAreaTree();
	}

}
