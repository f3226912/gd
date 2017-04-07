package com.gudeng.commerce.gd.api.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.PushAdAppDTO;
import com.gudeng.commerce.gd.api.service.OrderSubToolService;
import com.gudeng.commerce.gd.api.service.PushAdInfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.api.util.MoneyUtil;
import com.gudeng.commerce.gd.customer.dto.NstNoticeEntityDTO;
import com.gudeng.commerce.gd.customer.dto.PushAdInfoDTO;
import com.gudeng.commerce.gd.customer.dto.PushProductDTO;
import com.gudeng.commerce.gd.customer.service.PushAdInfoService;

@Service
public class PushAdInfoToolServiceImpl implements PushAdInfoToolService {

	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties; 
	
	private static PushAdInfoService pushAdInfoService;
	
	@Autowired
	public OrderSubToolService orderSubToolService;
	
	protected PushAdInfoService getHessianPushAdInfoService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.pushAdInfo.url");
		if(pushAdInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			pushAdInfoService = (PushAdInfoService) factory.create(PushAdInfoService.class, url);
		}
		return pushAdInfoService;
	}

	@Override
	public List<PushAdAppDTO> getAdList(Map<String, Object> map)	throws Exception {
		List<PushAdInfoDTO> pushAdInfoList = getHessianPushAdInfoService().getListByConditionPage(map);
		
		List<PushAdAppDTO> pushAdList = null;
		//增加服务器前缀地址
		if(pushAdInfoList != null && pushAdInfoList.size() > 0){
			pushAdList = new ArrayList<PushAdAppDTO>();
			String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
			for(int i=0, len=pushAdInfoList.size(); i<len; i++){
				PushAdInfoDTO dto = pushAdInfoList.get(i);
				PushAdAppDTO adDTO = new PushAdAppDTO();
				adDTO.setAdCanal(dto.getAdCanal());
				adDTO.setAdLinkUrl(dto.getAdLinkUrl());
				adDTO.setAdName(dto.getAdName());
				adDTO.setAdUrl(dto.getAdUrl());
				adDTO.setMarketId(Long.parseLong(dto.getMarketId()));
				adDTO.setProductId(dto.getProductId());
				adDTO.setAdUrl(imageHost + dto.getAdUrl());
				pushAdList.add(adDTO);
			}
		}
		return pushAdList;
	}

	@Override
	public List<PushProductDTO> getProductList(Map<String, Object> map)
			throws Exception {
		List<PushProductDTO> list = getHessianPushAdInfoService().getPushProductList(map);
		String imageHost =  gdProperties.getProperties().getProperty("gd.image.server");
		for (PushProductDTO pushProductDTO : list) {
			Double oringialPrice=pushProductDTO.getPrice();
			String formatedPrice=MoneyUtil.format(oringialPrice);
			pushProductDTO.setFormattedPrice(formatedPrice);
			pushProductDTO.setImageUrl(imageHost + pushProductDTO.getImageUrl());
		}
		
		list = orderSubToolService.addActivityDetail(list);
		return list;
	}

	@Override
	public int getProductTotal(Map<String, Object> map) throws Exception {
		return getHessianPushAdInfoService().getProductTotal(map);
	}

	@Override
	public List<NstNoticeEntityDTO> getNoticeList() throws Exception {
		// TODO Auto-generated method stub
		return getHessianPushAdInfoService().getNoticeList();
	}
}
