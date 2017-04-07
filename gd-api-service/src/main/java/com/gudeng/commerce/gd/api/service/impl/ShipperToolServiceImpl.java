package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.service.ShipperToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.order.dto.PreWeighCarDetailDTO;
import com.gudeng.commerce.gd.order.dto.ReWeighCarBusinessDTO;
import com.gudeng.commerce.gd.order.dto.WeighCarDTO;
import com.gudeng.commerce.gd.order.entity.WeighCarEntity;

public class ShipperToolServiceImpl implements ShipperToolService {
	private static Logger logger = LoggerFactory.getLogger(ShipperToolServiceImpl.class);
	@Autowired
	public GdProperties gdProperties;

	public static com.gudeng.commerce.gd.order.service.ShipperService shipperService;

	protected com.gudeng.commerce.gd.order.service.ShipperService hessianOrderWeighLogService()
			throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.shipperService.url");
		if (shipperService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			shipperService = (com.gudeng.commerce.gd.order.service.ShipperService) factory
					.create(com.gudeng.commerce.gd.order.service.ShipperService.class, url);
		}
		return shipperService;
	}

	public Long saveWeightLog(WeighCarEntity weighCarEntity) throws Exception {
		return hessianOrderWeighLogService().saveWeightLog(weighCarEntity);
	}

	@Override
	public Integer addProduct(PreWeighCarDetailDTO preWeighCarDetail) throws Exception {

		return hessianOrderWeighLogService().addProduct(preWeighCarDetail);
	}

	@Override
	public Integer updateProduct(PreWeighCarDetailDTO preWeighCarDetail) throws Exception {
		return hessianOrderWeighLogService().updateProduct(preWeighCarDetail);
	}

	@Override
	public Integer delProduct(PreWeighCarDetailDTO preWeighCarDetail) throws Exception {
		return hessianOrderWeighLogService().delProduct(preWeighCarDetail);
	}

	@Override
	public List<PreWeighCarDetailDTO> getProductlist(PreWeighCarDetailDTO preWeighCarDetail) throws Exception {
		return hessianOrderWeighLogService().getProductlist(preWeighCarDetail);
	}

	@Override
	public Integer submitWeightLog(List<ReWeighCarBusinessDTO> list) throws Exception {
		return hessianOrderWeighLogService().submitWeightLog(list);
	}

	@Override
	public Integer submitWeightLogClass(ReWeighCarBusinessDTO reWeighCarBusinessDTO) throws Exception {
		return hessianOrderWeighLogService().submitWeightLogClass(reWeighCarBusinessDTO);
	}

	@Override
	public Integer submit(ReWeighCarBusinessDTO reWeighCarBusinessDTO) throws Exception {
		return hessianOrderWeighLogService().submit(reWeighCarBusinessDTO);
	}

	

}
