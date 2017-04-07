package com.gudeng.commerce.gd.admin.service.impl;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.admin.service.GiftToolService;
import com.gudeng.commerce.gd.admin.util.GdProperties;
import com.gudeng.commerce.gd.customer.dto.CallstatiSticsDTO;
import com.gudeng.commerce.gd.customer.dto.GiftDTO;
import com.gudeng.commerce.gd.customer.dto.MemberBaseinfoDTO;
import com.gudeng.commerce.gd.customer.entity.GiftEntity;
import com.gudeng.commerce.gd.customer.entity.PushRecordEntity;
import com.gudeng.commerce.gd.customer.service.GiftService;

/** 
 *功能描述：
 */
@Service
public class GiftToolServiceImpl implements GiftToolService{ 
	
	/** hessian 接口配置工具 **/
	@Autowired
	public GdProperties gdProperties;
	
	private static GiftService giftService;

	/**
	 * 功能描述: giftService 接口服务
	 * 
	 * @param
	 * @return
	 */
	protected GiftService getHessianGiftService() throws MalformedURLException {
		String url = gdProperties.getGiftUrl();
		if(giftService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			giftService = (GiftService) factory.create(GiftService.class, url);
		}
		return giftService;
	}

	@Override
	public int getTotal(Map map) throws Exception {
		return getHessianGiftService().getTotal(map);
	}
	
	@Override
	public int getCountByName(Map map) throws Exception {
		return getHessianGiftService().getCountByName(map);
	}
	
	@Override
	public int getCountByType(Map map) throws Exception {
		return getHessianGiftService().getCountByType(map);
	}
	
	@Override
	public List<GiftDTO> getBySearch(Map map) throws Exception {
		return getHessianGiftService().getBySearch(map);
	}
	
	@Override
	public int add(GiftEntity giftEntity) throws Exception {
		return getHessianGiftService().add(giftEntity);
	}
	
	@Override
	public GiftDTO getById(String id) throws Exception {
		return getHessianGiftService().getById(id);
	}
	
	@Override
	public int update(GiftDTO giftDTO) throws Exception {
		return getHessianGiftService().update(giftDTO);
	}
}
