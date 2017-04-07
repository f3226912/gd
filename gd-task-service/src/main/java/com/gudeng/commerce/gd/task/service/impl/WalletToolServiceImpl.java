package com.gudeng.commerce.gd.task.service.impl;

import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO;
import com.gudeng.commerce.gd.order.dto.AccInfoDTO.ACC_STATUS;
import com.gudeng.commerce.gd.order.service.AccInfoService;
import com.gudeng.commerce.gd.task.service.WalletToolService;
import com.gudeng.commerce.gd.task.util.GdProperties;

public class WalletToolServiceImpl implements WalletToolService{
	@Autowired
	private  GdProperties gdProperties;
	
	private static AccInfoService accInfoService;
	
	protected AccInfoService getWalletService() throws MalformedURLException {
		String url = gdProperties.getProperties().getProperty("gd.wallet.url");
		if(accInfoService==null){
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setOverloadEnabled(true);
			accInfoService = (AccInfoService) factory.create(AccInfoService.class, url);
		}
		return  accInfoService;
	}
	
	public AccInfoDTO  getWalletIndex(Long memberId ) throws MalformedURLException{
		AccInfoDTO accInfoDTO= getWalletService().getWalletIndex(memberId);
		if (null==accInfoDTO) {
			accInfoDTO=new AccInfoDTO();
			accInfoDTO.setAccStatus(ACC_STATUS.VALIDATE.getValue());
			accInfoDTO.setBalAvailable(0.0);
			accInfoDTO.setBalblock(0.0);
			accInfoDTO.setBalTotal(0.0);
			accInfoDTO.setTransPwd("");
			accInfoDTO.setMemberId(memberId.intValue());
			getWalletService().addAccInfo(accInfoDTO);
		}
		
		if (null!=accInfoDTO) {
			if (StringUtils.isBlank(accInfoDTO.getTransPwd())) {
				accInfoDTO.setHasPwd(0);
			}
			else {
				accInfoDTO.setHasPwd(1);
			}
		}
		
		accInfoDTO.setTransPwd(null);
		return accInfoDTO;
	}
} 
