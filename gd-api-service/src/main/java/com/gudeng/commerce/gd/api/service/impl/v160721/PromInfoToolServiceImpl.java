package com.gudeng.commerce.gd.api.service.impl.v160721;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.api.dto.CommonFeeAppDTO;
import com.gudeng.commerce.gd.api.service.v160721.PromInfoToolService;
import com.gudeng.commerce.gd.api.util.GdProperties;
import com.gudeng.commerce.gd.promotion.dto.PromFeeByActDTO;
import com.gudeng.commerce.gd.promotion.dto.PromOrderProminfoDTO;
import com.gudeng.commerce.gd.promotion.service.PromActProdInfoService;
import com.gudeng.commerce.gd.promotion.service.PromFeeByActService;
import com.gudeng.commerce.gd.promotion.service.prom.PromChainControllerInti;

public class PromInfoToolServiceImpl implements PromInfoToolService {

	@Autowired
	public GdProperties gdProperties;
	
	private PromChainControllerInti promChainControllerInti;
	
	private PromActProdInfoService promActProdInfoService;
	
	private PromFeeByActService promFeeByActService;

	private PromFeeByActService getHessianPromFeeByActService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promFeeByActService.url");
		if (promFeeByActService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promFeeByActService = (PromFeeByActService) factory.create(PromFeeByActService.class, hessianUrl);
		}
		return promFeeByActService;
	}
	
	private PromActProdInfoService getHessianPromActProdInfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promActProdInfoService.url");
		if (promActProdInfoService == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promActProdInfoService = (PromActProdInfoService) factory.create(PromActProdInfoService.class, hessianUrl);
		}
		return promActProdInfoService;
	}
	
	private PromChainControllerInti getHessianPromInfoService() throws MalformedURLException {
		String hessianUrl = gdProperties.getProperties().getProperty("gd.promInfoService.url");
		if (promChainControllerInti == null) {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			promChainControllerInti = (PromChainControllerInti) factory.create(PromChainControllerInti.class, hessianUrl);
		}
		return promChainControllerInti;
	}

	@Override
	public Map<String, Object> getActProductInfo(Map<String, Object> map) throws Exception {
		return getHessianPromInfoService().execute(map);
	}

	@Override
	public PromOrderProminfoDTO getPromInfoByOrderNo(Long orderNo)
			throws Exception {
		return getHessianPromActProdInfoService().getPromOrderInfoByOrderNo(orderNo);
	}

	@Override
	public CommonFeeAppDTO getIsFeePaidByAct(Integer buyerId, Integer sellId,
			Integer actId, CommonFeeAppDTO feeDTO) throws Exception {
		List<Integer> memberIdList = new ArrayList<>();
		if(buyerId != null){
			memberIdList.add(buyerId);
		}
		if(sellId != null){
			memberIdList.add(sellId);
		}
		List<PromFeeByActDTO> list = getHessianPromFeeByActService().getByMemberIdListAndActId(memberIdList , actId);
		
		if(list != null && list.size() > 0){
			for(PromFeeByActDTO dto : list){
				if(dto.getMemberId().intValue() == buyerId.intValue()){
					feeDTO.setBuyerFeePaid(true);
				}
				if(dto.getMemberId().intValue() == sellId.intValue()){
					feeDTO.setSellerFeePaid(true);
				}
			}
		}
		return feeDTO;
	}
}
